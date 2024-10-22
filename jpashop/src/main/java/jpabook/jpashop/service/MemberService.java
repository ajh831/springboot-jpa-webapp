package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
            회원 가입
    */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
        회원 전체 조회
    */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

/*
    // Spring Data JPA 적용 전
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
*/
    // Spring Data JPA 적용 후
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
/*
        // Spring Data JPA 적용 전
        Member member = memberRepository.findOne(id);
*/
        Member member = memberRepository.findById(id).get();
        member.setName(name);
        /*
            영속상태의 Member를 setName으로 이름을 바꿔주면
            메서드가 종료될 때, Spring AOP가 @Transactional 애너테이션에 의해 트랜잭션을 커밋한다.
            트랜잭션 커밋 시점에 JPA는 자동으로 flush()를 호출하여, 영속성 컨텍스트에 있는 변경된 엔티티를 데이터베이스에 반영한다.
            이때 변경된 엔티티에 대한 SQL 쿼리가 실행된다.
            트랜잭션 커밋이 완료되면, 데이터베이스에 최종적으로 변경 사항이 commit()을 통해 적용된다.
         */
    }
}
