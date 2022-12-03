package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> search(String keyword,String condition, Pageable pageable);
}
