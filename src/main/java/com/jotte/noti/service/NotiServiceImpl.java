package com.jotte.noti.service;

import com.jotte.comment.dao.ICommentDAO;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.noti.dao.INotiDAO;
import com.jotte.noti.vo.NotiVO;
import com.jotte.post.dao.IPostDAO;
import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiServiceImpl implements INotiService {

    private final INotiDAO notiDAO;
    private final IPostDAO postDAO;
    private final ICommentDAO commentDAO;


    @Override
    public List<NotiVO> getNotiList(UserVO user) {
        return notiDAO.getNotiList(user.getUserUuid());
    }

    @Override
    public int insertNoti(CommentVO comment) throws BizNotEffectedException {
        NotiVO noti = new NotiVO();
        noti.setNotiUuid(java.util.UUID.randomUUID().toString());
        if (comment.getCommentParentUuid() == null) {
            // 게시글 댓글이므로 받는 사람은 게시글 작성자
            PostVO post = postDAO.getPostByUuid(comment.getPostUuid());
            noti.setUserUuid(post.getUserUuid());
            noti.setPostUuid(comment.getPostUuid());
            noti.setCommentTimestamp(commentDAO.getCommentByUuid(comment.getCommentUuid()).getCommentTimestamp());
            if (post.getUserUuid().equals(comment.getUserUuid())) {
                // 자기 자신의 게시글에 댓글을 달았을 경우
                // return 0; // 테스트
            }
        } else {
            // 대댓글이므로 받는 사람은 댓글 작성자
            CommentVO parentComment = commentDAO.getCommentByUuid(comment.getCommentParentUuid());
            if (parentComment.getCommentParentUuid() != null) {
                // 대대댓글이므로 최상위 댓글의 Timestamp를 가져옴
                parentComment = commentDAO.getCommentByUuid(parentComment.getCommentParentUuid());
            }
            noti.setUserUuid(parentComment.getUserUuid());
            if (parentComment.getUserUuid().equals(comment.getUserUuid())) {
                // 자기 자신의 댓글에 대댓글을 달았을 경우
                // return 0; // 테스트
            }
            noti.setCommentTimestamp(parentComment.getCommentTimestamp());
        }
        noti.setCommentUuid(comment.getCommentUuid());
        noti.setCommentUserUuid(comment.getUserUuid());
        String commentContent = Jsoup.parse(comment.getCommentContent()).text().trim();
        if (commentContent.length() > 20) {
            commentContent = commentContent.substring(0, 20);
        } else if (commentContent.length() == 0) {
            commentContent = "댓글 미리보기 없음";
        }
        noti.setCommentContent(commentContent);
        noti.setPostUuid(comment.getPostUuid());

        int result = notiDAO.insertNoti(noti);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public int deleteNoti(UserVO user) throws BizNotEffectedException {
        int result = notiDAO.deleteNoti(user.getUserUuid());
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public int markAsRead(UserVO user, String notiUuid) throws BizNotEffectedException {
        int result = notiDAO.markAsRead(user.getUserUuid(), notiUuid);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }
}
