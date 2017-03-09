package jpastart.reserve.repository;

import jpastart.reserve.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public User find(String email) {
        return em.find(User.class, email);
    }

    public void save(User user) {
        em.persist(user);
    }

    public void remove(User user) {
        em.remove(user);
    }

    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery(
                "select u from User u order by u.name",
                User.class);
        return query.getResultList();
    }
}
