package com.teamclub.util.page;

/**
 * Created by ilkkzm on 17-4-27.
 */

import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;
import com.fasterxml.jackson.databind.JsonNode;
import com.teamclub.util.libs.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<A> {
    public final List<A> innerData;
    public int totalRows;
    public int pageCount;
    public int curPage;
    public int pageSize;

    public Pager(List<A> innerData, int totalRows, int pageCount, int curPage, int pageSize) {
        this.innerData = innerData;
        this.totalRows = totalRows;
        this.pageCount = pageCount;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    private static <A> PagedList<A> genePaged(Query<A> query, PageForm form) {
        Integer currentPage = Integer.valueOf(form.getPage() * form.getSize());
        Integer pageSize = Integer.valueOf(form.getSize());
        PagedList<A> pagedList = query.setFirstRow(currentPage.intValue()).setMaxRows(pageSize.intValue()).findPagedList();
        return pagedList;
    }

    public static <A> Pager<A> genePager(Query<A> query, PageForm form) {
        PagedList<A> pagedList = genePaged(query, form);
        Pager pager = new Pager(pagedList.getList(), pagedList.getTotalCount(), pagedList.getTotalPageCount(), form.getPage(), form.getSize());
        return pager;
    }

    public static <A> JsonNode genePagerJson(Query<A> query, PageForm form) {
        Pager<A> aPager = genePager(query, form);
        return aPager.toJson();
    }

    public String toJsonStr() {
        return Json.stringify(this.toJson());
    }

    public JsonNode toJson() {
        Map<String, Object> map = new HashMap(6);
        map.put("totalRows", Integer.valueOf(this.totalRows));
        map.put("pageCount", Integer.valueOf(this.pageCount));
        map.put("curPage", Integer.valueOf(this.curPage));
        map.put("pageSize", Integer.valueOf(this.pageSize));
        map.put("data", this.innerData);
        return Json.toJson(map);
    }
}
