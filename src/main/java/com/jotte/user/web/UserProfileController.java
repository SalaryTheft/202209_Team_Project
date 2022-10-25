package com.jotte.user.web;

import com.jotte.comment.service.ICommentService;
import com.jotte.comment.vo.CommentVO;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.vo.SearchVO;
import com.jotte.post.service.IPostService;
import com.jotte.post.vo.PostVO;
import com.jotte.user.service.IUserService;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserProfileController {
    private final IUserService userService;
    private final IPostService postService;
    private final ICommentService commentService;

    @RequestMapping(value = "/u/{userNickname}", method = RequestMethod.GET)
    public String userProfile(@PathVariable String userNickname, String tab, Model model, @ModelAttribute("search") SearchVO search) {
        try {
            UserVO profileUser = userService.getUserByNickname(userNickname);
            model.addAttribute("profileUser", profileUser);
            search.setKeyword(userNickname);
            search.setType("writer");
            if ("posts".equals(tab) || tab == null || "".equals(tab)) {
                List<PostVO> postList = postService.getPostList(search);
                model.addAttribute("postList", postList);
            } else if ("comments".equals(tab)) {
                List<CommentVO> commentList = commentService.getCommentList(search);
                model.addAttribute("commentList", commentList);
            }
            return "user/profile";
        } catch (BizNotFoundException e) {
            model.addAttribute("alertMsg", "존재하지 않는 사용자입니다.");
            model.addAttribute("url", null);
            return "alert";
        }
    }


    @RequestMapping(value = "/u/{userNickname}/icon", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] userIcon(@PathVariable String userNickname) {
        try {
            String userIcon = userService.getUserByNickname(userNickname).getUserIcon();
            if (userIcon != null) {
                userIcon = userIcon.substring(userIcon.indexOf(",") + 1);
            } else {
                userIcon = "/9j/4AAQSkZJRgABAQEBLAEsAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/wAALCACWAJYBAREA/8QAHAABAAIDAQEBAAAAAAAAAAAAAAUGAQMEBwII/8QAMxAAAgIBAgIGCAYDAAAAAAAAAAECAwQFEQYhEhMxQVFhIjJCcYGhsdEUI1JikcEzcuH/2gAIAQEAAD8A/QQAAAAAAAAAAAAAAAAAAAAABIYGj5uYlKNfQrftz5Il6OGKkvz8mTfhCO31Nz4awtv8t6fvX2OTJ4Ykk3j5Kl5Tjt80Q2bg5WHLbIqlFd0u1P4nMAAAAABGLk1GKbbeySLXoehwojG/Lip2vmoPsj92ThkA+La4W1uuyKnF9qa5FV17RXiqWRjJuj2o98P+EIAAAAAWLhLTlLfOtjulyrT+bLMAADEoqUXGSTTWzTKRr2B+BzXGC/Kn6UPLyI8AAAA+qoOyyNce2TSR6DiUxx8aumC5QikbQAACJ4pxlfpcrEvSpfST8u8poAAAB2aFBT1fFi+zrE/45l8AAABpzoKeFfB9jrkvkzz0AAAA7NEmq9WxpPs6xL+eRfAAAAaNQmq8C+x+zXL6HnwAAABmEnCalF7NPdHoGBkRysOq+PtxTfk+83gAAENxZkqnTupT9K57fBdpUAAAAATvCmoqm14d0tq5veDfc/D4lrAAB8W2QqrlZZJRjFbtso+s50s7NlbzUFygvBHEAAAAAWPQte2UcbNb8I2fcsi5rdGQDVk3149MrrZdGEe1lQ1vV7M+XV1pwoT5Lvl5siwAAAADs0zTsnPs6NUdoL1pvsRa9L0jFwYqSj1lvfOS+ngSIAMNJrZpNELqugUXqVmLtTZ+n2X9ir5OPdjWuq+twku5moAAAAl9C0aea1ffvChPl4z93kW6iqumpV1QUIR7EkfYAABy6hg0Z1Lruj/rJdsSmapp92Bf0LF0oP1JrskcgAABK8PaW867rbU+og+f7n4FyhGMYqMUkktkl3GQAAADRm41WXjyoujvGX8p+KKPqWHbhZUqLOe3OMv1LxOYAA24dE8rJror9ab293mX7Ex68XHhRUtowWxtAAAAAI3iDAWZgScV+bWulB+PiikgAFh4MxelZdlyXq+hH395ZwAAAAAY7ij6/jfhdUtgltGT6cfczgABduGaep0enxnvN/FkkAAAAAAVvjSlbY96XPnB/Vf2VsALmz0PEgq8WqtezBL5G0AAAAAAiOLK+npEpd8Jxf8ARTgD6oW90F4yX1PRV2GQAAAAACP4iW+i5K/avqijg//Z";
            }
            return Base64.getDecoder().decode(userIcon);
        } catch (BizNotFoundException e) {
            return null;
        }
    }

    @RequestMapping(value = "/u/{userNickname}/background", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] userBackground(@PathVariable String userNickname) {
        try {
            String userBackground = userService.getUserByNickname(userNickname).getUserBackground();
            if (userBackground != null) {
                userBackground = userBackground.substring(userBackground.indexOf(",") + 1);
            } else {
                userBackground = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
            }
            return Base64.getDecoder().decode(userBackground);
        } catch (BizNotFoundException e) {
            return null;
        }
    }
}
