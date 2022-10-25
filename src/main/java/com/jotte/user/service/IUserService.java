package com.jotte.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.exception.BizPasswordNotMatchedException;
import com.jotte.common.vo.SearchVO;
import com.jotte.user.vo.UserVO;

import java.util.List;

public interface IUserService {
    UserVO login(UserVO user) throws BizNotFoundException, BizPasswordNotMatchedException;

    UserVO register(UserVO user) throws BizNotEffectedException, BizDuplicateKeyException;

    UserVO getUserByUuid(String userUuid) throws BizNotFoundException;

    UserVO getUserByEmail(String userEmail) throws BizNotFoundException;

    UserVO getUserByNickname(String userNickname) throws BizNotFoundException;

    List<UserVO> getUserList(SearchVO search);

    int getUserCount(SearchVO search);

    int insertUser(UserVO user) throws BizNotEffectedException;

    int updateUser(UserVO user) throws BizNotEffectedException;

    int deleteUser(UserVO user) throws BizNotEffectedException;

    String getGmail(String host, String code, boolean isSecure) throws JsonProcessingException;
}
