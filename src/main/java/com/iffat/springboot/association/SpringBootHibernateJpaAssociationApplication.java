package com.iffat.springboot.association;

import com.iffat.springboot.association.entities.Address;
import com.iffat.springboot.association.entities.Client;
import com.iffat.springboot.association.entities.Invoice;
import com.iffat.springboot.association.repositories.ClientRepository;
import com.iffat.springboot.association.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class SpringBootHibernateJpaAssociationApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

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
        removeAddressFindById();
    }

    public void removeAddressFindById() {
        Optional<Client> optionalClient = clientRepository.findById(1L);
        optionalClient.ifPresent(optClient -> {
            Address address1 = new Address("Sabaody",1234);
            Address address2 = new Address("Alabasta",5678);

            optClient.setAddresses(Arrays.asList(address1, address2));

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
        Client client = new Client("Jhon","Doe");

        Address address1 = new Address("Sabaody",1234);
        Address address2 = new Address("Alabasta",5678);

        client.setAddresses(Arrays.asList(address1, address2));

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
            Address address1 = new Address("Sabaody",1234);
            Address address2 = new Address("Alabasta",5678);

            client.setAddresses(Arrays.asList(address1, address2));

            Client clientDB = clientRepository.save(client);
            System.out.println(clientDB);
        });
    }

    public void oneToMany() {
        Client client = new Client("Jhon","Doe");

        Address address1 = new Address("Sabaody",1234);
        Address address2 = new Address("Alabasta",5678);
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);
        Client clientDB = clientRepository.save(client);
        System.out.println(clientDB);
    }

    public void manyToOne(){
        Client client = new Client("Jhon","Doe");
        clientRepository.save(client);
        Invoice invoice = new Invoice("Invoice Office",2000L);
        invoice.setClient(client);
        Invoice invoiceDB = invoiceRepository.save(invoice);
        System.out.println(invoiceDB);
    }

    public void manyToOneFindByIdClient(){
        Optional<Client> optionalClient = clientRepository.findById(1L);
        if(optionalClient.isPresent()) {
            Client client = optionalClient.orElseThrow();
            Invoice invoice = new Invoice("Invoice Office", 2000L);
            invoice.setClient(client);
            Invoice invoiceDB = invoiceRepository.save(invoice);
            System.out.println(invoiceDB);
        }
    }
}
