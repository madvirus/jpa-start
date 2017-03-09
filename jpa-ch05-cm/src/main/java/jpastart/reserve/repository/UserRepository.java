package jpastart.reserve.repository;

import jpastart.reserve.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class UserRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public User find(String email) {
        return em.find(User.class, email);
    }

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    @Transactional
    public void remove(User user) {
        em.remove(user);
    }

    @Transactional
    public List<User> findAll() {
        TypedQuery<User> query =
                em.createQuery(
                        "select u from User u order by u.name",
                        User.class);
        return query.getResultList();
    }
}
