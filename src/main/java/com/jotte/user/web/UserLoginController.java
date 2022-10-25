package com.jotte.user.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.exception.BizPasswordNotMatchedException;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.service.IUserService;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("signupUser")
@RequiredArgsConstructor
public class UserLoginController {

    private final IUserService userService;

    /**
     * 로그인 페이지
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "user/login";
    }

    /**
     * 로그인 처리(JSON)
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String loginAction(@RequestBody UserVO loginUser, HttpServletRequest request) {
        try {
            UserVO user = userService.login(loginUser);
            request.getSession().setAttribute("user", user);
            return new JsonMsgVO("success", "로그인 성공").toString();
        } catch (BizPasswordNotMatchedException | BizNotFoundException e) {
            return new JsonMsgVO("fail", "아이디 또는 이메일이 존재하지 않거나 비밀번호가 일치하지 않습니다.").toString();
        }
    }

    /**
     * 로그아웃 후 Referrer로 이동
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println("Referer: " + request.getHeader("Referer"));
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * 구글 OAuth 로그인 처리
     */
    @RequestMapping(value = "/login/google/oauth", method = RequestMethod.GET)
    public String googleOAuthAction(HttpServletRequest request, @RequestParam(value = "code") String code, HttpSession session) throws JsonProcessingException {
        String email = userService.getGmail(request.getHeader("host"), code, request.isSecure());
        try {
            UserVO user = userService.getUserByEmail(email);
            session.setAttribute("user", user);
            String redirectUri = (String) session.getAttribute("redirect_uri");
            if (redirectUri != null) {
                return "redirect:" + redirectUri;
            } else {
                return "redirect:/";
            }
        } catch (BizNotFoundException e) {
            return "redirect:/?modal=/signup?email=" + email;
        }
    }
}
