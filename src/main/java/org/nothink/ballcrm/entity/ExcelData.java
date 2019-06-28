package org.nothink.ballcrm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * excel的一个sheet数据存储类
 */
public class ExcelData implements Serializable {
    private static final long serialVersionUID = 4444017239100620999L;
    // 表头
    private List<String> titles;
    // 数据
    private List<List<Object>> rows;
    // 页签名称
    private String name;

    @Override
    public String toString() {
        return "ExcelData{" +
                "titles=" + titles +
                ", rows=" + rows +
                ", name='" + name + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}