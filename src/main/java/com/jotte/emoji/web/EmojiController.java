package com.jotte.emoji.web;

import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.emoji.service.IEmojiService;
import com.jotte.emoji.vo.EmojiVO;
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
public class EmojiController {

    private final IEmojiService emojiService;

    @Auth
    @RequestMapping(value = "/emoji/list", method = RequestMethod.GET)
    public String emojiList(Model model) {
        List<EmojiVO> emojiList = emojiService.getEmojiList();
        model.addAttribute("emojiList", emojiList);

        return "emoji/emoji";
    }

    @Auth
    @RequestMapping(value = "/emoji/edit", method = RequestMethod.GET)
    public String emojiEdit(Model model) {

        return "emoji/emoji_edit";
    }

    @Auth
    @RequestMapping(value = "/emoji/edit", method = RequestMethod.POST)
    @ResponseBody
    public String emojiEditAction(Model model, EmojiVO emoji, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        emoji.setUserUuid(user.getUserUuid());
        emojiService.insertEmoji(emoji);
        System.out.println(emoji);

        return "<script>parent.location.reload();</script>";
    }

    @Auth
    @RequestMapping(value = "/emoji/list", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEmoji(Model model, EmojiVO emoji, HttpServletRequest request) {
        emojiService.deleteEmoji(emoji);

        return "redirect:/emoji/emoji";
    }
}
