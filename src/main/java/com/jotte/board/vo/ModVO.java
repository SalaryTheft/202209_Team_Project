package com.jotte.board.vo;

import com.jotte.common.gson.JsonExclude;
import com.jotte.user.vo.UserVO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class ModVO {
    @NotEmpty
    private String boardId;
    @NotEmpty
    @JsonExclude
    private String userUuid;
    @NotEmpty
    private String modRole;

    private UserVO user;

    public ModVO(String boardId, String userUuid, String modRole) {
        this.boardId = boardId;
        this.userUuid = userUuid;
        this.modRole = modRole;
    }
}
