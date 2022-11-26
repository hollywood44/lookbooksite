package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.LookBook;
import com.lbs.lookbooksite.domain.LookBook_Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookBook_ImageRepository extends JpaRepository<LookBook_Image,Long> {
}
