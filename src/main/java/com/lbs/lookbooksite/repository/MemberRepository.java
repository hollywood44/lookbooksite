package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String > {

    Optional<Member> findByPhone(String phone);
}
