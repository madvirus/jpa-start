package jpastart.reserve.repository;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepository {

    public User find(String email) {
        EntityManager em = EMF.currentEntityManager();
        return em.find(User.class, email);
    }

    public void save(User user) {
        EntityManager em = EMF.currentEntityManager();
        em.persist(user);
    }

    public void remove(User user) {
        EntityManager em = EMF.currentEntityManager();
        em.remove(user);
    }

    public List<User> findAll() {
        TypedQuery<User> query =
                EMF.currentEntityManager()
                        .createQuery(
                                "select u from User u order by u.name",
                                User.class);
        return query.getResultList();
    }
}
