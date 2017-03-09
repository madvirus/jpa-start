package jpastart.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

public class EMF {
    private static EntityManagerFactory emf;
    private static ThreadLocal<EntityManager> currentEm = new ThreadLocal<>();

    public static void init() {
        emf = Persistence.createEntityManagerFactory("jpastart");
    }
    
    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public static EntityManager currentEntityManager() {
        EntityManager em = currentEm.get();
        if (em == null) {
            em = emf.createEntityManager();
            currentEm.set(em);
        }
        return em;
    }

    public static void closeCurrentEntityManager() {
        EntityManager em = currentEm.get();
        if (em != null) {
            currentEm.remove();
            em.close();
        }
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static void close() {
        emf.close();
    }

    public static PersistenceUnitUtil getUnitUtil() {
        return emf.getPersistenceUnitUtil();
    }
}
