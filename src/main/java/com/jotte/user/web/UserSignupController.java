package com.jotte.user.web;

import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.service.IUserService;
import com.jotte.user.service.MailService;
import com.jotte.user.validation.UserValidation;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@SessionAttributes("signupUser")
@RequiredArgsConstructor
public class UserSignupController {

    private final IUserService userService;
    private final MailService mailService;

    /**
     * 회원가입
     * 1/4 - 메일 입력
     *
     * @param email Gmail 간편 가입 시 이메일 자동입력 처리
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupStep1(String email, Model model) {
        model.addAttribute("email", email);
        return "user/signup_step1";
    }

    /**
     * 회원가입
     * 2/4 - 이메일 인증
     */
    @RequestMapping(value = "/signup/2")
    public String signupStep2(@ModelAttribute("signupUser") UserVO signupUser, Model model, HttpServletRequest request) {
        try {
            userService.getUserByEmail(signupUser.getUserEmail());
        } catch (BizNotFoundException e) {
            // TODO: 비동기로 이메일 중복 검사 구현 + Validation
            try {
                Map<String, Object> mailAuthInfo = mailService.sendAuthCode(signupUser.getUserEmail());
                request.getSession().setAttribute("mailAuthInfo", mailAuthInfo);
            } catch (MessagingException e2) {
                model.addAttribute("error", "메일 전송에 실패했습니다.");
                return "user/signup_step1";
            }
            return "user/signup_step2";
        }
        // 이메일 중복인 경우
        model.addAttribute("error", "이미 사용 중이거나 유효하지 않은 이메일입니다.");
        return "user/signup_step1";
    }

    /**
     * 회원가입
     * 3/4 - 회원정보 입력
     */
    @RequestMapping(value = "/signup/3", method = RequestMethod.POST)
    public String signupStep3(@ModelAttribute("signupUser") UserVO signupUser, Model model, HttpServletRequest request, String authCode) {
        Map<String, Object> mailAuthInfo = (Map<String, Object>) request.getSession().getAttribute("mailAuthInfo");
        if (mailAuthInfo == null) {
            return "redirect:/signup";
        } else if (System.currentTimeMillis() - (long) mailAuthInfo.get("authExpire") > 1000 * 60 * 5) {
            model.addAttribute("error", "인증 시간이 초과되었습니다. 다시 시도해주세요.");
            return "user/signup_step2";
        }
        // 인증 코드 확인
        if (authCode.equals(mailAuthInfo.get("authCode"))) {
            return "user/signup_step3";
        } else {
            model.addAttribute("error", "인증 코드가 일치하지 않습니다.");
            return "user/signup_step2";
        }
    }

    /**
     * 회원가입
     * 4/4 - 회원가입 완료
     */
    @RequestMapping(value = "/signup/4")
    public String signupStep4(@ModelAttribute("signupUser") @Validated(UserValidation.SignUp.class) UserVO signupUser,
                              BindingResult error, Model model, HttpSession session) {
        if (error.hasErrors()) {
            model.addAttribute("error", "잘못된 접근입니다.");
            return "user/signup_step3";
        }
        try {
            UserVO user = userService.register(signupUser);
            session.setAttribute("user", user);
            session.removeAttribute("signupUser");
        } catch (BizDuplicateKeyException | BizNotEffectedException e) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "user/signup_step3";
        }
        session.removeAttribute("mailAuthInfo");
        return "user/signup_step4";
    }

    /**
     * 이메일 중복 검사
     */
    @RequestMapping(value = "/signup/check_email", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkEmailAction(@RequestBody UserVO newUser) {
        try {
            userService.getUserByEmail(newUser.getUserEmail());
            return new JsonMsgVO("fail", "이미 사용 중인 이메일입니다.").toString();
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("success", "사용 가능한 이메일입니다.").toString();
        }
    }

    /**
     * 닉네임 중복 검사
     */
    @RequestMapping(value = "/signup/check_nickname", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkNicknameAction(@RequestBody UserVO newUser) {
        try {
            userService.getUserByNickname(newUser.getUserNickname());
            return new JsonMsgVO("fail", "이미 사용 중인 닉네임입니다.").toString();
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("success", "사용 가능한 닉네임입니다.").toString();
        }
    }

    @ModelAttribute("signupUser")
    public UserVO signupUser() {
        return new UserVO();
    }
}
