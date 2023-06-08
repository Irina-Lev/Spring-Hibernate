package spring.hibernate;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

/**
 * Unit tests for {@link ContactDao}.
 * <p>
 * Тесты проверяют корректность реализации ContactDao.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfiguration_Contacts.class)
public record ContactDaoTests(@Autowired ContactDao contactDao) {

    private static Contact IVAN;

    private static Contact MARIA;

    /**
     * There are two contacts inserted in the database in contact.sql.
     */
    private static List<Contact> PERSISTED_CONTACTS;

    @BeforeEach
    public void persistData() {
        IVAN = new Contact(
                "Ivan", "Ivanov", "iivanov@gmail.com", "1234567"
        );
        var id = contactDao.saveContact(IVAN);
        IVAN.setId(id);
        MARIA = new Contact(
                "Maria", "Ivanova", "mivanova@gmail.com", "7654321"
        );
        id = contactDao.saveContact(MARIA);
        MARIA.setId(id);
        PERSISTED_CONTACTS = List.of(IVAN, MARIA);
    }

    @AfterEach
    public void removeData() {
        contactDao.deleteContact(IVAN.getId());
        contactDao.deleteContact(MARIA.getId());
    }

    @Test
    void addContact() {
        var contact = new Contact("Jackie", "Chan", "jchan@gmail.com", "1234567890");
        var contactId = contactDao.saveContact(contact);
        contact.setId(contactId);

        var contactInDb = contactDao.getContact(contactId);


        assertThat(contactInDb).isEqualTo(contact);
    }

    @Test
    void getContact() {
        var contact = contactDao.getContact(IVAN.getId());

        assertThat(contact).isEqualTo(IVAN);
    }

    @Test
    void getAllContacts() {
        var contacts = contactDao.getAll();

        assertThat(contacts).containsAll(PERSISTED_CONTACTS);
    }

    @Test
    void updatePhoneNumber() {
        var contact = new Contact("Jekyll", "Hide", "jhide@gmail.com", "");
        var contactId = contactDao.saveContact(contact);
        var newPhone = "777-77-77";
        contact.setPhoneNumber(newPhone);
        contactDao.updateContact(contact);

        var updatedContact = contactDao.getContact(contact.getId());
        assertThat(updatedContact.getPhoneNumber()).isEqualTo(newPhone);
    }

    @Test
    void updateEmail() {
        var contact = new Contact("Captain", "America", "", "");
        var contactId = contactDao.saveContact(contact);

        var newEmail = "cap@gmail.com";
        contact.setEmail(newEmail);
        contactDao.updateContact(contact);

        var updatedContact = contactDao.getContact(contact.getId());
        assertThat(updatedContact.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void deleteContact() {
        var contact = new Contact("To be", "Deleted", "", "");
        var contactId = contactDao.saveContact(contact);

        contactDao.deleteContact(contactId);

        var deletedContact = contactDao.getContact(contactId);
        assertThat(deletedContact).isNull();
    }
}