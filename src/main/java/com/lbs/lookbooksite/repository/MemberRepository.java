package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String > {
}
