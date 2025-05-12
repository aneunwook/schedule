package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class Paging {
    private int page; // 현재 페이지
    private int size; // 페이지당 출력할 개수

    public Paging(int page, int size){
        this.page = page;
        this.size = size;
    }

    public int offSet(){
        return page * size;
    }

}
