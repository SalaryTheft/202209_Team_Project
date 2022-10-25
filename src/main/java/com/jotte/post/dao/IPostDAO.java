package com.jotte.post.dao;

import com.jotte.board.vo.BoardVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.vo.PostAjaxSearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IPostDAO {
    int insertPost(PostVO post);

    int updatePost(PostVO post);

    int deletePost(PostVO post);

    PostVO getPostByUuid(@Param("postUuid") String postUuid);

    List<PostVO> getPostList(SearchVO search);

    int getPostCount(SearchVO search);

    List<PostVO> getPostListAjax(@Param("board") BoardVO board,
                             @Param("search") PostAjaxSearchVO search,
                             @Param("user") UserVO user);

    List<String> getBestPostList();

    List<PostVO> getNewPostListAjax(@Param("search") PostAjaxSearchVO search,
                                    @Param("user") UserVO user);

    PostVO getPostByUuidAjax(@Param("postUuid") String postUuid,
                             @Param("user") UserVO user);
}
