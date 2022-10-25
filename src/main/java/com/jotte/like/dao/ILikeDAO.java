package com.jotte.like.dao;

import com.jotte.like.vo.LikeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILikeDAO {
    int insertLike(LikeVO like);
    int deleteLike(LikeVO like);
}
