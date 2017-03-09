package jpastart.attach;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AttachFileTest extends JpaTestBase {
    @Test
    public void find_by_SuperType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            AttachFile attach1 = em.find(AttachFile.class, "F001");
            assertThat(attach1, instanceOf(AttachFile.class));
            AttachFile attach2 = em.find(AttachFile.class, "F002");
            assertThat(attach2, instanceOf(LocalFile.class));
            AttachFile attach3 = em.find(AttachFile.class, "F003");
            assertThat(attach3, instanceOf(CloudFile.class));
        } finally {
            em.close();
        }
    }

    @Test
    public void find_by_SpecificType() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            LocalFile attach2 = em.find(LocalFile.class, "F002");
            assertThat(attach2, notNullValue());
        } finally {
            em.close();
        }
    }

    @Test
    public void save() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            AttachFile file1 = new AttachFile("F011", "F011", new Date());
            em.persist(file1);

            LocalFile file2 = new LocalFile("F012", "F012", new Date(), "/PATH");
            em.persist(file2);

            CloudFile file3 = new CloudFile("F013", "F013", new Date(), "I3", "http://i3.com/F013");
            em.persist(file3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
