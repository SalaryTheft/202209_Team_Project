package com.jotte.comment.service;

import com.jotte.comment.dao.ICommentDAO;
import com.jotte.comment.vo.CommentAjaxSearchVO;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.noti.service.INotiService;
import com.jotte.post.vo.PostVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentDAO commentDAO;

    private final INotiService notiService;

    @Override
    public CommentVO insertComment(CommentVO comment) throws BizNotEffectedException {
        String commentUuid = UUID.randomUUID().toString();
        comment.setCommentUuid(commentUuid);
        comment.setCommentContentText(
                Jsoup.parse(comment.getCommentContent()).text().trim()
                        .replaceAll(" +", " ")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
        );
        int cnt = commentDAO.insertComment(comment);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }

        try {
            notiService.insertNoti(comment);
        } catch (BizNotEffectedException e) {
            // 알림을 보내지 못해도 댓글은 작성되어야 함
        }

        return commentDAO.getCommentByUuid(commentUuid);
    }

    @Override
    public CommentVO updateComment(CommentVO comment) throws BizNotEffectedException {
        comment.setCommentContentText(
                Jsoup.parse(comment.getCommentContent()).text().trim()
                        .replaceAll(" +", " ")
                        .replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;")
        );
        int cnt = commentDAO.updateComment(comment);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return commentDAO.getCommentByUuid(comment.getCommentUuid());
    }

    @Override
    public int deleteComment(CommentVO comment) throws BizNotEffectedException {
        int cnt = commentDAO.deleteComment(comment);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public CommentVO getCommentByUuid(String Uuid) throws BizNotFoundException {
        CommentVO comment = commentDAO.getCommentByUuid(Uuid);
        if (comment == null) {
            throw new BizNotFoundException();
        }
        return comment;
    }

    @Override
    public List<CommentVO> getCommentList(SearchVO search) {
        search.setTotalRows(getCommentCount(search));
        search.setPages();
        return commentDAO.getCommentList(search);
    }

    @Override
    public int getCommentCount(SearchVO search) {
        return commentDAO.getCommentCount(search);
    }

    @Override
    public List<CommentVO> getCommentListAjax(PostVO post, CommentAjaxSearchVO search, UserVO user) {
        return commentDAO.getCommentListAjax(post, search, user);
    }

    @Override
    public String getFirstCommentTimestamp(PostVO post, CommentAjaxSearchVO search) {
        return commentDAO.getFirstCommentTimestamp(post, search);
    }
}
