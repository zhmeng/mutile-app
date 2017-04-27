package com.teamclub.util.page;

/**
 * Created by ilkkzm on 17-4-27.
 */
public class PageForm {
    private int size = 10;
    private int page;

    public int getSize() {
        return this.size;
    }

    public PageForm() { }

    public PageForm(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
