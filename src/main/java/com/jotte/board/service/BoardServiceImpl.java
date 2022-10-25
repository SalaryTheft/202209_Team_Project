package com.jotte.board.service;

import com.jotte.board.dao.IBoardDAO;
import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.vo.SearchVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements IBoardService {

    private final IBoardDAO boardDao;
    private final IModService modService;

    @Override
    public BoardVO getBoardById(String boardId) throws BizNotFoundException {
        BoardVO boardVO = boardDao.getBoardById(boardId);
        if (boardVO == null) {
            throw new BizNotFoundException();
        } else {
            return boardVO;
        }
    }

    @Override
    public List<BoardVO> getBoardList(SearchVO search) {
        if (search != null) {
            search.setTotalRows(getBoardCount(search));
            search.setPages();
        } else { // 전체 게시판 목록
            search = new SearchVO();
            search.setTotalRows(getBoardCount(null));
            search.setPages();
        }
        return boardDao.getBoardList(search);
    }

    @Override
    public int getBoardCount(SearchVO search) {
        return boardDao.getBoardCount(search);
    }

    @Override
    public int insertBoard(BoardVO board, ModVO mod) throws BizNotEffectedException, BizDuplicateKeyException {
        if (boardDao.getBoardById(board.getBoardId()) != null || boardDao.getBoardByName(board.getBoardName()) != null) {
            throw new BizDuplicateKeyException();
        } else {
            int cnt = boardDao.insertBoard(board);
            if (cnt == 0) {
                throw new BizNotEffectedException();
            }
            modService.insertMod(new ModVO(board.getBoardId(), mod.getUserUuid(), mod.getModRole()));
            return cnt;
        }
    }

    @Override
    public int updateBoard(BoardVO board) throws BizNotEffectedException {
        int cnt = boardDao.updateBoard(board);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int deleteBoard(BoardVO board) throws BizNotEffectedException {
        int cnt = boardDao.deleteBoard(board);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public List<String> getBoardTabs(BoardVO board) {
        return boardDao.getBoardTabs(board);
    }

    @Override
    public int insertBoardTab(BoardVO board, String tabName) throws BizNotEffectedException {
        int cnt = boardDao.insertBoardTab(board, tabName);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int deleteBoardTab(BoardVO board, String tabName) throws BizNotEffectedException {
        int cnt = boardDao.deleteBoardTab(board, tabName);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int updateBoardTab(BoardVO board, String oldTabName, String newTabName) throws BizNotEffectedException {
        int cnt = boardDao.updateBoardTab(board, oldTabName, newTabName);
        boardDao.movePostTab(board, oldTabName, newTabName);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int updateBoardTabOrder(BoardVO board, String tabName, int tabOrder) throws BizNotEffectedException {
        int cnt = boardDao.updateBoardTabOrder(board, tabName, tabOrder);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int insertBoardSubscription(BoardVO board, UserVO user) throws BizNotEffectedException {
        int cnt = boardDao.insertBoardSubscription(board, user);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int deleteBoardSubscription(BoardVO board, UserVO user) throws BizNotEffectedException {
        int cnt = boardDao.deleteBoardSubscription(board, user);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public boolean isBoardSubscribed(BoardVO board, UserVO user) {
        return boardDao.isBoardSubscribed(board, user);
    }

    @Override
    public List<String> getSubBoardList(UserVO user) {
        return boardDao.getSubBoardList(user);
    }

    @Override
    public List<BoardVO> getBestBoardList(int limit) {
        return boardDao.getBestBoardList(limit);
    }
}
