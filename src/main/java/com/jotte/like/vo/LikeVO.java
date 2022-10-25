package com.jotte.like.vo;

import lombok.Data;

@Data
public class LikeVO {
    private String userUuid;
    private String targetUuid;
    private String likeType;

    public LikeVO(String userUuid, String targetUuid, String likeType) {
        this.userUuid = userUuid;
        this.targetUuid = targetUuid;
        this.likeType = likeType;
    }
}
