package com.jotte.post.web;

import com.google.gson.GsonBuilder;
import com.jotte.board.service.IBoardService;
import com.jotte.board.service.IModService;
import com.jotte.post.service.IPostService;
import com.jotte.board.vo.*;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.gson.JsonExcludeStrategy;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.post.vo.PostAjaxSearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final IBoardService boardService;
    private final IModService modService;
    private final IPostService postService;

    @RequestMapping(value = "/r/{boardId}/post", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPostList(@PathVariable String boardId, PostAjaxSearchVO search, HttpServletRequest request) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            BoardVO board = boardService.getBoardById(boardId);
            List<PostVO> posts = postService.getPostListAjax(board, search, user);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
            return gsonBuilder.create().toJson(posts);
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("fail", "게시판이 존재하지 않습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/write", method = RequestMethod.GET)
    public String writePage(@PathVariable String boardId, Model model) {
        try {
            addBoardInfoAttribute(boardId, model);
            return "board/post_editor";
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/write", method = RequestMethod.POST)
    public String write(@PathVariable String boardId, PostVO post, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            UserVO user = (UserVO) session.getAttribute("user");
            post.setUserUuid(user.getUserUuid());
            post.setBoardId(boardId);
            postService.insertPost(post);
            return "redirect:/r/" + boardId; //TODO: 게시글 작성 후 해당 게시글로 이동
        } catch (BizNotEffectedException e) {
            return "redirect:/error/500";
        }
    }

    @RequestMapping(value = "/r/{boardId}/post/{postUuid}", method = RequestMethod.GET)
    public String postPage(@PathVariable String boardId, @PathVariable String postUuid, Model model, HttpServletRequest request) {
        try {
            addBoardInfoAttribute(boardId, model);
            BoardVO board = boardService.getBoardById(boardId);
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            PostVO post = postService.getPostByUuid(postUuid);
            model.addAttribute("post", post);
            boolean isSubscribed = boardService.isBoardSubscribed(board, user);
            model.addAttribute("isSubscribed", isSubscribed);
            return "board/post_view";
        } catch (BizNotFoundException e) {
            return "redirect:/r/" + boardId;
        }
    }

    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String post(@PathVariable String boardId, @PathVariable String postUuid, HttpServletRequest request) {
        try {
            BoardVO board = boardService.getBoardById(boardId);
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            PostAjaxSearchVO search = new PostAjaxSearchVO();
            search.setUuid(postUuid);
            PostVO post = postService.getPostListAjax(board, search, user).get(0);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
            return gsonBuilder.create().toJson(post);
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("fail", "게시글이 존재하지 않습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/edit", method = RequestMethod.GET)
    public String editPage(@PathVariable String boardId, @PathVariable String postUuid,
                           Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            addBoardInfoAttribute(boardId, model);
            PostVO post = postService.getPostByUuid(postUuid);
            if (user.getUserUuid().equals(post.getUserUuid())) {
                model.addAttribute("post", post);
                return "board/post_editor";
            } else {
                return "redirect:/error/403";
            }
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/edit", method = RequestMethod.POST)
    public String editAction(@PathVariable String boardId, @PathVariable String postUuid,
                             PostVO post, HttpServletRequest request) {
        // TODO : Validation
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            PostVO originPost = postService.getPostByUuid(postUuid);
            if (user.getUserUuid().equals(originPost.getUserUuid())) {
                post.setUserUuid(user.getUserUuid());
                post.setBoardId(boardId);
                post.setPostUuid(postUuid);
                postService.updatePost(post);
                return "redirect:/r/{boardId}/post/{postUuid}"
                        .replace("{boardId}", boardId)
                        .replace("{postUuid}", postUuid);
            } else {
                return "redirect:/error/403";
            }
        } catch (BizNotFoundException | BizNotEffectedException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/post/{postUuid}/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(@PathVariable String boardId, @PathVariable String postUuid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            PostVO post = postService.getPostByUuid(postUuid);
            if (user.getUserUuid().equals(post.getUserUuid())) {
                postService.deletePost(post);
                return new JsonMsgVO("success", "게시글이 삭제되었습니다.").toString();
            } else {
                return new JsonMsgVO("fail", "권한이 없습니다.").toString();
            }
        } catch (BizNotFoundException | BizNotEffectedException e) {
            return new JsonMsgVO("fail", "게시글을 찾을 수 없거나 삭제할 수 없습니다.").toString();
        }
    }

    /**
     * 게시판 정보를 모델에 추가하는 메서드 (게시판, 게시판 탭, 게시판 관리자)
     *
     * @param boardId  게시판 아이디
     * @param model 모델
     * @throws BizNotFoundException 게시판이 존재하지 않을 경우
     */
    public void addBoardInfoAttribute(String boardId, Model model) throws BizNotFoundException {
        BoardVO board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        List<ModVO> boardMods = modService.getModList(board);
        model.addAttribute("boardMods", boardMods);
        List<String> boardTabs = boardService.getBoardTabs(board);
        model.addAttribute("boardTabs", boardTabs);
    }
}
