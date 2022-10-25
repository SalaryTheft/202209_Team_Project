package com.jotte.board.service;

import com.jotte.board.dao.IModDAO;
import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.user.dao.IUserDAO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModServiceImpl implements IModService {

    private final IModDAO modDao;
    private final IUserDAO userDao;


    @Override
    public int insertMod(ModVO mod) throws BizDuplicateKeyException, BizNotEffectedException {
        if (modDao.getExistingMod(mod) != null) {
            throw new BizDuplicateKeyException();
        } else {
            int result = modDao.insertBoardMod(mod);
            if (result == 0) {
                throw new BizNotEffectedException();
            }
            return result;
        }
    }

    @Override
    public int deleteMod(ModVO mod) throws BizNotEffectedException {
        int result = modDao.deleteBoardMod(mod);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public int updateMod(ModVO mod) throws BizNotEffectedException {
        int result = modDao.updateBoardMod(mod);
        if (result == 0) {
            throw new BizNotEffectedException();
        }
        return result;
    }

    @Override
    public List<ModVO> getModList(BoardVO board) {
        return modDao.getModList(board);
    }

    @Override
    public boolean isMod(BoardVO board, UserVO user, String modRole) {
        List<ModVO> modList = modDao.getModList(board);
        List<UserVO> userList = new ArrayList<>();
        for (ModVO mod : modList) {
            if (modRole.equals(mod.getModRole()) && mod.getUserUuid().equals(user.getUserUuid())) {
                return true;
            }
        }
        return false;
    }
}
