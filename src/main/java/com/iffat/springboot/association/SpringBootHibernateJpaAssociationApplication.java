package com.iffat.springboot.association;

import com.iffat.springboot.association.entities.Address;
import com.iffat.springboot.association.entities.Client;
import com.iffat.springboot.association.entities.ClientDetails;
import com.iffat.springboot.association.entities.Invoice;
import com.iffat.springboot.association.repositories.ClientDetailsRepository;
import com.iffat.springboot.association.repositories.ClientRepository;
import com.iffat.springboot.association.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class SpringBootHibernateJpaAssociationApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHibernateJpaAssociationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // manyToOne();
        // manyToOneFindByIdClient();
        // oneToMany();
        // oneToManyFindByIdClient();
        // removeAddress();
        // removeAddressFindById();
        // oneToManyInvoiceBiDirectional();
        // oneToManyInvoiceBiDirectionalFindById();
        // removeInvoiceBiDirectionalFindById();
        // removeInvoiceBiDirectional();
        // oneToOne();
        // oneToOneFindById();
        // oneToOneBidirectional();
        oneToOneBidirectionalFindById();
    }

    public void oneToOneBidirectionalFindById() {
        Optional<Client> optionalClient = clientRepository.findOne(1L);

        optionalClient.ifPresent(client -> {
            ClientDetails clientDetails = new ClientDetails(true, 5000);
            client.setClientDetails(clientDetails);
            Client clientDB = clientRepository.save(client);
            System.out.println(clientDB);
        });
    }

    public void oneToOneBidirectional() {
        Client client = new Client("Jhon","Doe");

        ClientDetails clientDetails = new ClientDetails(true, 5000);

        client.setClientDetails(clientDetails);

        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);
    }

    public void oneToOneFindById() {
        ClientDetails clientDetails = new ClientDetails(true, 5000);
        clientDetailsRepository.save(clientDetails);

        Optional<Client> optionalClient = clientRepository.findOne(2L);
        optionalClient.ifPresent(client -> {
            client.setClientDetails(clientDetails);
            Client clientDB = clientRepository.save(client);
            System.out.println(clientDB);
        });
    }

    public void oneToOne() {

        ClientDetails clientDetails = new ClientDetails(true, 5000);
        clientDetailsRepository.save(clientDetails);

        Client client = new Client("Jhon","Doe");
        client.setClientDetails(clientDetails);
        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);
    }

    public void removeInvoiceBiDirectional() {
        Client client = new Client("Jhon","Doe");

        Invoice invoice1 = new Invoice("Invoice Sport",200L);
        Invoice invoice2 = new Invoice("Invoice Office", 300L);

        client.addInvoice(invoice1).addInvoice(invoice2);

        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);

        Optional<Client> optionalClientDB = clientRepository.findOne(3L);

        optionalClientDB.ifPresent(optClient -> {
            Optional<Invoice> optionalInvoiceDB = invoiceRepository.findById(2L);

            optionalInvoiceDB.ifPresent(optInvoice -> {
                optClient.remove(optInvoice);
                Client clientDB1 = clientRepository.save(optClient);
                System.out.println(clientDB1);
            });
        });
    }

    public void removeInvoiceBiDirectionalFindById() {
        Optional<Client> optionalClient = clientRepository.findOne(1L);

        optionalClient.ifPresent(optClient -> {

            Invoice invoice1 = new Invoice("Invoice Sport",200L);
            Invoice invoice2 = new Invoice("Invoice Office", 300L);

            optClient.addInvoice(invoice1).addInvoice(invoice2);

            Client clientDB = clientRepository.save(optClient);
            System.out.println(clientDB);
        });

        Optional<Client> optionalClient2 = clientRepository.findOne(1L);
        optionalClient2.ifPresent(optClient -> {
            Invoice invoice3 = new Invoice("Invoice Sport",200L);
            invoice3.setId(2L);

            // Optional<Invoice> optionalInvoice = invoiceRepository.findById(2L);
            Optional<Invoice> optionalInvoice = Optional.of(invoice3);

            optionalInvoice.ifPresent(optInvoice -> {
                // optClient.remove(optInvoice);
                optClient.getInvoices().remove(optInvoice);
                optInvoice.setClient(null);
                Client clientDB = clientRepository.save(optClient);
                System.out.println(clientDB);
            });
        });
    }

    public void oneToManyInvoiceBiDirectionalFindById() {
        Optional<Client> optionalClient = clientRepository.findOne(1L);

        optionalClient.ifPresent(optClient -> {
            Invoice invoice1 = new Invoice("Invoice Office", 2000L);
            Invoice invoice2 = new Invoice("Invoice Sport", 1000L);

            optClient.addInvoice(invoice1).addInvoice(invoice2);

            Client clientDB = clientRepository.save(optClient);
            System.out.println(clientDB);
        });
    }

    public void oneToManyInvoiceBiDirectional() {
        Client client = new Client("Jhon", "Doe");

        Invoice invoice1 = new Invoice("Invoice Office", 2000L);
        Invoice invoice2 = new Invoice("Invoice Sport", 1000L);

//        List<Invoice> invoices = Arrays.asList(invoice1, invoice2);
//        client.setInvoices(invoices);
//
//        invoice1.setClient(client);
//        invoice2.setClient(client);
        client.addInvoice(invoice1).addInvoice(invoice2);

        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);
    }

    public void removeAddressFindById() {
        Optional<Client> optionalClient = clientRepository.findById(1L);
        optionalClient.ifPresent(optClient -> {
            Address address1 = new Address("Sabaody", 1234);
            Address address2 = new Address("Alabasta", 5678);
            Set<Address> addresses = new HashSet<>();
            addresses.add(address1);
            addresses.add(address2);
            optClient.setAddresses(addresses);

            Client clientDB = clientRepository.save(optClient);
            System.out.println(clientDB);

            Optional<Client> optionalClient2 = clientRepository.findOne(1L);
            optionalClient2.ifPresent(optClient2 -> {
                optClient2.getAddresses().remove(address1);
                Client clientDB2 = clientRepository.save(optClient2);
                System.out.println(clientDB2);
            });
        });
    }

    public void removeAddress() {
        Client client = new Client("Jhon", "Doe");

        Address address1 = new Address("Sabaody", 1234);
        Address address2 = new Address("Alabasta", 5678);

        Set<Address> addresses = new HashSet<>();
        addresses.add(address1);
        addresses.add(address2);
        client.setAddresses(addresses);

        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);

        Optional<Client> optionalClient = clientRepository.findById(3L);
        optionalClient.ifPresent(opClient -> {
            opClient.getAddresses().remove(address2);
            clientRepository.save(opClient);
            System.out.println(opClient);
        });
    }

    public void oneToManyFindByIdClient() {
        Optional<Client> optionalClient = clientRepository.findById(2L);
        optionalClient.ifPresent(client -> {
            Address address1 = new Address("Sabaody", 1234);
            Address address2 = new Address("Alabasta", 5678);

            Set<Address> addresses = new HashSet<>();
            addresses.add(address1);
            addresses.add(address2);
            client.setAddresses(addresses);

            Client clientDB = clientRepository.save(client);
            System.out.println(clientDB);
        });
    }

    public void oneToMany() {
        Client client = new Client("Jhon", "Doe");

        Address address1 = new Address("Sabaody", 1234);
        Address address2 = new Address("Alabasta", 5678);
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);
        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);
    }

    public void manyToOne() {
        Client client = new Client("Jhon", "Doe");
        clientRepository.save(client);
        Invoice invoice = new Invoice("Invoice Office", 2000L);
        invoice.setClient(client);
        Invoice invoiceDB = invoiceRepository.save(invoice);
        System.out.println(invoiceDB);
    }

    public void manyToOneFindByIdClient() {
        Optional<Client> optionalClient = clientRepository.findById(1L);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.orElseThrow();
            Invoice invoice = new Invoice("Invoice Office", 2000L);
            invoice.setClient(client);
            Invoice invoiceDB = invoiceRepository.save(invoice);
            System.out.println(invoiceDB);
        }
    }
}
