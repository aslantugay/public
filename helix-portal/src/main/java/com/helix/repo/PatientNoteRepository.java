package com.helix.repo;

import com.helix.model.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    List<PatientNote> findByPatientId(Long patientId);
}
