package spring.hibernate;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfiguration_Contacts.class);
        var contactDao = context.getBean(ContactDao.class);

        var contact1 = new Contact("Ivan", "Ivanov", "1234567", "ivan@yandex.ru");
        contactDao.saveContact(contact1);

        var contact2 = new Contact("Svetlana", "Ivanova", "2224567", "svetlana@yandex.ru");
        contactDao.saveContact(contact2);

        var contacts = contactDao.getAll();
        System.out.println("List of all contacts: " + contacts);

        var contact = contactDao.getContact(2);
        contact.setPhoneNumber("7777777");
        contact.setEmail("post_office@mail.ru");
        contactDao.updateContact(contact);
        contactDao.deleteContact(1);

        contacts = contactDao.getAll();
        System.out.println("List of all contacts: " + contacts);
    }
}
