package com.iffat.springboot.association.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    // @JoinColumn(name = "client_id")
    @JoinTable(
            name = "tbl_clients_to_addresses",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"address_id"})
    )
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private List<Invoice> invoices;

    public Client(Long id, String name, String lastname) {
        this();
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Client() {
        addresses = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", addresses='" + addresses + '\'' +
                ", invoices='" + invoices + '\'' +
                '}';
    }
}
