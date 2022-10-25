package com.jotte.common.vo;

import lombok.Data;

@Data
public class SearchVO {
    private String keyword; // 검색어
    private String type = "title"; // 종류(제목, 작성자, 내용)
    private String tab; // 탭(카테고리)
    private String board; // 게시판 이름

    private String range; // 날짜 범위(전체, 1일, 1주, 1개월, 3개월, 6개월, 1년)

    private int page = 1; // 현재 페이지
    private int rowsPerPage = 10; // 한 페이지당 게시글 수
    private int pageSize = 5; // 보여줄 페이지 수
    private int totalRows; // (검색 후) 총 게시글 수

    private int firstPage;
    private int lastPage;
    private int firstRow;
    private int lastRow;
    private int totalPages;

    public void setPages() {
        totalPages = (totalRows - 1) / rowsPerPage + 1;

        firstRow = (page - 1) * rowsPerPage + 1;
        lastRow = firstRow + rowsPerPage - 1;
        if (lastRow > totalRows)
            lastRow = totalRows;

        firstPage = (page - 1) / pageSize * pageSize + 1;
        lastPage = firstPage + pageSize - 1;
        if (lastPage > totalPages)
            lastPage = totalPages;
    }

    public String getRange() {
        // WHERE POST_TIMESTAMP < ${range}
        // 전체, 1일, 3일, 1주, 1개월, 3개월, 6개월, 1년
        if (this.range == null || this.range.equals("all")) {
            return null;
        } else if (this.range.equals("1d")) {
            return "SYSTIMESTAMP - INTERVAL '1' DAY";
        } else if (this.range.equals("3d")) {
            return "SYSTIMESTAMP - INTERVAL '3' DAY";
        } else if (this.range.equals("1w")) {
            return "SYSTIMESTAMP - INTERVAL '7' DAY";
        } else if (this.range.equals("1m")) {
            return "SYSTIMESTAMP - INTERVAL '1' MONTH";
        } else if (this.range.equals("3m")) {
            return "SYSTIMESTAMP - INTERVAL '3' MONTH";
        } else if (this.range.equals("6m")) {
            return "SYSTIMESTAMP - INTERVAL '6' MONTH";
        } else if (this.range.equals("1y")) {
            return "SYSTIMESTAMP - INTERVAL '1' YEAR";
        } else {
            return null;
        }
    }
}