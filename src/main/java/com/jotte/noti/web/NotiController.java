package com.jotte.noti.web;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.noti.service.INotiService;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class NotiController {

    private final INotiService notiService;

    @RequestMapping(value = "/noti", method = RequestMethod.GET)
    public String notiPage(HttpServletRequest request, Model model) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("notiList", notiService.getNotiList(user));
        }
        return "noti/noti";
    }

    @Auth
    @RequestMapping(value = "/noti/markasread",
            method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String markAsRead(HttpServletRequest request, String uuid) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        try {
            notiService.markAsRead(user, uuid);
            return new JsonMsgVO("success", "읽음 처리 성공").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("error", "읽음 처리 실패").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/noti/delete",
            method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteNoti(HttpServletRequest request) {
        UserVO user = (UserVO) request.getSession().getAttribute("user");
        try {
            notiService.deleteNoti(user);
            return new JsonMsgVO("success", "알림 삭제 성공").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("error", "알림 삭제 실패").toString();
        }
    }

}
