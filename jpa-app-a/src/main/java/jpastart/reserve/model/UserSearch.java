package jpastart.reserve.model;

import jpastart.jpa.EMF;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserSearch {
    public List<User> search(String name) {
        EntityManager em = EMF.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.like(root.get(User_.name), "최%"));
            TypedQuery<User> query = em.createQuery(cq);
            List<User> users = query.getResultList();
            return users;
        } finally {
            em.close();
        }
    }
}
