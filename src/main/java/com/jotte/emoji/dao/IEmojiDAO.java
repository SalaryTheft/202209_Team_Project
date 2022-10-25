package com.jotte.emoji.dao;

import com.jotte.emoji.vo.EmojiVO;
import com.jotte.post.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IEmojiDAO {
    List<EmojiVO> getEmojiList();

    EmojiVO getEmojiByUuid(String emojiUuid);

    int insertEmoji(EmojiVO emoji);

    int deleteEmoji(EmojiVO emoji);
}
