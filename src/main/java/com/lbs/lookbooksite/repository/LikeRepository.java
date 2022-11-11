package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Like;
import com.lbs.lookbooksite.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByTargetBoardAndLikedMember(Board boardId, Member memberId);
}
