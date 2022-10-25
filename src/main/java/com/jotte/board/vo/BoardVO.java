package com.jotte.board.vo;

import com.jotte.board.validation.BoardValidation;
import com.jotte.user.vo.UserVO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class BoardVO {
    @NotEmpty(groups = {BoardValidation.Insert.class})
    @Size(min = 3, max = 20,
            groups = {BoardValidation.Insert.class})
    private String boardId;
    @NotEmpty(groups = {BoardValidation.Insert.class})
    @Size(min = 1, max = 15,
            groups = {BoardValidation.Insert.class})
    private String boardName;
    @NotEmpty(groups = {BoardValidation.Insert.class})
    @Size(min = 4, max = 50,
            groups = {BoardValidation.Insert.class})
    private String boardDesc;
    private String boardRegDate;
    private String boardIcon;
    private String boardBackground;
    private String boardColor;

    private List<String> boardTabs;
    private List<UserVO> boardMods;

    private String boardNameIndex;

    private int boardSubCount;
    private int boardPostCount;

    private boolean isBoardSubscribed;

    public String getBoardRegDate() {
        try {
            Date date = null;
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.boardRegDate);
            return new SimpleDateFormat("yyyy년 M월 d일").format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public String getBoardSubCount() {
        if (this.boardSubCount < 100000) {
            return String.format("%,d", this.boardSubCount);
        } else {
            return String.format("%.1fK", this.boardSubCount / 1000.0);
        }
    }

    public String getBoardPostCount() {
        if (this.boardPostCount < 100000) {
            return String.format("%,d", this.boardPostCount);
        } else {
            return String.format("%.1fK", this.boardPostCount / 1000.0);
        }
    }
}
