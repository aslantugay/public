package org.springframework.samples.petclinic.portal.service;

import org.springframework.samples.petclinic.portal.model.PetDocument;

import java.util.List;

public interface PortalDocumentService {

    PetDocument create(PetDocument document);

    PetDocument get(Integer id);

    void delete(Integer id);

    List<PetDocument> listMine();

    List<Object[]> search(String keyword, String orderBy);

    List<Object[]> report(Integer savedDocId);

    Object renderLabel(String expr);

    byte[] download(String name);

    byte[] fetchFromUrl(String url);

    String importSettings(String data);
}
