package com.iffat.springboot.association;

import com.iffat.springboot.association.entities.*;
import com.iffat.springboot.association.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class SpringBootHibernateJpaAssociationApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

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
        // oneToOneBidirectionalFindById();
        // manyToMany();
        // manyToManyFind();
        // manyToManyRemoveFind();
        manyToManyRemove();
    }

    public void manyToManyRemove() {
        Student student1 = new Student("Jhon", "Doe");
        Student student2 = new Student("Ton", "Doe");

        Course course1 = new Course("Master Java Course","Steven");
        Course course2 = new Course("Master Javascript Course","Steven");

        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(List.of(student1, student2));
        students.forEach(System.out::println);

        Optional<Student> optionalStudentDB = studentRepository.findOneWithCourse(3L);

        if(optionalStudentDB.isPresent()) {
            Student student = optionalStudentDB.get();

            Optional<Course> optionalCourseDB = courseRepository.findById(3L);

            if(optionalCourseDB.isPresent()) {
             Course courseDB = optionalCourseDB.get();
             student.getCourses().remove(courseDB);
                Student student3 = studentRepository.save(student);
                System.out.println(student3);
            }
        }
    }

    public void manyToManyRemoveFind() {
        Optional<Student> optionalStudent1 = studentRepository.findById(1L);
        Optional<Student> optionalStudent2 = studentRepository.findById(2L);

        Student student1 = optionalStudent1.get();
        Student student2 = optionalStudent2.get();

        Course course1 = courseRepository.findById(1L).get();
        Course course2 = courseRepository.findById(2L).get();

        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(List.of(student1, student2));
        students.forEach(System.out::println);

        Optional<Student> optionalStudentDB = studentRepository.findOneWithCourse(1L);
        if(optionalStudentDB.isPresent()) {

            Student studentDB = optionalStudentDB.get();
            Optional<Course> optionalCourseDB = courseRepository.findById(2L);

            if(optionalCourseDB.isPresent()) {
                Course courseDB = optionalCourseDB.get();
                studentDB.getCourses().remove(courseDB);

                Student studentResponse = studentRepository.save(studentDB);
                System.out.println(studentResponse);
            }
        }
    }

    public void manyToManyFind() {
        Optional<Student> optionalStudent1 = studentRepository.findById(1L);
        Optional<Student> optionalStudent2 = studentRepository.findById(2L);

        Student student1 = optionalStudent1.get();
        Student student2 = optionalStudent2.get();

        Course course1 = courseRepository.findById(1L).get();
        Course course2 = courseRepository.findById(2L).get();

        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(List.of(student1, student2));
        students.forEach(System.out::println);
    }

    @Transactional
    public void manyToMany() {
        Student student1 = new Student("Jhon", "Doe");
        Student student2 = new Student("Ton", "Doe");

        Course course1 = new Course("Master Java Course","Steven");
        Course course2 = new Course("Master Javascript Course","Steven");

        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        Iterable<Student> students = studentRepository.saveAll(List.of(student1, student2));
        students.forEach(System.out::println);
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
