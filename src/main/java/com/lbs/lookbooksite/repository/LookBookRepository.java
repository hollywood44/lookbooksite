package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.LookBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookBookRepository extends JpaRepository<LookBook,Long> {
}
