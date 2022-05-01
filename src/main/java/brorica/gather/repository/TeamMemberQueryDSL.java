package brorica.gather.repository;

import brorica.gather.domain.QTeamMember;
import brorica.gather.domain.TeamMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TeamMemberQueryDSL extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QTeamMember teamMember;

    public TeamMemberQueryDSL(final JPAQueryFactory jpaQueryFactory) {
        super(TeamMember.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamMember = QTeamMember.teamMember;
    }

    public Optional<TeamMember> exist(Long teamId, Long memberId) {
        TeamMember findTeamMember = jpaQueryFactory
            .selectFrom(teamMember)
            .where(teamMember.team.id.eq(teamId),
                teamMember.member.id.eq(memberId))
            .fetchFirst();
        return Optional.ofNullable(findTeamMember);
    }
}
