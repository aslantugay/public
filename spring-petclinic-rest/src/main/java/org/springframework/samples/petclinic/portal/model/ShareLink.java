package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * A capability token that grants read access to a single document.
 */
@Entity
@Table(name = "share_link")
public class ShareLink extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "doc_id")
    private Integer docId;

    @Column(name = "owner_account_id")
    private Integer ownerAccountId;

    @Column(name = "token")
    private String token;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(Integer ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
