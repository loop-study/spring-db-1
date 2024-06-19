package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import java.rmi.UnexpectedException;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired LogRepository logRepository;

    //===================================================
    //          Service @Transactional 없음
    //===================================================
    /**
     * memberService        @Transactional : OFF
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON
     */
    @Test
    public void outerTxOff_success() throws Exception {
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then
        assertThat(memberRepository.find(username).isPresent()).isTrue();
        assertThat(logRepository.find(username).isPresent()).isTrue();
    }

    /**
     * memberService        @Transactional : OFF
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON Exception
     */
    @Test
    public void outerTxOff_fail() {
        //given
        String username = "로그예외_outerTxOff_success";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then
        assertThat(memberRepository.find(username).isPresent()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }



    //===================================================
    //          Service @Transactional 적용
    //===================================================

    /**
     * memberService        @Transactional : ON
     * memberRepository     @Transactional : OFF
     * LogRepository        @Transactional : OFF
     */
    @Test
    public void singleTx() {
        //given
        String username = "singleTx";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then
        assertThat(memberRepository.find(username).isPresent()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }

    /**
     * memberService        @Transactional : ON
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON
     */
    @Test
    public void outerTxOn_success() {
        String username = "outerTxOn_success";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then
        assertThat(memberRepository.find(username).isPresent()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }

    /**
     * memberService        @Transactional : ON
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON Exception
     */
    @Test
    public void outerTxOn_fail() {
        //given
        String username = "로그예외_outerTxOn_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then 모든 데이터 롤백
        assertThat(memberRepository.find(username).isEmpty()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }

    /**
     * memberService        @Transactional : ON
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON Exception
     */
    @Test
    public void recoverException_fail() {
        //given
        String username = "로그예외_recoverException_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then 모든 데이터 롤백
        assertThat(memberRepository.find(username).isEmpty()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }
    /**
     * memberService        @Transactional : ON
     * memberRepository     @Transactional : ON
     * LogRepository        @Transactional : ON(Requires_new) Exception
     */
    @Test
    public void recoverException_success() {
        //given
        String username = "로그예외_recoverException_success";

        //when
        memberService.joinV2(username);

        //then member 저장, log 롤백
        assertThat(memberRepository.find(username).isPresent()).isTrue();
        assertThat(logRepository.find(username).isEmpty()).isTrue();
    }
}