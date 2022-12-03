package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import static com.lbs.lookbooksite.domain.QBoard.board;

import com.lbs.lookbooksite.domain.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;



public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Board> search(String keyword,String condition, Pageable pageable) {
        List<Board> results = null;
        Long count = 0L;
        switch (condition) {
            case "all":
                results = queryFactory
                        .selectFrom(board)
                        .where(
                                containWriter(keyword)
                                        .or(containContent(keyword))
                                        .or(containTitle(keyword))
                        )
                        .offset(pageable.getOffset()) /*offset*/
                        .limit(pageable.getPageSize())/*limit*/
                        .orderBy(board.boardId.desc())
                        .fetch();
                return new PageImpl<>(results);
            case "title":
                results = queryFactory
                        .selectFrom(board)
                        .where(
                                containTitle(keyword)
                        )
                        .offset(pageable.getOffset()) /*offset*/
                        .limit(pageable.getPageSize())/*limit*/
                        .fetch();
                return new PageImpl<>(results);
            case "content":
                results = queryFactory
                        .selectFrom(board)
                        .where(
                                containContent(keyword)
                        )
                        .offset(pageable.getOffset()) /*offset*/
                        .limit(pageable.getPageSize())/*limit*/
                        .fetch();
                return new PageImpl<>(results);
            case "writer":
                results = queryFactory
                        .selectFrom(board)
                        .where(
                                containWriter(keyword)
                        )
                        .offset(pageable.getOffset()) /*offset*/
                        .limit(pageable.getPageSize())/*limit*/
                        .fetch();
                return new PageImpl<>(results);

        }
        return Page.empty();
    }

    private BooleanExpression containTitle(String titleKeyword) {
        return hasText(titleKeyword) ? board.title.contains(titleKeyword) : null;
    }

    private BooleanExpression containWriter(String writerKeyword) {
        return hasText(writerKeyword) ? board.writer.memberId.contains(writerKeyword) : null;
    }

    private BooleanExpression containContent(String contentKeyword) {
        return hasText(contentKeyword) ? board.content.contains(contentKeyword) : null;
    }
}
