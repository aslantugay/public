package org.springframework.samples.petclinic.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * A message on the shared support wall shown to all signed-in members on the
 * portal landing page.
 */
@Entity
@Table(name = "support_message")
public class SupportMessage extends org.springframework.samples.petclinic.model.BaseEntity {

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "author")
    private String author;

    @Column(name = "body", length = 2000)
    private String body;

    /** reference to the subject of the message (e.g. invoice/doc) */
    @Column(name = "ref_type")
    private String refType;

    @Column(name = "ref_id")
    private Integer refId;

    @Column(name = "share_token")
    private String shareToken;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
