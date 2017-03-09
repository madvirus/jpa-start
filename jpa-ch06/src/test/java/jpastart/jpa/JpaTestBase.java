package jpastart.jpa;

import jpastart.util.DBTestResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTestBase {
    @Rule
    public DBTestResource resource = new DBTestResource();

}
