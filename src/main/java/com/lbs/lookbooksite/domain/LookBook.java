package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lookbook_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LookBook extends BaseTimeEntity {

    // 룩북 게시글 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lookbookId;

    // 제목
    @Column(length = 50)
    private String lookbookTitle;

    // 브랜드
    @Column(length = 50)
    private String brand;

    // 사진 경로
    @Column(length = 100)
    private String photoUrl;

    // 스타일 태그
    @Column(length = 100)
    private String styleTag;


    //===============================비즈니스 로직===============================//

    public void addStyleTag(String tag) {
        this.styleTag = this.styleTag+tag;
    }


}
