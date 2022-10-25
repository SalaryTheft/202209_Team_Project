package com.jotte.board.dao;

import com.jotte.board.vo.BoardVO;
import com.jotte.common.vo.SearchVO;
import com.jotte.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IBoardDAO {
    BoardVO getBoardById(String boardId);

    BoardVO getBoardByName(String boardName);

    List<BoardVO> getBoardList(@Param("search") SearchVO search);

    int getBoardCount(@Param("search") SearchVO search);

    int insertBoard(BoardVO board);

    int updateBoard(BoardVO board);

    int deleteBoard(BoardVO board);

    List<String> getBoardTabs(BoardVO board);

    int insertBoardTab(@Param("board") BoardVO board,
                       @Param("tabName") String tabName);

    int deleteBoardTab(@Param("board") BoardVO board,
                       @Param("tabName") String tabName);

    int updateBoardTab(@Param("board") BoardVO board,
                       @Param("oldTabName") String tabName,
                       @Param("newTabName") String newTabName);

    int movePostTab(@Param("board") BoardVO board,
                    @Param("oldTabName") String tabName,
                    @Param("newTabName") String newTabName);

    int updateBoardTabOrder(@Param("board") BoardVO board,
                            @Param("tabName") String tabName,
                            @Param("tabOrder") int tabOrder);

    int insertBoardSubscription(@Param("board") BoardVO board,
                                @Param("user") UserVO user);

    int deleteBoardSubscription(@Param("board") BoardVO board,
                                @Param("user") UserVO user);

    boolean isBoardSubscribed(@Param("board") BoardVO board,
                              @Param("user") UserVO user);

    List<String> getSubBoardList(@Param("user") UserVO user);

    List<BoardVO> getBestBoardList(int limit);
}
