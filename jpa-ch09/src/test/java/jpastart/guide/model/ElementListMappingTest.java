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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ElementListMappingTest extends JpaTestBase {

    private Itinerary findItinerary(Long primaryKey) {
        Itinerary itinerary = null;
        EntityManager em = EMF.createEntityManager();
        try {
            itinerary = em.find(Itinerary.class, primaryKey);
            Hibernate.initialize(itinerary.getSites());
        } finally {
            em.close();
        }
        return itinerary;
    }

    private List<ItinerarySiteData> selectSiteDatas(Long savedId) throws SQLException {
        List<ItinerarySiteData> sites = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from itinerary_site where itinerary_id = ? order by list_idx");
            pstmt.setLong(1, savedId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sites.add(new ItinerarySiteData(rs.getLong(1), rs.getInt(2), rs.getString(3)));
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
            List<String> sites = Arrays.asList("경복궁", "청계천", "명동", "인사동");
            Itinerary itinerary = new Itinerary("광화문-종로 인근", "설명", sites);
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

        List<ItinerarySiteData> sites = selectSiteDatas(savedId);

        assertThat(sites, hasSize(4));
        String[] expectedSites = {"경복궁", "청계천", "명동", "인사동"};
        for (int i = 0; i < expectedSites.length; i++) {
            assertThat(sites.get(i).listIdx, equalTo(i));
            assertThat(sites.get(i).site, equalTo(expectedSites[i]));
        }
    }

    @Test
    public void find() throws Exception {
        Itinerary itinerary = findItinerary(1L);

        assertThat(itinerary, notNullValue());
        assertSites(1L, itinerary.getSites());
    }

    @Test
    public void find_and_change_sites() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            List<String> sites = Arrays.asList("정림사지", "궁남지", "부여박물관");
            itinerary.changeSites(sites);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Itinerary itinerary = findItinerary(1L);
        assertThat(itinerary, notNullValue());
        assertSites(1L, itinerary.getSites());
    }

    @Test
    public void find_and_remove_site_list_element() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            itinerary.getSites().remove(1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        Itinerary itinerary = findItinerary(1L);
        assertThat(itinerary, notNullValue());
        assertSites(1L, itinerary.getSites());
    }

    @Test
    public void find_and_update_somesite_element() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            List<String> sites = itinerary.getSites();
            sites.set(1, "낙화암-금강 유람선");
            sites.add("백제문화단지");
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        List<ItinerarySiteData> siteDataList = selectSiteDatas(1L);
        System.out.println(siteDataList.stream().map(site -> site.toString()).collect(Collectors.joining("\n")));
    }


    @Test
    public void find_and_update_site_element_to_null() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            itinerary.getSites().set(1, null);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        List<ItinerarySiteData> siteDataList = selectSiteDatas(1L);
        System.out.println(siteDataList.toString());
        assertThat(siteDataList, hasSize(3));
        assertThat(siteDataList.get(0).listIdx, equalTo(0));
        assertThat(siteDataList.get(1).listIdx, equalTo(2));
        assertThat(siteDataList.get(2).listIdx, equalTo(3)); // !!

        Itinerary itinerary = findItinerary(1L);
        List<String> sites = itinerary.getSites();
        assertThat(sites, hasSize(4));
        assertThat(sites.get(1), nullValue());
    }

    @Test
    public void find_and_update_site_last_element_to_null() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            itinerary.getSites().set(3, null);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        List<ItinerarySiteData> siteDataList = selectSiteDatas(1L);
        System.out.println(siteDataList.toString());
        assertThat(siteDataList, hasSize(3));
        assertThat(siteDataList.get(0).listIdx, equalTo(0));
        assertThat(siteDataList.get(1).listIdx, equalTo(1));
        assertThat(siteDataList.get(2).listIdx, equalTo(2));

        Itinerary itinerary = findItinerary(1L);
        List<String> sites = itinerary.getSites();
        assertThat(sites, hasSize(3));
    }

    @Test
    public void find_and_clear_site_list() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            itinerary.clearSites();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        Itinerary itinerary = findItinerary(1L);
        assertThat(itinerary.getSites(), hasSize(0));
        assertSites(1L, itinerary.getSites());
    }

    @Test
    public void find_and_set_sites_to_null() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Itinerary itinerary = em.find(Itinerary.class, 1L);
            itinerary.changeSites(null);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        Itinerary itinerary = findItinerary(1L);
        assertThat(itinerary.getSites(), hasSize(0));
        assertSites(1L, itinerary.getSites());
    }

    private void assertSites(Long id, List<String> sitess) throws SQLException {
        List<ItinerarySiteData> siteDataList = selectSiteDatas(id);
        assertThat(sitess, hasSize(siteDataList.size()));
        for (int i = 0; i < siteDataList.size(); i++) {
            assertThat(
                    sitess.get(i),
                    equalTo(siteDataList.get(i).site));
        }
    }
    private void assertSites(Long id) throws SQLException {
        assertSites(id, findItinerary(id).getSites());
    }

    private class ItinerarySiteData {
        public final Long itineraryId;
        public final int listIdx;
        public final String site;

        public ItinerarySiteData(Long itineraryId, int listIdx, String site) {
            this.itineraryId = itineraryId;
            this.listIdx = listIdx;
            this.site = site;
        }

        @Override
        public String toString() {
            return "ItinerarySiteData{" +
                    "itineraryId=" + itineraryId +
                    ", listIdx=" + listIdx +
                    ", site='" + site + '\'' +
                    '}';
        }
    }
}
