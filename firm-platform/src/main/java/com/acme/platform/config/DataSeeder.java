package com.acme.platform.config;

import com.acme.platform.domain.Comment;
import com.acme.platform.domain.Document;
import com.acme.platform.domain.Firm;
import com.acme.platform.domain.Invoice;
import com.acme.platform.domain.Role;
import com.acme.platform.domain.Unit;
import com.acme.platform.domain.User;
import com.acme.platform.repository.CommentRepository;
import com.acme.platform.repository.DocumentRepository;
import com.acme.platform.repository.FirmRepository;
import com.acme.platform.repository.InvoiceRepository;
import com.acme.platform.repository.UnitRepository;
import com.acme.platform.repository.UserRepository;
import com.acme.platform.security.PasswordHasher;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final FirmRepository firmRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final InvoiceRepository invoiceRepository;
    private final CommentRepository commentRepository;
    private final PasswordHasher passwordHasher;

    public DataSeeder(FirmRepository firmRepository,
                      UnitRepository unitRepository,
                      UserRepository userRepository,
                      DocumentRepository documentRepository,
                      InvoiceRepository invoiceRepository,
                      CommentRepository commentRepository,
                      PasswordHasher passwordHasher) {
        this.firmRepository = firmRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.invoiceRepository = invoiceRepository;
        this.commentRepository = commentRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void run(String... args) {
        Firm acme = newFirm("Acme Holding", "1234567890", "billing@acme.example");
        Firm globex = newFirm("Globex Corp", "9876543210", "billing@globex.example");

        Unit acmeFinance = newUnit(acme, "Finance", "CC-100");
        Unit acmeHr = newUnit(acme, "HR", "CC-200");
        Unit globexRnd = newUnit(globex, "R&D", "CC-900");

        newUser(acme, acmeFinance, "alice@acme.example", "Alice Finance", Role.FIRM_ADMIN, "alice123");
        newUser(acme, acmeHr, "bob@acme.example", "Bob HR", Role.UNIT_MANAGER, "bob123");
        newUser(globex, globexRnd, "carol@globex.example", "Carol RnD", Role.UNIT_MANAGER, "carol123");

        Document acmeDoc = newDocument(acme, acmeFinance,
                "Q3 Financials", "financials/q3.pdf", "CONFIDENTIAL");
        Document globexDoc = newDocument(globex, globexRnd,
                "Project Zeus Spec", "specs/zeus.pdf", "SECRET");

        // Cross-tenant activity stream entries.
        newComment(acmeDoc, "alice@acme.example", "Uploaded the reviewed Q3 numbers.");
        newComment(globexDoc, "carol@globex.example", "First draft of the Zeus spec is ready for review.");

        newInvoice(acme, "INV-1001", "Initech", new BigDecimal("4200.00"));
        newInvoice(globex, "INV-2001", "Umbrella", new BigDecimal("9100.00"));
    }

    private Firm newFirm(String name, String tax, String email) {
        Firm firm = new Firm();
        firm.setName(name);
        firm.setTaxNumber(tax);
        firm.setBillingEmail(email);
        firm.setCreditBalance(new BigDecimal("5000.00"));
        return firmRepository.save(firm);
    }

    private Unit newUnit(Firm firm, String name, String costCenter) {
        Unit unit = new Unit();
        unit.setFirm(firm);
        unit.setName(name);
        unit.setCostCenter(costCenter);
        return unitRepository.save(unit);
    }

    private void newUser(Firm firm, Unit unit, String email, String name, Role role, String pwd) {
        User user = new User();
        user.setFirm(firm);
        user.setUnit(unit);
        user.setEmail(email);
        user.setDisplayName(name);
        user.setRole(role);
        user.setPasswordHash(passwordHasher.hash(pwd));
        userRepository.save(user);
    }

    private Document newDocument(Firm firm, Unit unit, String title, String key, String classification) {
        Document document = new Document();
        document.setPublicId(UUID.randomUUID().toString());
        document.setFirmId(firm.getId());
        document.setUnitId(unit.getId());
        document.setTitle(title);
        document.setStorageKey(key);
        document.setClassification(classification);
        return documentRepository.save(document);
    }

    private void newComment(Document document, String authorEmail, String body) {
        Comment comment = new Comment();
        comment.setDocumentPublicId(document.getPublicId());
        comment.setDocumentTitle(document.getTitle());
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);
        commentRepository.save(comment);
    }

    private void newInvoice(Firm firm, String number, String customer, BigDecimal amount) {
        Invoice invoice = new Invoice();
        invoice.setFirmId(firm.getId());
        invoice.setInvoiceNumber(number);
        invoice.setCustomerName(customer);
        invoice.setAmount(amount);
        invoice.setMemoHtml("<p>Thank you for your business.</p>");
        invoiceRepository.save(invoice);
    }
}
