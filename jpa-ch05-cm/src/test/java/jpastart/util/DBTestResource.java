package jpastart.util;

import org.junit.rules.ExternalResource;

public class DBTestResource extends ExternalResource {
    @Override
    protected void before() throws Throwable {
        DBUtil.initTestData();
    }
}
