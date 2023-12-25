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
        oneToMany();
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
