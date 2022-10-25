package com.jotte.board.web;

import com.jotte.board.service.IBoardService;
import com.jotte.board.vo.BoardVO;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class BoardSubController {
    private final IBoardService boardService;

    @Auth
    @RequestMapping(value = "/r/{boardId}/subscribe", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String subscribe(@PathVariable String boardId, HttpServletRequest request) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            BoardVO board = boardService.getBoardById(boardId);
            boardService.insertBoardSubscription(board, user);
            return new JsonMsgVO("success", "구독 성공").toString();
        } catch (BizNotFoundException | BizNotEffectedException e) {
            return new JsonMsgVO("fail", "구독 실패").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/unsubscribe", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String unsubscribe(@PathVariable String boardId, HttpServletRequest request) {
        try {
            UserVO user = (UserVO) request.getSession().getAttribute("user");
            BoardVO board = boardService.getBoardById(boardId);
            boardService.deleteBoardSubscription(board, user);
            return new JsonMsgVO("success", "구독 취소 성공").toString();
        } catch (BizNotFoundException | BizNotEffectedException e) {
            return new JsonMsgVO("fail", "구독 취소 실패").toString();
        }
    }
}
