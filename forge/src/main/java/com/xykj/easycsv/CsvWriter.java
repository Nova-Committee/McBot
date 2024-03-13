package com.xykj.easycsv;

import com.xykj.easycsv.Converter;
import com.xykj.easycsv.entity.CsvProperty;
import com.xykj.easycsv.entity.IgnoreField;
import com.xykj.easycsv.entity.Rule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 写出工具
 */
public class CsvWriter {

    private Rule rule;

    public CsvWriter(Rule rule) {
        if (rule==null){
            this.rule=new Rule(null,null,",");
        }else {
            this.rule=rule;
        }
    }

    /**
     * 属性-标题Map
     */
    Map<String, String> fieldTitleMap;

    public <T> void doWrite(String outputPath, List<T> dataList){

        File outputFile=new File(outputPath);
        Writer writer=null;
        try {
            outputFile.createNewFile();
            writer=new FileWriter(outputFile,true);
            for (int i = 0; i < dataList.size(); i++) {
                T t = dataList.get(i);
                String oneRow;
                if (i==0){
                    fieldTitleMap = getFieldTitleMap(t.getClass());
                    oneRow = getTitleRowStr();
                    writer.write(oneRow);
                }
                oneRow = getOneRowStr(t);
                writer.write(oneRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取一行的字符串
     * @param t
     * @param <T>
     * @return
     */
    private<T> String getOneRowStr(T t){
        List<String> oneRow = getOneRow(t);
        return listToStr(oneRow);
    }


    private<T> List<String> getOneRow(T t){
        List<String> result=new ArrayList<>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldTitleMap.containsKey(fieldName)){
                try {

                    field.setAccessible(true);

                    Object value =field.get(t);
                    if (value==null){
                        result.add("");
                    }else {
                        result.add(value.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 获取一行字符串
     * @return
     */
    private String listToStr(List<String> list){
        StringBuilder result=new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            String cell = list.get(i);
            if (i==0){
                if (rule.getStartWith()!=null){
                    result.append(rule.getStartWith());
                }
                result.append(cell);
                if (rule.getEndWith()!=null){
                    result.append(rule.getEndWith());
                }
            }else{
                result.append(rule.getSplit());
                if (rule.getStartWith()!=null){
                    result.append(rule.getStartWith());
                }
                result.append(cell);
                if (rule.getEndWith()!=null){
                    result.append(rule.getEndWith());
                }
            }
        }
        result.append("\n");
        return result.toString();
    }

//    public<T> Method getGetMethodByFieldName(Field field, T result) throws NoSuchMethodException {
//        String fieldName=field.getName();
//        Character firstChar = fieldName.charAt(0);
//        fieldName = firstChar.toString().toUpperCase(Locale.ROOT) + fieldName.substring(1);
//        String methodName="get"+fieldName;
//        Class<?> type = field.getType();
//        return result.getClass().getmethod()
//    }

    /**
     * 获取标题行字符串
     * @return
     */
    private String getTitleRowStr(){
        StringBuilder result=new StringBuilder("");
        boolean isFirst=true;
        List<String> titles = this.fieldTitleMap.keySet().stream().map(e -> this.fieldTitleMap.get(e)).collect(Collectors.toList());
        return listToStr(titles);
    }

    private<T> Map<String, String> getFieldTitleMap(Class<T> tClass){
        fieldTitleMap=new LinkedHashMap<>();
        for (Field field : tClass.getDeclaredFields()) {
            IgnoreField ignoreField = field.getAnnotation(IgnoreField.class);
            //如果有忽略注解，此字段将不处理
            if (ignoreField!=null){
                continue;
            }
            String fieldName = field.getName();
            CsvProperty annotation = field.getAnnotation(CsvProperty.class);
            if (annotation!=null){
                String titleName = annotation.value();
                if (!"".equals(titleName)){
                    fieldTitleMap.put(fieldName,titleName);
                }else {
                    fieldTitleMap.put(fieldName,fieldName);
                }
            }else {
                //如果此注解为空，则用类的属性名直接当标题
                fieldTitleMap.put(fieldName,fieldName);
            }

        }
        return fieldTitleMap;
    }


}
