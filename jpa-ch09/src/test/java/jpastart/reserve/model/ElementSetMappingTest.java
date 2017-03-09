package jpastart.reserve.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class ElementSetMappingTest extends JpaTestBase {

    private User findUser(String email) {
        EntityManager em = EMF.createEntityManager();
        try {
            User user = em.find(User.class, email);
            Hibernate.initialize(user.getKeywords());
            return user;
        } finally {
            em.close();
        }
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = new User("user@email.com", "사용자", new Date());
            HashSet<String> keywords = new HashSet<>();
            keywords.add("역사");
            keywords.add("고구려");
            keywords.add("고조선");
            keywords.add("조선");
            keywords.add("부여");
            keywords.add("전통음식");
            keywords.add("유적");
            keywords.add("이순신");
            keywords.add("세종대왕");
            user.setKeywords(keywords);
            em.persist(user);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        User user = findUser("user@email.com");
        Set<String> keywords = user.getKeywords();
        assertThat(keywords, hasItems("역사", "유적", "전통음식"));
        for (String keyword : keywords) {
            System.out.println(keyword);
        }
    }

    @Test
    public void addAndRemove() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            Set<String> keywords = user.getKeywords();
            keywords.remove("서울");
            keywords.add("한성");
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void assignNewSet() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            Set<String> keywords = new HashSet<>();
            keywords.add("한성");
            keywords.add("부여");
            user.setKeywords(keywords);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void clearAndAdd() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            Set<String> keywords = user.getKeywords();
            keywords.clear();
            keywords.add("한성");
            keywords.add("부여");
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void clear() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            Set<String> keywords = user.getKeywords();
            keywords.clear();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Test
    public void assignEmptySet() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            user.setKeywords(Collections.emptySet());
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
    @Test
    public void assignNull() throws Exception {
        String email = "gildong@dooly.net";

        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, email);
            user.setKeywords(null);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }


}
