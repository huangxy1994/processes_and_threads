package com.example.tools;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    private static String src = "/src/main/java/com/example/config/config.properties";
    private static InputStreamReader inputStream = null;
    private static OutputStreamWriter outputStream = null;
    private static String encode = "utf-8";
    public static Properties properties;

    public PropertiesUtil() {
    }

    public PropertiesUtil(String path) {
        this.src = path;
    }

    /**
     * 加载properties文件
     *
     * @return Properties
     */
    public static Properties load() {
        if (src.trim().equals("")) {
            throw new RuntimeException("文件不存在");
        }

        String project_path = FileUtil.get_engineering_path();
        String path = FileUtil.join(project_path, src);

        try {
            inputStream = new InputStreamReader(new FileInputStream(path), encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 保存properties文件
     *
     * @param map  内容
     * @param path 文件路径
     */
    public static void store(Map<String, String> map, String path) {
        try {
            properties = new Properties();
            outputStream = new OutputStreamWriter(new FileOutputStream(path));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(outputStream, "The New properties file");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 添加内容
     *
     * @param map  内容
     * @param path 文件路径
     */
    public static void add(Map<String, String> map, String path) {
        try {
            properties = new Properties();
            outputStream = new OutputStreamWriter(new FileOutputStream(path, true));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(outputStream, "Add content");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 添加内容
     *
     * @param key   键
     * @param value 值
     * @param path  文件路径
     */
    public static void add(String key, String value, String path) {
        try {
            properties = new Properties();
            outputStream = new OutputStreamWriter(new FileOutputStream(path, true));
            properties.setProperty(key, value);
            properties.store(outputStream, "Add content");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 通过关键字获取值
     *
     * @param properties properties
     * @param key        关键字
     * @return 值
     */
    public static String getValueByKey(Properties properties, String key) {
        String val = properties.getProperty(key.trim());
        close();
        return val;

    }

    /**
     * 通过关键字获取值
     *
     * @param key 关键字
     * @return 值
     */
    public static String getValueByKey(String key) {
        properties = load();
        String val = properties.getProperty(key.trim());
        close();
        return val;
    }

    /**
     * 通过关键字获取值
     *
     * @param path 文件路径
     * @param key  关键字
     * @return 值
     */
    public static String getValue(String path, String key) {
        src = path;
        properties = load();
        String val = properties.getProperty(key.trim());
        close();
        return val;
    }

    /**
     * 获取Properties所有的值
     *
     * @return 文件里所有的值
     */
    public Map<String, String> getAllProperties() {
        properties = load();
        Map<String, String> map = new HashMap<>();
        // 获取所有的键值
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = getValueByKey(properties, key);
            map.put(key, value);
        }
        close();
        return map;
    }

    /**
     * 关闭输入输出流
     */
    public static void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            src = "/src/main/java/com/example/config/config.properties";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getValueByKey("company"));
    }
}