package com.jotte.board.service;

import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.vo.SearchVO;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface IBoardService {
    BoardVO getBoardById(String boardId) throws BizNotFoundException;
    List<BoardVO> getBoardList(SearchVO search);
    int getBoardCount(SearchVO search);
    int insertBoard(BoardVO board, ModVO mod) throws BizNotEffectedException, BizDuplicateKeyException;
    int updateBoard(BoardVO board) throws BizNotEffectedException;
    int deleteBoard(BoardVO board) throws BizNotEffectedException;
    List<String> getBoardTabs(BoardVO board);
    int insertBoardTab(BoardVO board, String tabName) throws BizNotEffectedException;
    int deleteBoardTab(BoardVO board, String tabName) throws BizNotEffectedException;
    int updateBoardTab(BoardVO board, String oldTabName, String newTabName) throws BizNotEffectedException;
    int updateBoardTabOrder(BoardVO board, String tabName, int tabOrder) throws BizNotEffectedException;

    int insertBoardSubscription(BoardVO board, UserVO user) throws BizNotEffectedException;

    int deleteBoardSubscription(BoardVO board, UserVO user) throws BizNotEffectedException;

    boolean isBoardSubscribed(BoardVO board, UserVO user);

    List<String> getSubBoardList(UserVO user);

    List<BoardVO> getBestBoardList(int limit);
}
