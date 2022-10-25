package com.jotte.emoji.web;

import com.jotte.common.interceptor.Auth;
import com.jotte.emoji.service.IEmojiService;
import com.jotte.emoji.vo.EmojiVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmojiViewController {

    private final IEmojiService emojiService;


}
