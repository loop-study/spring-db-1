package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void saveMember() throws SQLException{
        // save
        Member member = new Member("memberV2", 10000);
        memberRepositoryV0.save(member);

        // findById
        Member findMember = memberRepositoryV0.findById(member.getMemberId());
        log.info("find member : {}", findMember);

        // update 돈 변경 10000 -> 20000
        memberRepositoryV0.update(member.getMemberId(), 20000);
        Member updateMameber = memberRepositoryV0.findById(member.getMemberId());

//        assertThat(updateMameber).isEqualTo(member);
        assertThat(updateMameber.getMoney()).isEqualTo(20000);

        // delete
        memberRepositoryV0.delete(member.getMemberId());
        assertThatThrownBy(() -> memberRepositoryV0.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}