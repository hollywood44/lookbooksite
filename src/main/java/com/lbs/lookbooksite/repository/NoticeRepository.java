package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

    List<Notice> findByTargetMemberAndReadDateIsNull(Member member);

    List<Notice> findByTargetMemberOrderByRegDateDesc(Member member);
    Long countByTargetMemberAndReadDateIsNull(Member member);

    Page<Notice> findByTargetMemberAndReadDateIsNotNull(Member member, Pageable pageable);
}
