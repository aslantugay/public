package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * A document uploaded by a portal account for one of its pets
 * (vaccination record, lab result, insurance scan, ...).
 */
@Entity
@Table(name = "pet_document")
public class PetDocument extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "owner_account_id")
    private Integer ownerAccountId;

    @Column(name = "pet_id")
    private Integer petId;

    @Column(name = "title")
    private String title;

    @Column(name = "storage_key")
    private String storageKey;

    /** free-form label used for the advanced-search report */
    @Column(name = "tag")
    private String tag;

    @Column(name = "notes", length = 2000)
    private String notes;

    public Integer getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(Integer ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
