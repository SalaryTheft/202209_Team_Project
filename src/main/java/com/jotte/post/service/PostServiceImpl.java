package com.jotte.post.service;

import com.jotte.common.vo.SearchVO;
import com.jotte.post.dao.IPostDAO;
import com.jotte.board.vo.BoardVO;
import com.jotte.post.vo.PostAjaxSearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final IPostDAO postDAO;

    @Override
    public int insertPost(PostVO post) throws BizNotEffectedException {
        post.setPostUuid(UUID.randomUUID().toString());
        post.setPostContentText(
                Jsoup.parse(post.getPostContent()).text().trim()
                        .replaceAll(" +", " ")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
        );
        int result = postDAO.insertPost(post);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public int updatePost(PostVO post) throws BizNotEffectedException {
        post.setPostContentText(
                Jsoup.parse(post.getPostContent()).text().trim()
                        .replaceAll(" +", " ")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
        );
        int result = postDAO.updatePost(post);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public int deletePost(PostVO post) throws BizNotEffectedException {
        int result = postDAO.deletePost(post);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public PostVO getPostByUuid(String postUuid) {
        return postDAO.getPostByUuid(postUuid);
    }

    @Override
    public List<PostVO> getPostList(SearchVO search) {
        search.setTotalRows(getPostCount(search));
        search.setPages();
        return postDAO.getPostList(search);
    }

    @Override
    public int getPostCount(SearchVO search) {
        return postDAO.getPostCount(search);
    }

    @Override
    public List<PostVO> getPostListAjax(BoardVO board, PostAjaxSearchVO search, UserVO user) {
        return postDAO.getPostListAjax(board, search, user);
    }

    @Override
    public List<String> getBestPostList() {
        return postDAO.getBestPostList();
    }

    @Override
    public List<PostVO> getNewPostListAjax(PostAjaxSearchVO search, UserVO user) {
        return postDAO.getNewPostListAjax(search, user);
    }

    @Override
    public PostVO getPostByUuidAjax(String postUuid, UserVO user) {
        return postDAO.getPostByUuidAjax(postUuid, user);
    }

//    @Override
//    public String getFirstPostTimestamp(BoardVO board, PostAjaxSearchVO search) {
//        return postDAO.getFirstPostTimestamp(board, search);
//    }
}
