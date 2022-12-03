package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Optional<Report> findByTargetBoardAndSendMember(Board board, Member member);
}
