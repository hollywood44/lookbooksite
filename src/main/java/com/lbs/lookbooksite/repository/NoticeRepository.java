package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
}
