package com.jotte.like.web;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.like.service.ILikeService;
import com.jotte.like.vo.LikeVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final ILikeService likeService;

    @Auth
    @RequestMapping(value = "/like/upsert", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String like(String target, String type, HttpServletRequest request) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        try {
            likeService.insertLike(new LikeVO(user.getUserUuid(), target, type));
            return new JsonMsgVO("success", "좋아요/싫어요 성공").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "좋아요/싫어요 실패").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/like/remove", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String unlike(String target, HttpServletRequest request) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        try {
            likeService.deleteLike(new LikeVO(user.getUserUuid(), target, null));
            return new JsonMsgVO("success", "좋아요/싫어요 취소 성공").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "좋아요/싫어요 취소 실패").toString();
        }
    }


}
