package brorica.gather.repository;

import brorica.gather.domain.MemberList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberListRepository {

    private final EntityManager em;

    public List<MemberList> findByTeamId(Long id) {
        return em.createQuery("select m from MemberList as m where m.team.id = :team_id",
            MemberList.class)
            .setParameter("team_id", id)
            .getResultList();
    }
}
