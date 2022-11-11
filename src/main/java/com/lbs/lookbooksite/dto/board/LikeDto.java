package com.lbs.lookbooksite.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {

    private Long likeId;
    private Long targetBoardId;
    private String likedMemberId;
}
