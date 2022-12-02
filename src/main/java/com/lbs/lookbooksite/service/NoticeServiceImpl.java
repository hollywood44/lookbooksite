package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Notice;
import com.lbs.lookbooksite.dto.NoticeDto;
import com.lbs.lookbooksite.repository.BoardRepository;
import com.lbs.lookbooksite.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;


    @Override
    public void sendCommentNotice(Long boardId, Member member) {
        Board targetBoard = boardRepository.findById(boardId).get();

        String sendNotice = boardId + "번 게시글에 댓글이 달렸습니다.";

        Notice notice = Notice.builder()
                .notice(sendNotice)
                .targetMember(targetBoard.getWriter())
                .sendMember(member)
                .build();

        noticeRepository.save(notice);
    }

    @Override
    public List<NoticeDto> readNotice(Member member) {
        List<Notice> checkNoti = noticeRepository.findByTargetMemberAndReadDateIsNull(member);
        List<NoticeDto> sendNotice = new ArrayList<>();

        if (!checkNoti.isEmpty()) {
            for (Notice noti : checkNoti) {
                NoticeDto dto  = NoticeDto.builder()
                        .noticeId(noti.getNoticeId()).notice(noti.getNotice()).build();
                sendNotice.add(dto);
            }
            for (Notice noti : checkNoti) {
                noti.readNotice();
                noticeRepository.save(noti);
            }
        }
        return sendNotice;
    }

    @Override
    public Long checkNoti(Member member) {
        Long check = noticeRepository.countByTargetMemberAndReadDateIsNull(member);

        if (check != null) {
            return check;
        } else {
            return 0L;
        }
    }

    @Override
    public List<NoticeDto> getAllNotice(Member member) {
        List<Notice> allNotice = noticeRepository.findByTargetMemberOrderByRegDateDesc(member);
        List<NoticeDto> sendNotice = new ArrayList<>();

        if (!allNotice.isEmpty()) {
            for (Notice noti : allNotice) {
                NoticeDto dto = NoticeDto.builder()
                        .noticeId(noti.getNoticeId()).notice(noti.getNotice()).build();
                sendNotice.add(dto);
            }
        }
        return sendNotice;
    }

    @Override
    public Page<NoticeDto> getPrevNotice(Member member,int page) {
        Function<Notice,NoticeDto> fn = (entity-> NoticeDto.builder().
                noticeId(entity.getNoticeId()).notice(entity.getNotice()).build());
        Page<NoticeDto> sendPrevNotice = Page.empty();

        Sort sort = Sort.by("readDate").descending();
        Pageable pageable = PageRequest.of(page, 20, sort);

        Page<Notice> entityPrevNoticeList = noticeRepository.findByTargetMemberAndReadDateIsNotNull(member, pageable);
        if (!entityPrevNoticeList.isEmpty()) {
            sendPrevNotice = entityPrevNoticeList.map(fn);
            }

        return sendPrevNotice;
    }
}
