package com.jotte.noti.vo;

import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import lombok.Data;

@Data
public class NotiVO {
    private String notiUuid;

    private String userUuid;
    private String commentUuid;
    private String commentTimestamp;
    private String commentUserUuid;
    private String commentContent;
    private String postUuid;
    private String notiTimestamp;
    private String notiReadYn;

    private UserVO commentUser;
    private PostVO commentPost;
}
