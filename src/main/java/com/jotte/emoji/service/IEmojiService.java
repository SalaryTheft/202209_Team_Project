package com.jotte.emoji.service;


import com.jotte.emoji.vo.EmojiVO;

import java.util.List;

public interface IEmojiService {
    List<EmojiVO> getEmojiList();

    EmojiVO getEmojiByUuid(String emojiUuid);

    int insertEmoji(EmojiVO emoji);

    int deleteEmoji(EmojiVO emoji);
}
