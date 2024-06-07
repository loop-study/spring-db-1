package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV4_1;
import hello.jdbc.repository.MemberRepositoryV4_2;
import hello.jdbc.repository.MemberRepositoryV5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 트랜잭션 - @Transactional AOP 방법.
 */
@Slf4j
public class MemberServiceV4 {

    private final MemberRepositoryV5 memberRepository;

    public MemberServiceV4(MemberRepositoryV5 memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {

        bisLogic(fromId, toId, money);
    }

    private void bisLogic(String fromId, String toId, int money) {
        // 모두 같은 커넥션을 써야 한다. 같은 세션쓰기 위함임.
        Member fromMember = memberRepository.findById( fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
