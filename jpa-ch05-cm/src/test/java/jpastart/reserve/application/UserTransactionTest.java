package jpastart.reserve.application;

import jpastart.reserve.model.UserDomain;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class UserTransactionTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage("jpastart.reserve.model")
                .addPackage("jpastart.reserve.repository")
                .addPackage("jpastart.reserve.application")
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Test
    public void session() throws Exception {

        String email = "newuser@jpsworld.net";
        UserDomain.instance().givenUser(email, "새사용자");

        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from User").executeUpdate();
        utx.commit();

        assertThat(UserDomain.instance().countsByEmail(email), equalTo(0));
    }
}
