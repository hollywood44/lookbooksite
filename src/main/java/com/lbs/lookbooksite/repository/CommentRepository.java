package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
