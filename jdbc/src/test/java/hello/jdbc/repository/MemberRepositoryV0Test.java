package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void saveMember() throws SQLException{
        // save
        Member member = new Member("memberV1", 10000);
        memberRepositoryV0.save(member);

        // findById
        Member findMember = memberRepositoryV0.findById(member.getMemberId());
        log.info("find member : {}", findMember);

    }
}