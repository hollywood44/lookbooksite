package com.lbs.lookbooksite.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lookbook_image_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LookBook_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String storedName;

    private String originName;

    private String storedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lookbookId")
    private LookBook lookBook;



    public void setLookBook(LookBook lookBook) {
        this.lookBook = lookBook;
    }


}
