package com.helix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_filters")
public class SavedFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerUserId;
    private String name;
    @Column(length = 2000)
    private String whereClause;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getWhereClause() { return whereClause; }
    public void setWhereClause(String whereClause) { this.whereClause = whereClause; }
}
