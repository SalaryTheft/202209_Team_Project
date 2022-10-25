package com.jotte.comment.service;

import com.jotte.comment.vo.CommentAjaxSearchVO;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface ICommentService {
    CommentVO insertComment(CommentVO comment) throws BizNotEffectedException;

    CommentVO updateComment(CommentVO comment) throws BizNotEffectedException;

    int deleteComment(CommentVO comment) throws BizNotEffectedException;

    CommentVO getCommentByUuid(String Uuid) throws BizNotFoundException;

    List<CommentVO> getCommentList(SearchVO search);

    int getCommentCount(SearchVO search);

    List<CommentVO> getCommentListAjax(PostVO post, CommentAjaxSearchVO search, UserVO user);

    String getFirstCommentTimestamp(PostVO post, CommentAjaxSearchVO search);
}
