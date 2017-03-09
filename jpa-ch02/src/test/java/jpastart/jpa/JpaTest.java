package jpastart.jpa;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import jpastart.reserve.model.User;

public class JpaTest {

    @Test
    public void createEntityManagerFactory() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpastart");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(new User("user2@user.com", "user", new Date()));
            transaction.commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        emf.close();
    }
}
