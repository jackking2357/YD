package com.yudian.common.utils.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoModleDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private List<Map<Integer, Map<Integer, String>>> list;

    private Map<Integer, String> headTitleMap = new HashMap<>();

    public NoModleDataListener() {
        list = new ArrayList<>();
    }

    
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        System.out.println("解析单行数据：" + JSON.toJSONString(data));
        Map<Integer, Map<Integer, String>> map = new HashMap<>();
        map.put(context.readRowHolder().getRowIndex(), data);
        list.add(map);
    }

    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成");
    }

    
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headTitleMap = headMap;
    }

    public List<Map<Integer, Map<Integer, String>>> getList() {
        return list;
    }

    public Map<Integer, String> getHeadTitleMap() {
        return headTitleMap;
    }
}
