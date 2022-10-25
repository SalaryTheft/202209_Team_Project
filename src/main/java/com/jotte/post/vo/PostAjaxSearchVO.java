package com.jotte.post.vo;

import lombok.Data;

import java.util.List;

@Data
public class PostAjaxSearchVO {
    private String tab;
    private String timestamp;
    private String uuid;
    private String board;
    private String order = "desc";
    private List<String> subBoardList;

    public String getOrder() {
        if (!"asc".equals(order) && !"desc".equals(order)) { // SQL Injection 방지
            order = "asc";
        }
        return order;
    }

    public String getTab() {
        if ("".equals(tab)) {
            return null;
        }
        return tab;
    }

    public String getTimestamp() {
        if ("".equals(timestamp)) {
            return null;
        }
        return timestamp;
    }
}
