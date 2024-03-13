package com.xykj.easycsv.entity;

/**
 * csv分割规则
 */
public class Rule {
    /**
     * 开始符号
     */
    private String startWith="";

    /**
     * 结束符号
     */
    private String endWith="";

    /**
     * 分隔符号
     */
    private String split;


    public Rule(String startWith, String endWith, String split) {
        this.startWith = startWith;
        this.endWith = endWith;
        if (split==null||"".equals(split)){
            System.out.println("分割符不能为空");
        }
        this.split = split;
    }

    public String getStartWith() {
        return startWith;
    }

    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }

    public String getEndWith() {
        return endWith;
    }

    public void setEndWith(String endWith) {
        this.endWith = endWith;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }
}
