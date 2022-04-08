package brorica.gather.service;

import brorica.gather.repository.MemberListRepository;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberListService {

    private final MemberListRepository memberListRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

}
