package com.helix.note;

import com.helix.model.PatientNote;
import com.helix.model.User;
import com.helix.repo.AnalyticsDao;
import com.helix.repo.PatientNoteRepository;
import com.helix.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final PatientNoteRepository notes;
    private final UserRepository users;
    private final AnalyticsDao analytics;

    public NoteService(PatientNoteRepository notes, UserRepository users, AnalyticsDao analytics) {
        this.notes = notes;
        this.users = users;
        this.analytics = analytics;
    }

    public PatientNote add(PatientNote n) {
        return notes.save(n);
    }

    public List<PatientNote> forPatient(Long patientId) {
        return notes.findByPatientId(patientId);
    }

    public List<PatientNote> recent() {
        return notes.findAll();
    }

    public List<Object[]> notesByAuthor(Long userId) {
        User author = users.findById(userId).orElseThrow(() -> new RuntimeException("no user"));
        return analytics.notesByAuthorName(author.getUsername());
    }
}
