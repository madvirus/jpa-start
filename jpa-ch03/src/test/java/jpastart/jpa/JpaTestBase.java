package jpastart.jpa;

import jpastart.util.DBTestResource;
import org.junit.Rule;

public class JpaTestBase {
    @Rule
    public DBTestResource resource = new DBTestResource();
}
