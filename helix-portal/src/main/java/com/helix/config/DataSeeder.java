package com.helix.config;

import com.helix.common.SignUtil;
import com.helix.model.*;
import com.helix.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PatientRepository patients;
    private final InvoiceRepository invoices;
    private final DocumentRepository documents;
    private final PatientNoteRepository notes;

    public DataSeeder(UserRepository users, PatientRepository patients, InvoiceRepository invoices,
                      DocumentRepository documents, PatientNoteRepository notes) {
        this.users = users;
        this.patients = patients;
        this.invoices = invoices;
        this.documents = documents;
        this.notes = notes;
    }

    @Override
    public void run(String... args) {
        User alice = mkUser("alice", "md5pass1", "alice@helix.local", "USER", 1L);
        User bob = mkUser("bob", "md5pass2", "bob@helix.local", "USER", 1L);
        User carol = mkUser("carol", "md5pass3", "carol@globex.local", "USER", 2L);
        User admin = mkUser("admin", "md5admin", "admin@helix.local", "ADMIN", 1L);

        Patient p1 = mkPatient(alice.getId(), 1L, "John Public", "111-22-3333", "Hypertension", "O+");
        Patient p2 = mkPatient(bob.getId(), 1L, "Jane Roe", "444-55-6666", "Diabetes", "A-");
        Patient p3 = mkPatient(carol.getId(), 2L, "Max Globex", "777-88-9999", "Asthma", "B+");

        mkInvoice(p1.getId(), alice.getId(), 1L, 1200.0, "PAID");
        mkInvoice(p2.getId(), bob.getId(), 1L, 850.0, "DUE");
        mkInvoice(p3.getId(), carol.getId(), 2L, 2300.0, "DUE");

        mkDoc(alice.getId(), 1L, "Lab Result A", "labA.pdf", "PRIVATE");
        mkDoc(bob.getId(), 1L, "Lab Result B", "labB.pdf", "PRIVATE");
        mkDoc(carol.getId(), 2L, "MRI Scan", "mri.pdf", "PRIVATE");

        notes.save(mkNote(p1.getId(), alice.getId(), "Follow-up in 2 weeks", "clinical"));
        notes.save(mkNote(p2.getId(), bob.getId(), "Patient reports fatigue", "clinical"));
    }

    private User mkUser(String u, String p, String e, String role, Long tenant) {
        User x = new User();
        x.setUsername(u); x.setPassword(p); x.setEmail(e); x.setRole(role);
        x.setTenantId(tenant); x.setApiKey(SignUtil.sign(u + ":key")); x.setBio("");
        return users.save(x);
    }

    private Patient mkPatient(Long owner, Long tenant, String name, String ssn, String dx, String bt) {
        Patient x = new Patient();
        x.setOwnerUserId(owner); x.setTenantId(tenant); x.setName(name);
        x.setSsn(ssn); x.setDiagnosis(dx); x.setBloodType(bt);
        return patients.save(x);
    }

    private void mkInvoice(Long patientId, Long owner, Long tenant, Double amt, String status) {
        Invoice x = new Invoice();
        x.setPatientId(patientId); x.setOwnerUserId(owner); x.setTenantId(tenant);
        x.setAmount(amt); x.setStatus(status); x.setMemo("");
        invoices.save(x);
    }

    private void mkDoc(Long owner, Long tenant, String title, String file, String vis) {
        Document x = new Document();
        x.setOwnerUserId(owner); x.setTenantId(tenant); x.setTitle(title);
        x.setFilename(file); x.setStoragePath("/var/lib/helix/storage/" + file); x.setVisibility(vis);
        documents.save(x);
    }

    private PatientNote mkNote(Long patientId, Long author, String body, String cat) {
        PatientNote n = new PatientNote();
        n.setPatientId(patientId); n.setAuthorId(author); n.setBody(body); n.setCategory(cat);
        return n;
    }
}
