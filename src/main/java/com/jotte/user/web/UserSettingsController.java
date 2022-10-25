package com.jotte.user.web;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.interceptor.Auth;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserSettingsController {

    private final IUserService userService;

    private final MailService mailService;

    /**
     * 사용자 설정 페이지
     */
    @Auth
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String userSettingsPage(Model model, HttpServletRequest request) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "user/settings";
    }

    @Auth
    @RequestMapping(value = "/settings/email", method = RequestMethod.GET)
    public String userSettingsEmailStep1(Model model, HttpServletRequest request) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "user/settings_email_step1";
    }

    @Auth
    @RequestMapping(value = "/settings/email/2", method = RequestMethod.POST)
    public String userSettingsEmailStep2(String userNewEmail, Model model, HttpServletRequest request) {
        try {
            userService.getUserByEmail(userNewEmail);
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "user/settings_email_step1";
        } catch (BizNotFoundException e) {
            try {
                mailService.sendAuthCode(userNewEmail);
                request.getSession().setAttribute("userNewEmail", userNewEmail);
                request.getSession().setAttribute("mailAuthInfo", mailService.sendAuthCode(userNewEmail));
                return "user/settings_email_step2";
            } catch (MessagingException ex) {
                model.addAttribute("error", "메일 전송에 실패했습니다.");
                return "user/settings_email_step1";
            }
        }
    }

    @Auth
    @RequestMapping(value = "/settings/email/3", method = RequestMethod.POST)
    public String userSettingsEmailStep3(String authCode, Model model, HttpServletRequest request) {
        // 인증번호 시간 초과 및 일치 여부 확인 후 처리
        Map<String, Object> mailAuthInfo = (Map<String, Object>) request.getSession().getAttribute("mailAuthInfo");
        if (mailAuthInfo == null || System.currentTimeMillis() - (long) mailAuthInfo.get("authExpire") > 1000 * 60 * 5) {
            model.addAttribute("error", "인증번호가 만료되었습니다.");
            return "user/settings_email_step1";
        }
        if (!authCode.equals(mailAuthInfo.get("authCode"))) {
            model.addAttribute("error", "인증번호가 일치하지 않습니다.");
            return "user/settings_email_step2";
        }
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        user.setUserEmail((String) request.getSession().getAttribute("userNewEmail"));
        try {
            userService.updateUser(user);
            request.getSession().setAttribute("user", user);
            request.getSession().removeAttribute("mailAuthInfo");
            return "user/settings_email_step3";
        } catch (BizNotEffectedException e) {
            model.addAttribute("error", "이메일 변경에 실패했습니다.");
            return "user/settings_email_step1";
        }
    }


    @Auth
    @RequestMapping(value = "/settings/password", method = RequestMethod.GET)
    public String userSettingsPasswordPage(Model model, HttpServletRequest request) {
        UserVO user = (UserVO) request.getAttribute("userVO");
        model.addAttribute("user", user);
        return "user/settings_password_step1";
    }

    @Auth
    @RequestMapping(value = "/settings/password", method = RequestMethod.POST)
    public String userSettingsPassword(String userOldPw, @Validated(UserValidation.Password.class) UserVO newUser, BindingResult error, HttpServletRequest request, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("error", "비밀번호 형식이 올바르지 않습니다.");
            return "user/settings_password_step1";
        }
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        if (!userOldPw.equals(user.getUserPw())) {
            model.addAttribute("error", "기존 비밀번호가 일치하지 않습니다.");
            return "user/settings_password_step1";
        } else {
            try {
                user.setUserPw(newUser.getUserPw());
                userService.updateUser(user);
                session.setAttribute("user", user);
                return "user/settings_password_step2";
            } catch (BizNotEffectedException e) {
                model.addAttribute("error", "비밀번호 변경에 실패했습니다.");
                return "user/settings_password_step1";
            }
        }
    }

    @Auth
    @RequestMapping(value = "/settings/deactivate", method = RequestMethod.GET)
    public String userSettingsDeactivationPage(Model model, HttpServletRequest request) {
        UserVO user = (UserVO) request.getAttribute("userVO");
        model.addAttribute("user", user);
        return "user/settings_deactivate_step1";
    }

    @Auth
    @RequestMapping(value = "/settings/deactivate", method = RequestMethod.POST)
    public String userSettingsDeactivation(String userPw, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        if (!userPw.equals(user.getUserPw())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "user/settings_deactivate_step1";
        } else {
            try {
                userService.deleteUser(user);
                session.removeAttribute("user");
                return "user/settings_deactivate_step2";
            } catch (BizNotEffectedException e) {
                model.addAttribute("error", "회원 탈퇴에 실패했습니다.");
                return "user/settings_deactivate_step1";
            }
        }
    }

    @Auth
    @RequestMapping(value = "/settings/nickname", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userSettingsNickname(String userNickname, HttpServletRequest request) {
        try {
            userService.getUserByNickname(userNickname);
            return new JsonMsgVO("fail", "이미 사용중인 닉네임입니다.").toString();
        } catch (BizNotFoundException e) {
            HttpSession session = request.getSession();
            UserVO user = (UserVO) session.getAttribute("user");
            user.setUserNickname(userNickname);
            try {
                userService.updateUser(user);
                session.setAttribute("user", user);
                return new JsonMsgVO("success", "닉네임이 변경되었습니다.").toString();
            } catch (BizNotEffectedException ex) {
                return new JsonMsgVO("fail", "닉네임 변경에 실패했습니다.").toString();
            }
        }
    }

    @Auth
    @RequestMapping(value = "/settings/desc", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userSettingsDesc(String userDesc, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        user.setUserDesc(userDesc);
        try {
            userService.updateUser(user);
            return new JsonMsgVO("success", "자기소개가 변경되었습니다.").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "자기소개 변경에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/settings/icon", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userSettingsIcon(String userIcon, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        user.setUserIcon(userIcon);
        try {
            userService.updateUser(user);
            session.setAttribute("user", user);
            return new JsonMsgVO("success", "아이콘이 변경되었습니다.").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "아이콘 변경에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/settings/background", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userSettingsBackground(String userBackground, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        user.setUserBackground(userBackground);
        try {
            userService.updateUser(user);
            session.setAttribute("user", user);
            return new JsonMsgVO("success", "배경이 변경되었습니다.").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "배경 변경에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/settings/color", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userSettingsColor(String userColor, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        user.setUserColor(userColor);
        try {
            userService.updateUser(user);
            return new JsonMsgVO("success", "색상이 변경되었습니다.").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "색상 변경에 실패했습니다.").toString();
        }
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.GET)
    public String resetPasswordStep1() {
        return "user/reset_pw_step1";
    }

    @RequestMapping(value = "/reset_password/2", method = RequestMethod.POST)
    public String resetPasswordStep2(String userEmail, Model model, HttpSession session) {
        try {
            userService.getUserByEmail(userEmail);
            try {
                session.setAttribute("mailAuthInfo", mailService.sendAuthCode(userEmail));
                session.setAttribute("userEmail", userEmail);
                return "user/reset_pw_step2";
            } catch (MessagingException e) {
                model.addAttribute("error", "메일 전송에 실패했습니다.");
                return "user/reset_pw_step1";
            }
        } catch (BizNotFoundException e) {
            model.addAttribute("error", "존재하지 않는 회원입니다.");
            return "user/reset_pw_step1";
        }
    }

    @RequestMapping(value = "/reset_password/3", method = RequestMethod.POST)
    public String resetPasswordStep3(String authCode, String userPw, Model model, HttpSession session) {
        // 인증번호 시간 초과 및 일치 여부 확인 후 처리
        Map<String, Object> mailAuthInfo = (Map<String, Object>) session.getAttribute("mailAuthInfo");
        if (mailAuthInfo == null || System.currentTimeMillis() - (long) mailAuthInfo.get("authExpire") > 1000 * 60 * 5) {
            model.addAttribute("error", "인증번호가 만료되었습니다.");
            return "user/reset_pw_step1";
        }
        if (!authCode.equals(mailAuthInfo.get("authCode"))) {
            model.addAttribute("error", "인증번호가 일치하지 않습니다.");
            return "user/reset_pw_step2";
        }
        try {
            UserVO user = userService.getUserByEmail((String) session.getAttribute("userEmail"));
            user.setUserPw(userPw);
            try {
                userService.updateUser(user);
                session.removeAttribute("mailAuthInfo");
                session.removeAttribute("userEmail");
                return "user/reset_pw_step3";
            } catch (BizNotEffectedException e) {
                model.addAttribute("error", "비밀번호 변경에 실패했습니다.");
                return "user/reset_pw_step2";
            }
        } catch (BizNotFoundException e) {
            model.addAttribute("error", "존재하지 않는 회원입니다.");
            return "user/reset_pw_step1";
        }


    }

}
