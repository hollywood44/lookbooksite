package com.lbs.lookbooksite.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private Long noticeId;
    private String notice;

    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof NoticeDto)) {
            return false;
        }
        NoticeDto otherMember = (NoticeDto) anObject;
        return (otherMember.getNoticeId() == this.getNoticeId());
    }

}
