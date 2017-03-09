package jpastart.util;

import jpastart.jpa.EMF;
import org.junit.rules.ExternalResource;

public class DBTestResource extends ExternalResource {
    @Override
    protected void before() throws Throwable {
        DBUtil.initTestData();
        EMF.init();
    }

    @Override
    protected void after() {
        EMF.close();
    }
}
