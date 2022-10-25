package com.jotte.board.service;

import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface IModService {
    int insertMod(ModVO mod) throws BizNotEffectedException, BizDuplicateKeyException;

    int deleteMod(ModVO mod) throws BizNotEffectedException;

    int updateMod(ModVO mod) throws BizNotEffectedException;

    List<ModVO> getModList(BoardVO board);

    boolean isMod(BoardVO board, UserVO user, String modRole);
}
