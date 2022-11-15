package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // 룩북 설명
    private String description;

    // 스타일 태그
    @Column(length = 100)
    private String styleTag;

    @Builder.Default
    @OneToMany(mappedBy = "lookBook", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LookBook_Image> lookbookImages = new ArrayList<>();

    //<editor-fold desc="메소드">

    public void addImgs(LookBook_Image lookBook_image) {
        lookBook_image.setLookBook(this); //todo 되는지 확인 위아래 바꿈
        lookbookImages.add(lookBook_image);
    }

    //</editor-fold>

}
