package com.gmail.stepan1983.DTO;

import com.gmail.stepan1983.model.BookItem;

import java.util.List;

public class BookItemsWithParamList {
    private List<BookItemDTO> bookItems;
    private Integer totalCountByParam;

    public BookItemsWithParamList(List<BookItemDTO> bookItems, Integer totalCountByParam) {
        this.bookItems = bookItems;
        this.totalCountByParam = totalCountByParam;
    }

    public BookItemsWithParamList() {
    }

    public List<BookItemDTO> getBookItems() {
        return bookItems;
    }

    public void setBookItems(List<BookItemDTO> bookItems) {
        this.bookItems = bookItems;
    }

    public Integer getTotalCountByParam() {
        return totalCountByParam;
    }

    public void setTotalCountByParam(Integer totalCountByParam) {
        this.totalCountByParam = totalCountByParam;
    }
}
