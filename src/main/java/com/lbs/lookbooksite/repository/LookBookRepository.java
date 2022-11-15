package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.LookBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookBookRepository extends JpaRepository<LookBook,Long> {

    Page<LookBook> findByStyleTagContains(String styleTag,Pageable pageable);
    Page<LookBook> findAll(Pageable pageable);
}
