package com.gmail.stepan1983.model;

import java.util.List;

public class TableRestResponse <T>{

//    counter ajax
    private Integer draw;


    private Integer recordsTotal;

    private Integer recordsFiltered;

    List<T> data;

}
