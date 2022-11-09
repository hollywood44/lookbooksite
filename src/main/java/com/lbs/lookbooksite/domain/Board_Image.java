package com.lbs.lookbooksite.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_image_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String storedName;

    private String originName;

    private String storedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public void setBoardId(Board board) {
        this.board = board;
    }
}
