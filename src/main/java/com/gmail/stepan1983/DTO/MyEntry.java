package com.gmail.stepan1983.DTO;

import com.gmail.stepan1983.model.BookItem;

public class MyEntry {


    private BookItemDTO key;
    private Integer value;

    public MyEntry(BookItemDTO key, Integer value) {
        this.key = key;
        this.value = value;
    }



    public MyEntry() {
    }

    public BookItemDTO getKey() {
        return key;
    }

    public void setKey(BookItemDTO key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyEntry{" +
                "key=" + key +
                ", value=" + value +
                '}'+"\r\n";
    }
}
