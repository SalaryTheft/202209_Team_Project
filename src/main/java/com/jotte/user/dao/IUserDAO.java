package com.jotte.user.dao;

import com.jotte.common.vo.SearchVO;
import com.jotte.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserDAO {
    UserVO getUserByUuid(String userUuid);

    UserVO getUserByEmail(String userEmail);

    UserVO getUserByNickname(String userNickname);

    List<UserVO> getUserList(SearchVO search);

    int getUserCount(SearchVO search);

    int insertUser(UserVO user);

    int updateUser(UserVO user);

    int deleteUser(UserVO user);
}
