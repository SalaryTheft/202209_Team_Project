package com.jotte.like.service;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.like.dao.ILikeDAO;
import com.jotte.like.vo.LikeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements ILikeService {

    private final ILikeDAO likeDAO;

    public int insertLike(LikeVO like) throws BizNotEffectedException {
        int cnt = likeDAO.insertLike(like);
        if (cnt < 1) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    public int deleteLike(LikeVO like) throws BizNotEffectedException {
        int cnt = likeDAO.deleteLike(like);
        if (cnt < 1) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }
}
