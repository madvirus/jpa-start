package jpastart.util;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import jpastart.jpa.EMF;
import org.junit.rules.ExternalResource;

import java.util.Properties;

public class DBTestResource extends ExternalResource {

    private AtomikosDataSourceBean ds;

    @Override
    protected void before() throws Throwable {
        DBUtil.initTestData();

        ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("jpastart");
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        Properties p = new Properties();
        p.setProperty ( "user" , "jpauser" );
        p.setProperty ( "password" , "jpapass" );
        p.setProperty ( "URL" , "jdbc:mysql://localhost/jpastart?characterEncoding=utf8" );
        p.setProperty ( "pinGlobalTxToPhysicalConnection" , "true" );
        ds.setXaProperties(p);
        ds.setUniqueResourceName("jdbc/jpastart");
        ds.setPoolSize ( 5 );
        ds.init();

        EMF.init();
    }

    @Override
    protected void after() {
        EMF.close();
        ds.close();
    }
}
