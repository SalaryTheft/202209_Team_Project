package com.jotte.board.dao;

import com.jotte.board.vo.BoardVO;
import com.jotte.board.vo.ModVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IModDAO {
    ModVO getExistingMod(ModVO mod);

    int insertBoardMod(ModVO mod);

    int deleteBoardMod(ModVO mod);

    int updateBoardMod(ModVO mod);

    List<ModVO> getModList(BoardVO board);
}
