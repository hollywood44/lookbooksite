package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findAll(Pageable pageable);
}
