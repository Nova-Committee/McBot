package com.xykj.easycsv;

import com.xykj.easycsv.entity.Rule;
import com.xykj.easycsv.listener.CsvListener;
import com.xykj.easycsv.listener.CsvToMapListener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EasyCsv {

    public String getCharsetName() {
        if (charsetName!=null&& !charsetName.isEmpty()){
            return charsetName;
        }else {
            return "GBK";
        }
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    /**
     * 编码集合
     */
    private String charsetName;

    private Rule rule;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public EasyCsv(String charsetName) {
        this.charsetName = charsetName;
    }

    public EasyCsv(String charsetName, Rule rule) {
        this.charsetName = charsetName;
        this.rule = rule;
    }

    public EasyCsv() {
    }


    public EasyCsv(Rule rule) {
        this.rule = rule;
    }


    /**
     * 生成CSV文件
     * @param filePath
     * @param dataList
     * @param <T>
     */
    public <T> void write(String filePath,List<T> dataList){
        new CsvWriter(this.rule).doWrite(filePath,dataList);
    }


    /**
     * 获取所有数据
     * @param fileName 文件路径
     * @param classA  实体类class 类型
     * @param <T>
     * @return
     */
    public <T> List<T> readAll(String fileName, Class<T> classA){
        try {
            return read(new FileInputStream(fileName), classA,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 流方式读
     * @param inputStream
     * @param classA
     * @param <T>
     * @return
     */
    public <T> List<T> readAll(InputStream inputStream, Class<T> classA) {
        return read(inputStream, classA,null);
    }


    /**
     * 以监听器的模式读取csv文件
     * @param fileName
     * @param classA
     * @param csvListener
     * @param <T>
     */
    public <T> void doRead(String fileName,Class<T> classA, CsvListener<T> csvListener) {
        try {
            read(new FileInputStream(fileName),classA,csvListener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流方式读
     * @param inputStream
     * @param classA
     * @param csvListener
     * @param <T>
     */
    public <T> void doRead(InputStream inputStream,Class<T> classA, CsvListener<T> csvListener) {
        read(inputStream,classA,csvListener);
    }


    /**
     * 以监听器的方式读取csv文件转为Map对象
     * @param fileName
     * @param csvToMapListener
     */
    public void doReadCsvByMap(String fileName, CsvToMapListener csvToMapListener){
        readToMap(fileName, csvToMapListener);
    }

    /**
     * 内部读取将csv读取为Map结构
     * @param fileName
     * @param csvListener
     * @return
     */
    private List<Map<Integer,String>> readToMap(String fileName, CsvToMapListener csvListener) {
        BufferedReader reader = null;
        List<Map<Integer,String>>  result = new ArrayList<>();
        InputStream resourceAsStream=null;
        try {
            resourceAsStream = Files.newInputStream(Paths.get(fileName));
            reader = new BufferedReader(new InputStreamReader(resourceAsStream,"GBK"));
            String oneColumnStr;
            int i=0;
            Converter converter=new Converter(this.rule);
            while ((oneColumnStr = reader.readLine()) != null) {
                if (i==0){
                    converter.setTitleIndexMap(oneColumnStr);
                    if (csvListener!=null){
                        csvListener.invokeHead(converter.getTitleIndexMap(),oneColumnStr);
                    }
                }else {
                    if (csvListener!=null){
                        csvListener.invoke(converter.getMap(oneColumnStr),oneColumnStr);
                    }else {
                        result.add(converter.getMap(oneColumnStr));
                    }
                }
                i++;
            }
            if (csvListener!=null){
                csvListener.readOver();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    resourceAsStream.close();
                    reader.close();
                } catch (IOException e1) { }
            }
        }
        return result;
    }




    /**
     * 读取csv的内部方法
     * @param inputStream
     * @param classA
     * @param csvListener
     * @param <T>
     * @return
     */
    private <T> List<T> read(InputStream inputStream,Class<T> classA, CsvListener<T> csvListener) {
        BufferedReader reader = null;
        List<T> result = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream,getCharsetName()));
            String oneColumnStr;
            int i=0;
            Converter converter=new Converter(this.rule);
            while ((oneColumnStr = reader.readLine()) != null) {
                //如果开发人员没有指定编码格式，程序会自己检验字符格式并进行校正
                if (charsetName==null){
                    String encoding = getEncoding(new ByteArrayInputStream(oneColumnStr.getBytes()));
                    oneColumnStr=new String(oneColumnStr.getBytes(encoding), StandardCharsets.UTF_8);
                }
                if (i==0){
                    converter.setTitleIndexMap(oneColumnStr);
                    if (csvListener!=null){
                        csvListener.invokeHead(converter.getTitleIndexMap(),oneColumnStr);
                    }
                }else {
                    try {
                        if (csvListener!=null){
                            csvListener.invoke(converter.getT(oneColumnStr, classA),oneColumnStr);
                        }else {
                            result.add(converter.getT(oneColumnStr, classA));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        if (csvListener!=null){
                            csvListener.invoke(classA.newInstance(),oneColumnStr);
                            csvListener.onError(e,oneColumnStr);
                        }else {
                            result.add(classA.newInstance());
                        }
                    }
                }
                i++;
            }
            if (csvListener!=null){
                csvListener.readOver();
            }
            reader.close();
        } catch (IOException | InstantiationException | IllegalAccessException ignored) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }


    /**
     * 获取内容编码
     * @return
     */
    private static String getEncoding(ByteArrayInputStream  is) {
        is.mark(0);
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {

            boolean checked = false;

            int read = is.read(first3Bytes, 0, 3);

            if (read == -1) {
                return charset;
            }
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xA
                    && first3Bytes[1] == (byte) 0x5B
                    && first3Bytes[2] == (byte) 0x30) {
                charset = "UTF-8";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xD
                    && first3Bytes[1] == (byte) 0xA
                    && first3Bytes[2] == (byte) 0x5B) {
                charset = "GBK";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0x5B
                    && first3Bytes[1] == (byte) 0x54
                    && first3Bytes[2] == (byte) 0x49) {
                charset = "windows-1251";
                checked = true;
            }
            is.reset();
            is.mark(0);
            if (!checked) {
                int loc = 0;
                while ((read = is.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = is.read();
                        if (0x80 <= read && read <= 0xBF) {
                            continue;
                        } else {
                            break;
                        }
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = is.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = is.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            is.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
