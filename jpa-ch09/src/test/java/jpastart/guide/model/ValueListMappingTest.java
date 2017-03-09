package jpastart.guide.model;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import jpastart.util.DBUtil;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ValueListMappingTest extends JpaTestBase {

    private Itinerary2 findItinerary(Long primaryKey) {
        Itinerary2 itinerary2 = null;
        EntityManager em = EMF.createEntityManager();
        try {
            itinerary2 = em.find(Itinerary2.class, primaryKey);
            Hibernate.initialize(itinerary2.getSites());
        } finally {
            em.close();
        }
        return itinerary2;
    }


    private List<ItinerarySiteData2> selectSiteDatas(Long savedId) throws SQLException {
        List<ItinerarySiteData2> sites = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from itinerary_site where itinerary_id = ? order by list_idx");
            pstmt.setLong(1, savedId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new ItinerarySiteData2(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
            }
            return sites;
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }


    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        Long savedId = null;
        try {
            em.getTransaction().begin();
            List<SiteInfo> sitess = Arrays.asList(
                    new SiteInfo("경복궁", 180),
                    new SiteInfo("청계천", 60),
                    new SiteInfo("명동", 120),
                    new SiteInfo("인사동", 90)
            );
            Itinerary2 itinerary = new Itinerary2("광화문-종로 인근", "설명", sitess);
            em.persist(itinerary);
            em.getTransaction().commit();
            savedId = itinerary.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        List<ItinerarySiteData2> sites = selectSiteDatas(savedId);

        assertThat(sites, hasSize(4));
        SiteInfo[] expectedSites = {
                new SiteInfo("경복궁", 180),
                new SiteInfo("청계천", 60),
                new SiteInfo("명동", 120),
                new SiteInfo("인사동", 90)
        };
        for (int i = 0; i < expectedSites.length; i++) {
            assertThat(sites.get(i).listIdx, equalTo(i));
            assertThat(sites.get(i).site, equalTo(expectedSites[i].getSite()));
            assertThat(sites.get(i).time, equalTo(expectedSites[i].getTime()));
        }
    }

    @Test
    public void find() throws Exception {
        Itinerary2 itinerary = findItinerary(1L);

        assertThat(itinerary, notNullValue());
        assertSites(1L, itinerary.getSites());
    }


    @Test
    public void find_and_change_sites() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary2 itinerary = em.find(Itinerary2.class, 1L);
            List<SiteInfo> sites = Arrays.asList(
                    new SiteInfo("정림사지", 60),
                    new SiteInfo("궁남지", 120),
                    new SiteInfo("부여박물관", 90));
            itinerary.changeSites(sites);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        assertSites(1L);
    }

    @Test
    public void find_and_remove_site_list_element() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary2 itinerary = em.find(Itinerary2.class, 1L);
            itinerary.getSites().remove(1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        assertSites(1L);
    }


    @Test
    public void find_and_update_site_element_to_null() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary2 itinerary = em.find(Itinerary2.class, 1L);
            itinerary.getSites().set(1, null);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        List<ItinerarySiteData2> siteDataList = selectSiteDatas(1L);
        System.out.println(siteDataList.toString());
        assertThat(siteDataList, hasSize(3));
        assertThat(siteDataList.get(0).listIdx, equalTo(0));
        assertThat(siteDataList.get(1).listIdx, equalTo(2));
        assertThat(siteDataList.get(2).listIdx, equalTo(3)); // !!

        Itinerary2 itinerary = findItinerary(1L);
        List<SiteInfo> sites = itinerary.getSites();
        assertThat(sites, hasSize(4));
        assertThat(sites.get(1), nullValue());
    }

    @Test
    public void find_and_clear_site_list() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary2 itinerary = em.find(Itinerary2.class, 1L);
            itinerary.clearSites();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        assertSites(1L);
    }

    @Test
    public void find_and_set_sites_to_null() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary2 itinerary = em.find(Itinerary2.class, 1L);
            itinerary.changeSites(null);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        assertSites(1L);
    }

    private void assertSites(Long id, List<SiteInfo> sitess) throws SQLException {
        List<ItinerarySiteData2> siteDataList = selectSiteDatas(id);
        assertThat(sitess, hasSize(siteDataList.size()));
        for (int i = 0; i < siteDataList.size(); i++) {
            assertThat(
                    sitess.get(i).getSite(),
                    equalTo(siteDataList.get(i).site));
            assertThat(
                    sitess.get(i).getTime(),
                    equalTo(siteDataList.get(i).time));
        }
    }

    private void assertSites(Long id) throws SQLException {
        assertSites(id, findItinerary(id).getSites());
    }

    private class ItinerarySiteData2 {
        public final Long itineraryId;
        public final int listIdx;
        public final String site;
        public final int time;

        public ItinerarySiteData2(Long itineraryId, int listIdx, String site, int time) {
            this.itineraryId = itineraryId;
            this.listIdx = listIdx;
            this.site = site;
            this.time = time;
        }

        @Override
        public String toString() {
            return "ItinerarySiteData{" +
                    "itineraryId=" + itineraryId +
                    ", listIdx=" + listIdx +
                    ", site=" + site +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
}
