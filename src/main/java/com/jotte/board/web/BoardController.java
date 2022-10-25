package com.jotte.board.web;

import com.jotte.board.service.IBoardService;
import com.jotte.board.service.IModService;
import com.jotte.board.validation.BoardValidation;
import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final IBoardService boardService;
    private final IModService modService;

    @RequestMapping(value = "/r/list", method = RequestMethod.GET)
    public String boardList(Model model, HttpSession session) {
        List<BoardVO> boardList = boardService.getBoardList(null);
        ArrayList<String> indexList = new ArrayList<>();
        for (BoardVO board : boardList) {
            if (!indexList.contains(board.getBoardNameIndex())) {
                indexList.add(board.getBoardNameIndex());
            }
        }
        if (session.getAttribute("user") != null) {
            UserVO user = (UserVO) session.getAttribute("user");
            List<String> subBoardList = boardService.getSubBoardList(user);
            for (BoardVO board : boardList) {
                if (subBoardList.contains(board.getBoardId())) {
                    board.setBoardSubscribed(true);
                }
            }
        }
        model.addAttribute("boardList", boardList);
        model.addAttribute("indexList", indexList);
        return "board/board_list";
    }

    @RequestMapping(value = "/r/new", method = RequestMethod.GET)
    public String newBoardPage() {
        return "board/board_new";
    }

    @Auth
    @RequestMapping(value = "/r/new", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String newBoard(@Validated(BoardValidation.Insert.class) @RequestBody BoardVO board, BindingResult error, HttpServletRequest request) {
        if (error.hasErrors()) {
            return new JsonMsgVO("fail", "입력값을 확인해주세요.").toString();
        } else {
            // board Id cannot be "new", "list"
            if (board.getBoardId().equals("new") || board.getBoardId().equals("list")) {
                return new JsonMsgVO("fail", "사용할 수 없는 게시판 아이디입니다.").toString();
            }
            try {
                HttpSession session = request.getSession();
                UserVO user = (UserVO) session.getAttribute("user");
                boardService.insertBoard(board, new ModVO(board.getBoardId(), user.getUserUuid(), "MOD"));
                boardService.insertBoardTab(board, "일반");
                return new JsonMsgVO("success", "게시판이 생성되었습니다.").toString();
            } catch (BizDuplicateKeyException e) {
                return new JsonMsgVO("fail", "이미 존재하는 게시판 아이디 또는 게시판 이름입니다.").toString();
            } catch (BizNotEffectedException e) {
                return new JsonMsgVO("fail", "게시판 생성에 실패했습니다.").toString();
            }
        }
    }

    @RequestMapping(value = "/r/{boardId}", method = RequestMethod.GET)
    public String boardPage(@PathVariable String boardId, String tab, Model model, HttpServletRequest request) {
        try {
            // TODO : BoardVO에 boardTabs, boardMods 추가
            BoardVO board = boardService.getBoardById(boardId);
            model.addAttribute("board", board);
            List<ModVO> boardMods = modService.getModList(board);
            model.addAttribute("boardMods", boardMods);
            List<String> boardTabs = boardService.getBoardTabs(board);
            model.addAttribute("boardTabs", boardTabs);

            UserVO user = (UserVO) request.getSession().getAttribute("user");
            boolean isSubscribed = boardService.isBoardSubscribed(board, user);
            model.addAttribute("isSubscribed", isSubscribed);
            return "board/board";
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @RequestMapping(value = "/r/{boardId}/icon", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] boardIcon(@PathVariable String boardId) {
        try {
            String boIcon = boardService.getBoardById(boardId).getBoardIcon();
            if (boIcon != null) {
                boIcon = boIcon.substring(boIcon.indexOf(",") + 1);
            } else {
                boIcon = "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";
            }
            return Base64.getDecoder().decode(boIcon);
        } catch (BizNotFoundException e) {
            return null;
        }
    }

    @RequestMapping(value = "/r/{boardId}/background", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] boardBackground(@PathVariable String boardId) {
        try {
            String boBackground = boardService.getBoardById(boardId).getBoardBackground();
            if (boBackground != null) {
                boBackground = boBackground.substring(boBackground.indexOf(",") + 1);
            } else {
                boBackground = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
            }
            return Base64.getDecoder().decode(boBackground);
        } catch (BizNotFoundException e) {
            return null;
        }
    }


}
