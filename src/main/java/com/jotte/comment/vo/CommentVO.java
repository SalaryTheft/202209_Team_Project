package com.jotte.comment.vo;

import com.jotte.board.vo.BoardVO;
import com.jotte.common.gson.JsonExclude;
import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

@Data
public class CommentVO {
    private String postUuid;
    @JsonExclude
    private String userUuid;
    private String commentUuid;
    private String commentContent;
    private String commentContentText;
    private String commentTimestamp;
    private String commentUpdateTimestamp;
    private String commentDeleteYn;
    private String commentParentUuid;
    List<CommentVO> child;

    private UserVO user;
    private PostVO post;
    private BoardVO board;

    private int commentLikeCount;
    private String commentUserLikeStatus;

    public String getCommentTimestampShort() {
        SimpleDateFormat oracleTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return shortDateFormat.format(oracleTimestampFormat.parse(commentTimestamp));
        } catch (ParseException e) {
            return commentTimestamp;
        }
    }

    public String getCommentTimestampOffset() {
        Instant instant = Timestamp.valueOf(commentTimestamp).toInstant().minusNanos(1000);
        return instant.toString().replace("T", " ").replace("Z", "");
    }

    public String getCommentContentTextShort() {
        if (commentContentText == null) {
            return "미리보기 없음";
        }
        if (commentContentText.length() > 100) {
            return commentContentText.substring(0, 100) + "…";
        } else {
            return commentContentText;
        }
    }

}
