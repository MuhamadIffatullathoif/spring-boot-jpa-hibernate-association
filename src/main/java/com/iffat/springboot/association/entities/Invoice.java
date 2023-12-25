package com.iffat.springboot.association.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    @ManyToOne
    private Client client;

    public Invoice() {
    }

    public Invoice(Long id, String description, Long total) {
        this.id = id;
        this.description = description;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", client=" + client +
                '}';
    }
}
