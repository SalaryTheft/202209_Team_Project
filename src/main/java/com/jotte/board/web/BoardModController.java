package com.jotte.board.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jotte.board.service.IBoardService;
import com.jotte.board.service.IModService;
import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.interceptor.Auth;
import com.jotte.common.vo.JsonMsgVO;
import com.jotte.user.service.IUserService;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardModController {

    private final IBoardService boardService;
    private final IModService modService;
    private final IUserService userService;

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/general", method = RequestMethod.GET)
    public String boardEditPage(@PathVariable String boardId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            if (modService.isMod(board, user, "MOD")) {
                model.addAttribute("board", board);
                List<String> boardTabs = boardService.getBoardTabs(board);
                model.addAttribute("boardTabs", boardTabs);
                return "board/board_edit_general";
            }
            return "redirect:/error/403";
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/general", method = RequestMethod.POST)
    @ResponseBody
    public String boardEditAction(@PathVariable String boardId, BoardVO updateBoard, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            if (modService.isMod(board, user, "MOD")) {
                board.setBoardDesc(updateBoard.getBoardDesc());
                board.setBoardIcon(updateBoard.getBoardIcon());
                board.setBoardBackground(updateBoard.getBoardBackground());
                board.setBoardColor(updateBoard.getBoardColor());
                boardService.updateBoard(board);
                return "<script>parent.location.reload();</script>"; // TODO: 이거 바꿔야됨
            }
            return new JsonMsgVO("fail", "권한이 없습니다.").toString();
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("fail", "게시판이 존재하지 않습니다.").toString();
        } catch (BizNotEffectedException e) {
            return new JsonMsgVO("fail", "게시판 수정에 실패했습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/tabs", method = RequestMethod.GET)
    public String boardEditTabsPage(@PathVariable String boardId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            if (modService.isMod(board, user, "MOD")) {
                model.addAttribute("board", board);
                List<String> tabs = boardService.getBoardTabs(board);
                model.addAttribute("tabs", tabs);
                return "board/board_edit_tabs";
            }
            return "redirect:/error/403";
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/tabs", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String boardEditTabsAction(@PathVariable String boardId, @RequestBody Map<String, Object> data, HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        String action = (String) data.get("action");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            if (modService.isMod(board, user, "MOD")) {
                switch (action) {
                    case "insert":
                        try {
                            boardService.insertBoardTab(board, (String) data.get("tabName"));
                            return new JsonMsgVO("success", "탭이 추가되었습니다.").toString();
                        } catch (BizNotEffectedException e) {
                            return new JsonMsgVO("fail", "탭 추가에 실패했습니다.").toString();
                        }
                    case "delete":
                        try {
                            boardService.deleteBoardTab(board, (String) data.get("tabName"));
                            return new JsonMsgVO("success", "탭이 삭제되었습니다.").toString();
                        } catch (BizNotEffectedException e) {
                            return new JsonMsgVO("fail", "탭 삭제에 실패했습니다.").toString();
                        }
                    case "update":
                        try {
                            boardService.updateBoardTab(board, (String) data.get("oldTabName"), (String) data.get("newTabName"));
                            return new JsonMsgVO("success", "탭이 수정되었습니다.").toString();
                        } catch (BizNotEffectedException e) {
                            return new JsonMsgVO("fail", "탭 수정에 실패했습니다.").toString();
                        }
                    case "reorder":
                        try {
                            for (Map<Object, Object> tab : (List<Map<Object, Object>>) data.get("boardTabs")) {
                                boardService.updateBoardTabOrder(board, (String) tab.get("tabName"), (Integer) tab.get("tabOrder"));
                            }
                            return new JsonMsgVO("success", "탭 순서가 수정되었습니다.").toString();
                        } catch (BizNotEffectedException e) {
                            return new JsonMsgVO("fail", "탭 순서 수정에 실패했습니다.").toString();
                        }
                    default:
                        return new JsonMsgVO("fail", "알 수 없는 요청입니다.").toString();
                }
            }
            return new JsonMsgVO("fail", "권한이 없습니다.").toString();
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("fail", "게시판이 존재하지 않습니다.").toString();
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/mods", method = RequestMethod.GET)
    public String boardEditModsPage(@PathVariable String boardId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            List<ModVO> modList = modService.getModList(board);
            if (modService.isMod(board, user, "MOD")) {
                model.addAttribute("board", board);
                model.addAttribute("boardMods", modList);
                return "board/board_edit_mods";
            }
            return "redirect:/error/403";
        } catch (BizNotFoundException e) {
            return "redirect:/error/404";
        }
    }

    @Auth
    @RequestMapping(value = "/r/{boardId}/edit/mods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String boardEditModsAction(@PathVariable String boardId, @RequestBody Map<String, String> data, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        String action = data.get("action");
        try {
            BoardVO board = boardService.getBoardById(boardId);
            if (modService.isMod(board, user, "MOD")) {
                switch (action) {
                    case "insert": // 부관리자 추가
                        try {
                            modService.insertMod(new ModVO(board.getBoardId(), userService.getUserByNickname(data.get("userNickname")).getUserUuid(), "SUBMOD"));
                            return new JsonMsgVO("success", "권한이 추가되었습니다.").toString();
                        } catch (BizNotEffectedException | BizDuplicateKeyException e) {
                            return new JsonMsgVO("fail", "이미 추가된 사용자입니다.").toString();
                        }
                    case "delete": // 부관리자 삭제
                        try {
                            modService.deleteMod(new ModVO(board.getBoardId(), userService.getUserByNickname(data.get("userNickname")).getUserUuid(), "SUBMOD"));
                            return new JsonMsgVO("success", "권한이 삭제되었습니다.").toString();
                        } catch (BizNotEffectedException e) {
                            return new JsonMsgVO("fail", "권한 삭제에 실패했습니다.").toString();
                        }
                    case "update": // 주관리자 위임
                        if (modService.isMod(board, userService.getUserByNickname(data.get("userNickname")), "SUBMOD")) {
                            try {
                                modService.deleteMod(new ModVO(board.getBoardId(), user.getUserUuid(), "MOD")); // 기존 주관리자 권한 삭제
                                modService.updateMod(new ModVO(board.getBoardId(), userService.getUserByNickname(data.get("userNickname")).getUserUuid(), "MOD"));
                                return new JsonMsgVO("success", "권한이 수정되었습니다.").toString();
                            } catch (BizNotEffectedException e) {
                                return new JsonMsgVO("fail", "권한 수정에 실패했습니다.").toString();
                            }
                        } else {
                            return new JsonMsgVO("fail", "주관리자는 부관리자에게만 위임할 수 있습니다.").toString();
                        }
                    default:
                        return new JsonMsgVO("fail", "알 수 없는 요청입니다.").toString();
                }
            }
            return new JsonMsgVO("fail", "권한이 없습니다.").toString();
        } catch (BizNotFoundException e) {
            return new JsonMsgVO("fail", "게시판 또는 사용자가 존재하지 않습니다.").toString();
        }
    }
}
