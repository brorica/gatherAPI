package brorica.gather.repository;

import brorica.gather.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    public void save(Team team) {
        em.persist(team);
    }

    public Team findOne(Long id) {
        return em.find(Team.class, id);
    }

    public List<Team> findByName(String name) {
        return em.createQuery("select t from team t where t.name = :name", Team.class)
            .setParameter("name", name)
            .getResultList();
    }
}
