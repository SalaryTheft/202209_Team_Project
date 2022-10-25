package com.jotte.post.service;

import com.jotte.board.vo.BoardVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.vo.PostAjaxSearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface IPostService {
    int insertPost(PostVO post) throws BizNotEffectedException;

    int updatePost(PostVO post) throws BizNotEffectedException;

    int deletePost(PostVO post) throws BizNotEffectedException;

    PostVO getPostByUuid(String postUuid);

    List<PostVO> getPostList(SearchVO search);

    int getPostCount(SearchVO search);

    List<PostVO> getPostListAjax(BoardVO board, PostAjaxSearchVO search, UserVO user);

    List<String> getBestPostList();

    List<PostVO> getNewPostListAjax(PostAjaxSearchVO search, UserVO user);

    PostVO getPostByUuidAjax(String postUuid, UserVO user);

//    String getFirstPostTimestamp(BoardVO board, PostAjaxSearchVO search);
}
