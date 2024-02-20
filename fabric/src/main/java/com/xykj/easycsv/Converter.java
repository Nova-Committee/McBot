package com.xykj.easycsv;

import com.xykj.easycsv.entity.CsvProperty;
import com.xykj.easycsv.entity.Rule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 转化器
 */
public class Converter {

    /**
     * 分割工具
     * 分割工具
     */
    private Rule rule;

    public Converter(Rule rule) {
        this.rule = rule;
    }

    public Converter() {
        this.rule = null;
    }



    /**
     * 标题名-标题index
     */
    private Map<String,Integer> titleIndexMap =new LinkedHashMap<>();

    /**
     * 标题名-实体类属性名
     */
    Map<String, String> titleFieldMap=null;

    /**
     * 属性-坐标实体类
     */
    Map<String,Integer> fieldIndexMap=null;

    public Map<String, Integer> getFieldIndexMap() {
        return fieldIndexMap;
    }

    public void setFieldIndexMap(Map<String, Integer> fieldIndexMap) {
        this.fieldIndexMap = fieldIndexMap;
    }

    public Map<String, Integer> getTitleIndexMap() {
        return titleIndexMap;
    }

    public Map<String, String> getTitleFieldMap() {
        return titleFieldMap;
    }

    public void setTitleIndexMap(String titleColumn){
        String[] strList = getStrList(titleColumn);
        for (int i = 0; i < strList.length; i++) {
            String title = strList[i];
            if (i==0&&title!=null){
                char c = title.charAt(0);
                if (c=='\uFEFF'){
                    title=title.substring(1);
                }
            }
            titleIndexMap.put(title,i);
        }
    }

    /**
     * 获取一个标题-参数名map
     * @param a
     * @param <T>
     * @return
     */
    public <T> Map<String,String> getTitleFieldMap(Class<T> a){

        if (this.titleFieldMap!=null){
            return titleFieldMap;
        }
        Map<String,Integer> fieldIndexMap=new LinkedHashMap<>();
        Map<String,String> exprMap=new HashMap<>();
        for (Field field : a.getDeclaredFields()) {
            CsvProperty annotation = field.getAnnotation(CsvProperty.class);
            if (annotation==null){
                continue;
            }
            String name = field.getName();
            int index = annotation.index();
            if (index!=-1){
                fieldIndexMap.put(name,index);
            }
            String titleName = annotation.value();
            if (!"".equals(titleName)){
                exprMap.put(name,titleName);
            }
        }
        this.fieldIndexMap=fieldIndexMap;
        this.titleFieldMap=exprMap;
        return exprMap;
    }

    /**
     * 获取一个实例
     * @param str
     * @param classA
     * @param <T>
     * @return
     */
    public  <T> T getT(String str,Class<T> classA) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String[] cells = getStrList(str);
        final Map<String, String> titleFieldMap = getTitleFieldMap(classA);
        return getT(cells,classA,titleFieldMap);
    }

    public Map<Integer,String> getMap(String str){
        String[] cells = getStrList(str);
        Map<Integer,String> result=new LinkedHashMap<>();
        for (int i = 0; i < cells.length; i++) {
            result.put(i,cells[i]);
        }
        return result;
    }

    /**
     * 获取一条实例
     * @param cells  一行数据
     * @param a  返回对象的类型
     * @param titleFieldMap  标题-参数map  参数为对象的参数
     * @param <T>
     * @return
     */
    private  <T> T getT(String[] cells, Class<T> a,Map<String, String> titleFieldMap) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        T result = a.newInstance();
        Field[] fields = result.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            //先通过索引注解查询
            Integer index = this.fieldIndexMap.get(fieldName);
            if (index != null) {
                if(index>cells.length-1){
                    break;
                }
                String cellValue = cells[index];
                invoke(cellValue, field, result);
            }
            String title = titleFieldMap.get(fieldName);
            if (title != null) {
                String cellValue = getCellByTitle(title, cells);
                invoke(cellValue, field, result);
            }
        }
        return result;
    }

    /**
     * 设置值
     * @param cellValue
     * @param method
     * @param result
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public<T> void invoke(String cellValue,Method method,T result) throws InvocationTargetException, IllegalAccessException {
        if (cellValue!=null){
            Class<?> parameterType = method.getParameterTypes()[0];
            Object value = parseValue(parameterType, cellValue);
            if (value!=null){
                method.invoke(result,value);
            }
        }
    }

    public<T> void invoke(String cellValue,Field field,T result) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (cellValue!=null){
            Class<?> parameterType = field.getType();
            Method method = getSetMethodByFieldName(field, result);
            Object value = parseValue(parameterType, cellValue);
            if (value!=null){
                method.invoke(result,value);
            }
        }
    }

    public<T> Method getSetMethodByFieldName(Field field, T result) throws NoSuchMethodException {
        String fieldName=field.getName();
        Character firstChar = fieldName.charAt(0);
        fieldName = firstChar.toString().toUpperCase(Locale.ROOT) + fieldName.substring(1);
        String methodName="set"+fieldName;
        return result.getClass().getMethod(methodName,field.getType());
    }

    /**
     *
     * @param title
     * @param cells
     * @return
     */
    public String getCellByTitle(String title,String[] cells){
        Integer index = titleIndexMap.get(title);
        if (index!=null&&index!=-1){
            return cells[index];
        }
        return null;
    }

    private String split=null;

    /**
     * 将一整行字符串分割成一个个单元格
     * @param str
     * @return
     */
    private String[] getStrList(String str){
        if (rule!=null){
            getStrListByRule(str);
        }
        return getDefaultStrList(str);
    }

    public String[] getStrListByRule(String str){
        if (split==null){
            String startWith = "";
            if (rule.getStartWith()!=null){
                startWith=rule.getStartWith();
            }
            String endWith = "";
            if (rule.getEndWith()!=null){
                endWith=rule.getEndWith();
            }
            String split = rule.getSplit();
            this.split=endWith + split + startWith;
        }
        String[] split1 = split(str,this.split);
        String first = split1[0];
        String last = split1[split1.length-1];
        split1[0]=first.replaceFirst(rule.getStartWith(),"");
        split1[split1.length-1]=last.substring(0,last.lastIndexOf(rule.getEndWith()));
        return split1;
    }

    public String[] getDefaultStrList(String str){
//        String[] split = str.split(",");
        String[] split = split(str,",");
        for (int i = 0; i < split.length; i++) {
            String s=split[i];
            if ((s.indexOf("\"")==0)&&(s.lastIndexOf("\"")==(s.length()-1))){
                s=s.substring(1,s.length()-2);
            }
            split[i]=s;
        }
        return split;
    }

    private static String[] split(String str,String regex){
        List<String> cellList=new ArrayList<>();
        int fromIndex=0;
        int cellStartIndex=0;
        while (cellStartIndex<=str.length()-1){
            fromIndex = str.indexOf(regex, fromIndex);
            if (fromIndex==-1){
                fromIndex=str.length();
            }
            int cellEndIndex =fromIndex;
            if (cellStartIndex==fromIndex){
                cellList.add("");
            }else {
                cellList.add(str.substring(cellStartIndex,cellEndIndex));
            }
            fromIndex=fromIndex+regex.length();
            cellStartIndex=fromIndex;
        }
        if (str.lastIndexOf(regex)+regex.length()==str.length()){
            cellList.add("");
        }
        String [] result=new String[cellList.size()];
        for (int i = 0; i < cellList.size(); i++) {
            result[i]=cellList.get(i);
        }
        return result;
    }

    /**
     * 获取参数名称
     * @param method
     * @return
     */
    public String getFieldName(Method method){
        String name = method.getParameters()[0].getName();
        return name;
    }


    /**
     * 将表格的值转换成对象属性的类型
     * @param parameterType
     * @param cellValue
     * @return
     */
    public Object parseValue(Class<?> parameterType,String cellValue){
//        String parameterTypeStr = parameterType.toString();
        Object result=null;
        try {
            if (parameterType.equals(BigDecimal.class)){
                result=new BigDecimal(cellValue);
            }else if (cellValue==null){
                System.out.println("一条空值");
            }else if (parameterType.equals(String.class)){
                result=cellValue;
            }else if (parameterType.equals(Integer.class)|| "int".equals(parameterType.getName())){
                result= parseInt(cellValue);
            }else if (parameterType.equals(Float.class)|| "float".equals(parameterType.getName())){
                result=parseFloat(cellValue);
            }else if (parameterType.equals(Double.class)|| "double".equals(parameterType.getName())){
                result=parseDouble(cellValue);
            }else if (parameterType.equals(Long.class)|| "long".equals(parameterType.getName())){
                result=parseLong(cellValue);
            }else if (parameterType.equals(Short.class)|| "short".equals(parameterType.getName())){
                result=parseShort(cellValue);
            }else if (parameterType.equals(Boolean.class)|| "boolean".equals(parameterType.getName())){
                result=Boolean.parseBoolean(cellValue);
            }else if (parameterType.equals(Character.class)||"char".equals(parameterType.getName())){
                result=cellValue.charAt(0);
            }
        }catch (Exception e){
            System.out.println("类型转换异常-无法将值‘"+cellValue+"’转换成‘"+parameterType+"’类型");
            e.printStackTrace();
        }
        return result;
    }

    private int parseInt(String value){
        if (value==null||"".equals(value)){
            return 0;
        }
        return new BigDecimal(value).intValue();
    }
    private float parseFloat(String value){
        if (value==null||"".equals(value)){
            return 0;
        }
        return new BigDecimal(value).floatValue();
    }
    private long parseLong(String value){
        if (value==null||"".equals(value)){
            return 0;
        }
        return new BigDecimal(value).longValue();
    }
    private double parseDouble(String value){
        if (value==null||"".equals(value)){
            return 0;
        }
        return new BigDecimal(value).doubleValue();
    }
    private short parseShort(String value){
        if (value==null||"".equals(value)){
            return 0;
        }
        return new BigDecimal(value).shortValue();
    }
}
