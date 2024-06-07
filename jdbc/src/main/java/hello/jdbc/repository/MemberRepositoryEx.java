package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

/**
 * 체크 예외를 인터페이스에 도입을 한다면?
 * 구현체에서 throws를 쓰기위해선 인터페이스 메소드에도 명시해줘야한다.
 *
 * 언체크 예외라면?
 * throws 명시 안해도됨.
 */
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
