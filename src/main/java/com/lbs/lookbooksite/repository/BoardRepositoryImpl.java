package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Board;
import static com.lbs.lookbooksite.domain.QBoard.board;

import com.lbs.lookbooksite.domain.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
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
                return searchAll(keyword, condition, pageable);
            case "title":
                return searchTitle(keyword, condition, pageable);
            case "content":
                return searchContent(keyword, condition, pageable);
            case "writer":
                return searchWriter(keyword, condition, pageable);

        }
        return Page.empty();
    }

    Page<Board> searchAll(String keyword,String condition, Pageable pageable) {
        List<Board> results = queryFactory
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

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        containWriter(keyword)
                                .or(containContent(keyword))
                                .or(containTitle(keyword))
                ).fetchOne();

        return new PageImpl<>(results, pageable, count);
    }
    Page<Board> searchTitle(String keyword,String condition, Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(
                        containTitle(keyword)
                )
                .offset(pageable.getOffset()) /*offset*/
                .limit(pageable.getPageSize())/*limit*/
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        containTitle(keyword)
                ).fetchOne();

        return new PageImpl<>(results, pageable, count);
    }
    Page<Board> searchContent(String keyword,String condition, Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(
                        containContent(keyword)
                )
                .offset(pageable.getOffset()) /*offset*/
                .limit(pageable.getPageSize())/*limit*/
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        containContent(keyword)
                ).fetchOne();

        return new PageImpl<>(results, pageable, count);
    }
    Page<Board> searchWriter(String keyword,String condition, Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(
                        containWriter(keyword)
                )
                .offset(pageable.getOffset()) /*offset*/
                .limit(pageable.getPageSize())/*limit*/
                .fetch();

        Long count = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        containWriter(keyword)
                ).fetchOne();

        return new PageImpl<>(results, pageable, count);
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
