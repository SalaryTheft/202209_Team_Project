package com.jotte.comment.web;

import com.google.gson.GsonBuilder;
import com.jotte.board.service.IBoardService;
import com.jotte.comment.service.ICommentService;
import com.jotte.noti.service.INotiService;
import com.jotte.post.service.IPostService;
import com.jotte.board.vo.BoardVO;
import com.jotte.comment.vo.CommentAjaxSearchVO;
import com.jotte.comment.vo.CommentVO;
import com.jotte.post.vo.PostVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.gson.JsonExcludeStrategy;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final IBoardService boardService;
    private final IPostService postService;
    private final INotiService notiService;

    // TODO : 댓글 Validation 해야됨

    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment",
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String commentAjax(@PathVariable String boardId, @PathVariable String postUuid, CommentAjaxSearchVO search, HttpServletRequest request) {
        PostVO post = postService.getPostByUuid(postUuid);
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        if (search.getTimestamp() == null || search.getTimestamp().equals("")) {
            search.setTimestamp(commentService.getFirstCommentTimestamp(post, search));
        }
        List<CommentVO> comments = commentService.getCommentListAjax(post, search, user);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
        return gsonBuilder.create().toJson(comments);
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/write",
            method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String writeComment(@PathVariable String boardId, @PathVariable String postUuid, CommentVO comment, HttpServletRequest request) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            comment.setPostUuid(postUuid);
            comment.setUserUuid(user.getUserUuid());
            CommentVO result = commentService.insertComment(comment);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
            return gsonBuilder.create().toJson(result);
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("error", "댓글 작성 실패").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/{commentUuid}/reply", method = RequestMethod.GET)
    public String writeSubComment(@PathVariable String boardId, @PathVariable String commentUuid, Model model, @PathVariable String postUuid) {
        try {
            BoardVO board = boardService.getBoardById(boardId);
            model.addAttribute("board", board);
            CommentVO targetComment = commentService.getCommentByUuid(commentUuid);
            model.addAttribute("targetComment", targetComment);
            return "board/board_comment_editor";
        } catch (BizNotFoundException e) {
            return ""; // TODO : 에러 메시지 표시해야됨
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/{commentUuid}/reply",
            method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String writeSubCommentAction(@PathVariable String commentUuid, CommentVO comment, HttpServletRequest request, @PathVariable String boardId) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            CommentVO targetComment = commentService.getCommentByUuid(commentUuid);
            comment.setCommentParentUuid(targetComment.getCommentUuid());
            comment.setPostUuid(targetComment.getPostUuid());
            comment.setUserUuid(user.getUserUuid());
            CommentVO result = commentService.insertComment(comment);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
            return gsonBuilder.create().toJson(result);
        } catch (BizNotEffectedException | BizNotFoundException e) {
            return new JsonMsgVO("fail", "대상 댓글이 존재하지 않거나 작성에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/{commentUuid}/edit", method = RequestMethod.GET)
    public String editComment(@PathVariable String boardId, @PathVariable String commentUuid, Model model, @PathVariable String postUuid) {
        try {
            BoardVO board = boardService.getBoardById(boardId);
            model.addAttribute("board", board);
            CommentVO comment = commentService.getCommentByUuid(commentUuid);
            model.addAttribute("comment", comment);
            return "board/board_comment_editor";
        } catch (BizNotFoundException e) {
            return ""; // TODO : 에러 메시지 표시해야됨
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/{commentUuid}/edit",
            method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String editCommentAction(@PathVariable String commentUuid, CommentVO comment, HttpServletRequest request, @PathVariable String boardId) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            comment.setCommentUuid(commentUuid);
            comment.setUserUuid(user.getUserUuid());
            CommentVO result = commentService.updateComment(comment);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
            return gsonBuilder.create().toJson(result);
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "대상 댓글이 존재하지 않거나 수정에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/comment/{commentUuid}/delete",
            method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String deleteCommentAction(@PathVariable String commentUuid, HttpServletRequest request, @PathVariable String boardId) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            CommentVO comment = commentService.getCommentByUuid(commentUuid);
            if (!comment.getUserUuid().equals(user.getUserUuid())) {
                return new JsonMsgVO("fail", "권한이 없습니다.").toString();
            } else {
                commentService.deleteComment(comment);
                return new JsonMsgVO("success", "삭제되었습니다.").toString();
            }
        } catch (BizNotEffectedException | BizNotFoundException e) {
            return new JsonMsgVO("fail", "대상 댓글이 존재하지 않거나 삭제에 실패했습니다.").toString();
        }
    }
}
