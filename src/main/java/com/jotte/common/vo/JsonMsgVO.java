package com.jotte.common.vo;

import lombok.Data;

@Data
public class JsonMsgVO {
    String result;
    String message;

    public JsonMsgVO(String result, String message) {
        this.result = result;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("{ \"result\" : \"%s\", \"message\" : \"%s\" }", result, message);
    }
}
