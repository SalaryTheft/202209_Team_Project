package com.jotte.noti.service;

import com.jotte.comment.vo.CommentVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.noti.vo.NotiVO;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface INotiService {
    List<NotiVO> getNotiList(UserVO user);

    int insertNoti(CommentVO comment) throws BizNotEffectedException;

    int deleteNoti(UserVO user) throws BizNotEffectedException;

    int markAsRead(UserVO user, String notiUuid) throws BizNotEffectedException;
}
