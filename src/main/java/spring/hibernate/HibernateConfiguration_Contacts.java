package spring.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfiguration_Contacts {

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new Configuration()
                .addAnnotatedClass(Contact.class)
                .configure();
        return configuration.buildSessionFactory();
    }

    @Bean
    public ContactDao contactDao() {
        return new ContactDao(sessionFactory());
    }
}
