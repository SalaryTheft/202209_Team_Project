package com.jotte.post.vo;

import com.jotte.board.vo.BoardVO;
import com.jotte.common.gson.JsonExclude;
import com.jotte.user.vo.UserVO;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class PostVO {
    @JsonExclude
    private String userUuid;
    private String boardId;
    private String postUuid;
    private String postTitle;
    private String postContent;
    private String postContentText;
    private String postTab;
    private String postTimestamp;
    private String postUpdateTimestamp;

    // MyBatis Association
    private BoardVO board;
    private UserVO user;

    private String postUserLikeStatus;
    private int postLikeCount;
    private int postCommentCount;

    public String getPostTimeStampShort() {
        SimpleDateFormat oracleTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return shortDateFormat.format(oracleTimestampFormat.parse(postTimestamp));
        } catch (Exception e) {
            return postTimestamp;
        }
    }

    public String getPostContentTextShort() {
        if (this.postContentText == null || this.postContentText.trim().equals("")) {
            return "미리보기 없음";
        } else if (this.postContentText.length() > 300) {
            return this.postContentText.substring(0, 300);
        } else {
            return this.postContentText;
        }
    }
}
