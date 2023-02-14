package tryhibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import com.example.tryhibernate.entity.People;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

public class XmlMappingAppTests {

    @Test
    void test() {
        SessionFactory sessionFactory = null;
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            EntityManager entityManager = sessionFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.persist(new People("kent", 20));

            List<People> result = entityManager.createQuery( "from com.example.tryhibernate.entity.People", People.class ).getResultList();
            for ( People people : result ) {
                Assertions.assertNotNull(people);
                Assertions.assertEquals("kent", people.getName());
                Assertions.assertEquals(20, people.getAge());
                entityManager.remove(people);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
    }
}
