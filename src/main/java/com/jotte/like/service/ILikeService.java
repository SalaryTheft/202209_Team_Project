package com.jotte.like.service;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.like.vo.LikeVO;

public interface ILikeService {
    int insertLike(LikeVO like) throws BizNotEffectedException;
    int deleteLike(LikeVO like) throws BizNotEffectedException;
}
