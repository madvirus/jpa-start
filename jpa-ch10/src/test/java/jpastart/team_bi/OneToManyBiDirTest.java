package jpastart.team_bi;

import jpastart.jpa.EMF;
import jpastart.jpa.JpaTestBase;
import org.hibernate.Hibernate;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class OneToManyBiDirTest extends JpaTestBase {

    private Team find(String id) {
        Team t1;
        EntityManager em = EMF.createEntityManager();
        try {
            t1 = em.find(Team.class, id);
            Hibernate.initialize(t1.getPlayers());
        } finally {
            em.close();
        }
        return t1;
    }

    private void assertPlayer(Set<Player> players, List<PlayerData> playerDatas) {
        List<Player> plist = players.stream().sorted((p1, p2) -> p1.getId().compareTo(p2.getId())).collect(Collectors.toList());
        List<PlayerData> expectedList = playerDatas.stream().sorted((p1, p2) -> p1.id.compareTo(p2.id)).collect(Collectors.toList());
        for (int i = 0 ; i < plist.size() ; i++) {
            Player player = plist.get(i);
            PlayerData data = expectedList.get(i);
            assertThat(player.getId(), equalTo(data.id));
            assertThat(player.getName(), equalTo(data.name));
        }
    }

    @Test
    public void find() throws Exception {
        Team t1 = find("T1");
        assertThat(t1.getPlayers(), hasSize(2));
        assertPlayer(t1.getPlayers(), asList(data("P1", "선수1"), data("P2", "선수2")));
    }

    @Test
    public void persist() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();

            Team t3 = new Team("T3", "팀3");
            Player p4 = new Player("P4", "선수4");
            Player p5 = new Player("P5", "선수5");
            Player p3 = em.find(Player.class, "P3");

            t3.addPlayer(p4);
            p4.setTeam(t3);
            t3.addPlayer(p5);
            p5.setTeam(t3);
            t3.addPlayer(p3);
            p3.setTeam(t3);

            em.persist(t3);
            em.persist(p4);
            em.persist(p5);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Team t3 = find("T3");
        assertThat(t3.getPlayers(), hasSize(3));
        assertPlayer(t3.getPlayers(),
                asList(
                        data("P3", "선수3"),
                        data("P4", "선수4"),
                        data("P5", "선수5")
                )
        );
    }

    @Test
    public void change() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Team t1 = em.find(Team.class, "T1");
            Team t2 = em.find(Team.class, "T2");
            Optional<Player> pOpt = t1.getPlayers().stream().filter(p -> p.getId().equals("P2")).findFirst();
            Player player = pOpt.get();
            t1.removePlayer(player);
            t2.addPlayer(player);
            player.setTeam(t2);

            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Team t1 = find("T1");
        assertPlayer(t1.getPlayers(),
                asList(data("P1", "선수1"))
        );
        Team t2 = find("T2");
        assertPlayer(t2.getPlayers(),
                asList(data("P2", "선수2"))
        );
    }

    @Test
    public void remove() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Team t1 = em.find(Team.class, "T1");
            Optional<Player> pOpt = t1.getPlayers().stream().filter(p -> p.getId().equals("P2")).findFirst();
            pOpt.ifPresent(p -> {
                t1.removePlayer(p);
                p.setTeam(null);
            } );
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Team t1 = find("T1");
        assertPlayer(t1.getPlayers(),
                Arrays.asList(
                        data("P1", "선수1")
                )
        );
    }

    @Test
    public void clear() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Team t1 = em.find(Team.class, "T1");
            t1.getPlayers().forEach(p -> p.setTeam(null));
            t1.getPlayers().clear();
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Team t1 = find("T1");
        assertThat(t1.getPlayers(), hasSize(0));
    }

    @Test
    public void setNull() throws Exception {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            Team t1 = em.find(Team.class, "T1");
            t1.getPlayers().forEach(p -> p.setTeam(null));
            t1.setPlayers(null);
            em.getTransaction().commit();
        } catch(Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

        Team t1 = find("T1");
        assertThat(t1.getPlayers(), hasSize(0));
    }

    private PlayerData data(String id, String name) {
        return new PlayerData(id, name);
    }

    public static class PlayerData {
        public String id;
        public String name;

        public PlayerData(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
