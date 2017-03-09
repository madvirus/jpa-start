package jpastart.reserve.repository;

import jpastart.jpa.EMF;
import jpastart.reserve.model.Hotel;

import javax.persistence.EntityManager;

public class HotelRepository {

    public Hotel find(String id) {
        EntityManager em = EMF.currentEntityManager();
        return em.find(Hotel.class, id);
    }

}
