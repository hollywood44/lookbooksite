package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> ,BoardRepositoryCustom{

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByWriter(Member writer, Pageable pageable);
}
