package com.inha.capstonedesign.member.repository;

import com.inha.capstonedesign.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByMemberEmail(String memberEmail);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Member> findByMemberEmail(String memberEmail);

    @Query("SELECT m From Member m join fetch m.memberImage where m.memberEmail = :memberEmail")
    Optional<Member> findByMemberWithImage(String memberEmail);
}
