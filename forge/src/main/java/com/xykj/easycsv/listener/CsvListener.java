package com.xykj.easycsv.listener;

import java.util.Map;

public interface CsvListener<T> {
    /**
     * 初始化一行数据
     * @param data  实例化后的对象
     * @param sourceColumn 源数据行字符串
     */
    void invoke(T data,String sourceColumn);

    /**
     * 初始化标题
     * @param titleIndexMap
     * @param sourceColumn
     */
    void invokeHead(Map<String, Integer> titleIndexMap,String sourceColumn);


    /**
     * 当出现错误行时
     * @param sourceColumn
     */
    void onError(Exception e,String sourceColumn);

    /**
     * 读数据完毕
     */
    void readOver();
}
