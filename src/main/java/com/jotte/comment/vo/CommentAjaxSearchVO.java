package com.jotte.comment.vo;

import lombok.Data;

@Data
public class CommentAjaxSearchVO {
    private String order = "asc";
    private String timestamp;

    public String getOrder() {
        if (!"asc".equals(order) && !"desc".equals(order)) { // SQL Injection 방지
            order = "asc";
        }
        return order;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
