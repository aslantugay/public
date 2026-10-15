package org.springframework.samples.petclinic.portal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.PetDocument;
import org.springframework.samples.petclinic.portal.service.PortalDocumentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portal/documents")
public class PortalDocumentController {

    @Autowired
    private PortalDocumentService documentService;

    @PostMapping
    public PetDocument create(@RequestBody PetDocument document) {
        return documentService.create(document);
    }

    @GetMapping("/mine")
    public List<PetDocument> mine() {
        return documentService.listMine();
    }

    @GetMapping("/{id}")
    public PetDocument get(@PathVariable Integer id) {
        return documentService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        documentService.delete(id);
    }

    @GetMapping("/search")
    public List<Object[]> search(@RequestParam String keyword,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return documentService.search(keyword, orderBy);
    }

    @GetMapping("/report/{savedDocId}")
    public List<Object[]> report(@PathVariable Integer savedDocId) {
        return documentService.report(savedDocId);
    }

    @GetMapping("/label")
    public Object label(@RequestParam String expr) {
        return documentService.renderLabel(expr);
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String name) {
        return documentService.download(name);
    }

    @PostMapping("/fetch")
    public byte[] fetch(@RequestParam String url) {
        return documentService.fetchFromUrl(url);
    }

    @PostMapping("/import")
    public String importSettings(@RequestParam String data) {
        return documentService.importSettings(data);
    }
}
