package com.jotte.comment.dao;


import com.jotte.comment.vo.CommentAjaxSearchVO;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommentDAO {
    int insertComment(CommentVO comment);

    int updateComment(CommentVO comment);

    int deleteComment(CommentVO comment);

    CommentVO getCommentByUuid(String Uuid);

    List<CommentVO> getCommentList(SearchVO search);

    int getCommentCount(SearchVO search);

    List<CommentVO> getCommentListAjax(@Param("post") PostVO post,
                                       @Param("search")CommentAjaxSearchVO search,
                                       @Param("user") UserVO user);
    String getFirstCommentTimestamp(@Param("post") PostVO post,
                                    @Param("search")CommentAjaxSearchVO search);
}