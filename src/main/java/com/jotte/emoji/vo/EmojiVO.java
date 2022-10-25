package com.jotte.emoji.vo;


import lombok.Data;

@Data
public class EmojiVO {
    private String emojiUuid;
    private String emojiName;
    private String emojiDesc;
    private String userUuid;
    private String emojiData;

    public EmojiVO() {

    }
}
