package com.helix.note;

import com.helix.common.CommonResult;
import com.helix.model.PatientNote;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping("/patients/{patientId}")
    public CommonResult add(@PathVariable Long patientId, @RequestBody PatientNote note) {
        note.setPatientId(patientId);
        return CommonResult.success(service.add(note));
    }

    @GetMapping("/patients/{patientId}")
    public CommonResult forPatient(@PathVariable Long patientId) {
        return CommonResult.success(service.forPatient(patientId));
    }

    @GetMapping(value = "/feed", produces = MediaType.TEXT_HTML_VALUE)
    public String feed() {
        StringBuilder html = new StringBuilder("<html><body><h2>Recent notes</h2>");
        List<PatientNote> all = service.recent();
        for (PatientNote n : all) {
            html.append("<div class='note'>").append(n.getBody()).append("</div>");
        }
        return html.append("</body></html>").toString();
    }

    @GetMapping("/analytics/by-author/{userId}")
    public CommonResult byAuthor(@PathVariable Long userId) {
        return CommonResult.success(service.notesByAuthor(userId));
    }
}
