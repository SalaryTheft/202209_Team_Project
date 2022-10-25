package com.jotte.user.vo;

import com.jotte.common.gson.JsonExclude;
import com.jotte.user.validation.UserValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class UserVO {
    @NotEmpty(groups = {
            UserValidation.SignUp.class,
            UserValidation.Password.class})
    @Size(min = 8, max = 20, groups = {
            UserValidation.SignUp.class,
            UserValidation.Password.class})
    private String userPw;

    @JsonExclude
    private String userUuid;
    @NotEmpty(groups = {
            UserValidation.SignUp.class,
            UserValidation.Email.class})
    @Email(groups = {
            UserValidation.SignUp.class,
            UserValidation.Email.class})
    private String userEmail;

    @NotEmpty(groups = {
            UserValidation.SignUp.class,
            UserValidation.Nickname.class})
    @Size(min = 2, max = 15, groups = {
            UserValidation.SignUp.class,
            UserValidation.Nickname.class})
    private String userNickname;
    private String userRegDate;
    private String userDelYn;
    private String userRole;

    public UserVO() {

    }

    public UserVO(String userEmail, String userPw) {
        this.userEmail = userEmail;
        this.userPw = userPw;
    }

    private String userIcon;
    private String userBackground;
    private String userColor;
    private String userDesc;

    private int userPostCount;
    private int userCommentCount;

    public String getUserRegDate() {
        try {
            Date date = null;
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.userRegDate);
            return new SimpleDateFormat("yyyy년 M월 d일").format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public String getUserPostCount() {
        if (this.userPostCount < 100000) {
            return String.format("%,d", this.userPostCount);
        } else {
            return String.format("%.1fK", this.userPostCount / 1000.0);
        }
    }

    public String getUserCommentCount() {
        if (this.userCommentCount < 100000) {
            return String.format("%,d", this.userCommentCount);
        } else {
            return String.format("%.1fK", this.userCommentCount / 1000.0);
        }
    }

    public String getUserDesc(){
        if(this.userDesc == null || "".equals(this.userDesc)){
            return "안녕하세요 " + this.userNickname + "입니다";
        }else{
            return this.userDesc;
        }
    }
}
