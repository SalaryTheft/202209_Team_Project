package com.jotte.home.web;

import com.google.gson.GsonBuilder;
import com.jotte.board.service.IBoardService;
import com.jotte.board.vo.BoardVO;
import com.jotte.comment.service.ICommentService;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.gson.JsonExcludeStrategy;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.service.IPostService;
import com.jotte.post.vo.PostAjaxSearchVO;
import com.jotte.post.vo.PostVO;
import com.jotte.user.service.IUserService;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final IPostService postService;
    private final IBoardService boardService;
    private final ICommentService commentService;
    private final IUserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("bestBoardList", boardService.getBestBoardList(5));
        return "index";
    }

    @RequestMapping(value = "/feed/best", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getBestPostList(String uuid, HttpServletRequest request) {
        if (uuid == null || "".equals(uuid)) {
            try {
                List<String> bestPostList = postService.getBestPostList();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
                return gsonBuilder.create().toJson(bestPostList);
            } catch (Exception e) {
                return new JsonMsgVO("fail", "인기 게시글 목록을 불러오는데 실패했습니다.").toString();
            }
        } else {
            try {
                UserVO user = (UserVO) request.getSession().getAttribute("user");
                PostVO post = postService.getPostByUuidAjax(uuid, user);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
                return gsonBuilder.create().toJson(post);
            } catch (Exception e) {
                return new JsonMsgVO("fail", "게시글을 불러오는데 실패했습니다.").toString();
            }
        }
    }

    @RequestMapping(value = "/feed/new", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getNewPostList(HttpServletRequest request, String timestamp) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        PostAjaxSearchVO search = new PostAjaxSearchVO();
        search.setTimestamp(timestamp);
        List<PostVO> newPostList = postService.getNewPostListAjax(search, user);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
        return gsonBuilder.create().toJson(newPostList);
    }

    @RequestMapping(value = "/feed/sub", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSubPostList(HttpServletRequest request, String timestamp) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        if (user == null) {
            return new JsonMsgVO("fail", "로그인이 필요합니다.").toString();
        }
        PostAjaxSearchVO search = new PostAjaxSearchVO();
        search.setTimestamp(timestamp);
        List<String> subBoardList = boardService.getSubBoardList(user);
        if (subBoardList.size() == 0) {
            return new JsonMsgVO("fail", "구독한 게시판이 없습니다.").toString();
        }
        search.setSubBoardList(subBoardList);
        List<PostVO> subPostList = postService.getPostListAjax(null, search, user);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(JsonExcludeStrategy.strategy);
        return gsonBuilder.create().toJson(subPostList);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(String category, @ModelAttribute("paging") SearchVO search, Model model) {
        if ("posts".equals(category) || category == null) {
            List<PostVO> postList = postService.getPostList(search);
            model.addAttribute("postList", postList);
        } else if ("comments".equals(category)) {
            List<CommentVO> postList = commentService.getCommentList(search);
            model.addAttribute("commentList", postList);
        } else if ("users".equals(category)) {
            List<UserVO> userList = userService.getUserList(search);
            model.addAttribute("userList", userList);
        } else if ("boards".equals(category)) {
            List<BoardVO> boardList = boardService.getBoardList(search);
            model.addAttribute("boardList", boardList);
        }
        return "search";
    }

    /**
     * 세션에 리디렉션할 URI 저장
     *
     * @param redirect_uri 리디렉션할 URI
     */
    @RequestMapping(value = "/set_redirect_uri", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String setRedirectUriAction(HttpServletRequest request, String redirect_uri) {
        HttpSession session = request.getSession();
        session.setAttribute("redirect_uri", redirect_uri);
        return new JsonMsgVO("success", "리다이렉트 URI 설정 성공").toString();
    }
}
