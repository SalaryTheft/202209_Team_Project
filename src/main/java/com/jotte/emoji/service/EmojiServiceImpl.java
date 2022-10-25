package com.jotte.emoji.service;

import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.emoji.dao.IEmojiDAO;
import com.jotte.emoji.vo.EmojiVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmojiServiceImpl implements IEmojiService {

    private final IEmojiDAO emojiDao;

    @Override
    public List<EmojiVO> getEmojiList() {
        return emojiDao.getEmojiList();
    }

    @Override
    public EmojiVO getEmojiByUuid(String emojiUuid) {
        return emojiDao.getEmojiByUuid(emojiUuid);
    }

    @Override
    public int insertEmoji(EmojiVO emoji) {
        emoji.setEmojiUuid(UUID.randomUUID().toString());
        emojiDao.insertEmoji(emoji);

        return 0;
    }

    @Override
    public int deleteEmoji(EmojiVO emoji) {
        emojiDao.deleteEmoji(emoji);

        return 0;
    }

}
