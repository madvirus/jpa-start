package jpastart.jta;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.reserve.application.UserNotFoundException;
import jpastart.reserve.model.User;
import jpastart.reserve.model.UserDomain;
import org.junit.Before;
import org.junit.Test;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TransactionJoinTest extends JpaTestBase {

    private final String email = "newuser@jpsworld.net";

    @Before
    public void setUp() throws Exception {
        UserDomain.instance().givenUser(email, "새사용자");
    }

    @Test
    public void create_em_before_beginTx_then_join_explicitly_after_begin_tx() throws Exception {
        EntityManager em = EMF.createEntityManager();
        boolean isJoinedBeforeTx = em.isJoinedToTransaction();

        UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");

        utx.begin();
        boolean isJoinedAfterTx = em.isJoinedToTransaction();

        boolean isJoinedAfterJoin = false;
        try {
            em.joinTransaction();

            isJoinedAfterJoin = em.isJoinedToTransaction();

            User user = em.find(User.class, email);
            if (user == null) {
                throw new UserNotFoundException();
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (SystemException e) {
            }
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
        assertThat(isJoinedBeforeTx, equalTo(false));
        assertThat(isJoinedAfterTx, equalTo(false));
        assertThat(isJoinedAfterJoin, equalTo(true));
    }

    @Test
    public void create_em_after_beginTx() throws Exception {
        UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        utx.begin();

        EntityManager em = EMF.createEntityManager();
        boolean isJoinedAfterCreate = em.isJoinedToTransaction();
        try {
            User user = em.find(User.class, email);
            if (user == null) {
                throw new UserNotFoundException();
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (SystemException e) {
            }
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
        assertThat(isJoinedAfterCreate, equalTo(true));
    }

    @Test
    public void create_em_before_beginTx_and_no_explicit_join() throws Exception {
        EntityManager em = EMF.createEntityManager();
        boolean isJoinedBeforeTx = em.isJoinedToTransaction();

        UserTransaction utx = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        utx.begin();

        boolean isJoinedAfterFirstAccess = false;
        try {
            User user = em.find(User.class, email);
            isJoinedAfterFirstAccess = em.isJoinedToTransaction();
            if (user == null) {
                throw new UserNotFoundException();
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (SystemException e) {
            }
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
        assertThat(isJoinedBeforeTx, equalTo(false));
        assertThat(isJoinedAfterFirstAccess, equalTo(true));
    }
}
