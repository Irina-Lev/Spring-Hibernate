package spring.hibernate;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveContact(Contact contact) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.persist(contact);
            transaction.commit();
            return contact.getId();
        }
    }

    public void deleteContact(long contactID) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = getContact(contactID);
            if (contact!=null) {
                session.remove(contact);
            }
            transaction.commit();
        }
    }

    public void updateContact(Contact contact) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.merge(contact);
            transaction.commit();
        }
    }
    public List<Contact> getAll() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Contact c", Contact.class).getResultList();
        }
    }

    public Contact getContact(long contactID) {
        try (var session = sessionFactory.openSession()) {
            return session.get(Contact.class, contactID);
        }
    }



}
