package uk.ac.ebi.biostudies.api.util;

import org.springframework.util.MultiValueMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataTableColumnInfo {
    private String name;
    private int index;
    private String dir;
    private int column;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public static Map<Integer, DataTableColumnInfo> ParseDataTableRequest(MultiValueMap<String,String> dtRequest) throws Exception{
        Set<String> keySet = dtRequest.keySet();
        Map<Integer, DataTableColumnInfo> resultMap = new HashMap<>();
        Map<Integer, Integer> orderMap = new HashMap<>();
        int begin =0;
        int end = 0;
        String pval;
        DataTableColumnInfo colInfo;
        int colIndex = 0;
        int orderIndex = 0;
        for(String key : keySet){
            if(key.contains("[name]") && key.contains("columns")){
                begin = key.indexOf("[");
                end = key.indexOf("]");
                pval = key.substring(begin+1, end);
                colIndex = Integer.parseInt(pval);
                colInfo = resultMap.get(colIndex);
                if(colInfo==null){
                    colInfo = new DataTableColumnInfo();
                    resultMap.put(colIndex, colInfo);
                }
                colInfo.column = colIndex;
                colInfo.name = dtRequest.getFirst(key);
            }else
            if(key.contains("order[") && key.contains("[column]")){
                begin = key.indexOf("[");
                end = key.indexOf("]");
                pval = key.substring(begin+1, end);
                orderIndex = Integer.parseInt(pval);
                orderMap.put(orderIndex, Integer.parseInt(dtRequest.getFirst(key)));
            }else
            if(key.contains("order[") && key.contains("[dir]")){
                begin = key.indexOf("[");
                end = key.indexOf("]");
                pval = key.substring(begin+1, end);
                String dir = dtRequest.getFirst(key);
                orderIndex = Integer.parseInt(pval);
                colIndex = (Integer)orderMap.get(orderIndex);
                colInfo = resultMap.get(colIndex);
                colInfo.setDir(dir);
            }
        }
        Set<Integer> resultKeySet = new HashSet<>(resultMap.keySet());
        for(Integer key:resultKeySet)
            if(resultMap.get(key).getDir()==null)
                resultMap.remove(key);
        return resultMap;
    }


}
