package jpastart.query;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.model.User;
import jpastart.team.Player;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CollectionQueryTest extends JpaTestBase {
    @Test
    public void memberOfElementCollection() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u where :keyword member of u.keywords order by u.name", User.class);
            query.setParameter("keyword", "서울");
            List<User> users = query.getResultList();
            for (User p : users) {
                System.out.println(p.getName());
            }
        } finally {
            em.close();
        }
    }

//    @Test
//    public void isEmptyOfElementCollection() throws Exception {
//        EntityManager em = EMF.createEntityManager();
//        try {
//            TypedQuery<User> query = em.createQuery("select u from User u where u.keywords is empty ", User.class);
//            List<User> users = query.getResultList();
//            for (User p : users) {
//                System.out.println(p.getName());
//            }
//        } finally {
//            em.close();
//        }
//    }
}
