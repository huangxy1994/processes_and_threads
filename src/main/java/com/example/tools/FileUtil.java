package com.example.tools;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Pattern;

public class FileUtil {

    /**
     * 获取当前工程目录的绝对路径
     *
     * @return 工程目录的绝对路径
     */
    public static String get_engineering_path() {
        File directory = new File("");
        String path = null;
        try {
            path = directory.getAbsolutePath(); //获取绝对路径
        } catch (Exception e) {
            e.getStackTrace();
        }
        return path;
    }

    /**
     * 将两段路径拼接起来，然后返回绝对路径
     *
     * @param path1 第一个路径
     * @param path2 第二个路径
     * @return 拼接起来的绝对路径
     */
    public static String join(String path1, String path2) {
        String sep = System.getProperty("file.separator");
        String path = path1 + sep + path2;
        File directory = new File(path);
        String real_path = null;
        try {
            // real_path = directory.getAbsolutePath(); //获取绝对路径
            real_path = directory.getPath(); //获取绝对路径
        } catch (Exception e) {
            e.getStackTrace();
        }
        return real_path;
    }

    /**
     * 将多个路径拼接起来，然后返回绝对路径
     *
     * @param paths 多个路径
     * @return 拼接起来的绝对路径
     */
    public static String join(String... paths) {
        String real_path = "";
        for (String p : paths) {
            real_path = (real_path.isEmpty()) ? p : join(real_path, p);
        }
        return real_path;
    }

    /**
     * 拷贝文件
     *
     * @param src  源文件
     * @param dest 目标文件
     */
    public static void copy(File src, File dest) {
        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(src).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * 直接在本文件修改内容
     *
     * @param fileName 文件路径
     * @param oldstr   旧的字符串
     * @param newStr   新的字符串
     * @return 是否修改成功
     */
    public static boolean modifyFileContent(String fileName, String oldstr, String newStr) {
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            String line = null;
            // 读取
            while ((line = raf.readLine()) != null) {
                String line2 = new String(line.getBytes("ISO-8859-1"), "utf-8");
                lines.add(line2);
                if (line2.contains(oldstr)) {
                    indexes.add(lines.size() - 1);
                }
            }
            // 替换
            for (int i = 0; i < indexes.size(); i++) {
                int index = indexes.get(i);
                String str1 = lines.get(index);
                String str2 = str1.replace(oldstr, newStr);
                lines.set(index, str2);
            }
            // 写入
            raf.seek(0);
            for (int i = 0; i < lines.size(); i++) {
                String str = lines.get(i) + "\n";
                raf.write(str.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 修改文件内容，并写入到新的文件中
     *
     * @param fileName 文件路径
     * @param destName 新的文件路径
     * @param oldstr   旧的字符串
     * @param newStr   新的字符串
     * @return 是否修改成功
     */
    public static boolean modifyFileContent(String fileName, String destName, String oldstr, String newStr) {
        RandomAccessFile raf = null;
        RandomAccessFile raf2 = null;
        clearFile(destName);
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf2 = new RandomAccessFile(destName, "rw");
            String line = null;
            // 读取
            while ((line = raf.readLine()) != null) {
                String line2 = new String(line.getBytes("ISO-8859-1"), "utf-8");
                String str = "";
                if (line2.contains(oldstr)) {
                    str = line2.replace(oldstr, newStr) + "\n";
                } else {
                    str = line2 + "\n";
                }
                raf2.write(str.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 如果文件存在，清空文件内容，如果不存在，什么都不做
     *
     * @param fileName 文件路径
     */
    public static void clearFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 获取指定路径下所有文件
     *
     * @param path 指定路径
     * @return 所有文件
     */
    public static ArrayList<File> get_all_file(String path) {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(path);
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                ArrayList<File> files2 = get_all_file(FileUtil.join(path, f.getName()));
                files.addAll(files2);
            } else {
                files.add(f);
            }
        }
        files.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() > o2.lastModified()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return files;
    }

    /**
     * 获取指定路径下所有文件，指定后缀名
     *
     * @param path    指定路径
     * @param endWith 后缀名
     * @return 所有文件
     */
    public static File[] get_all_file(String path, String endWith) {
        File file = new File(path);
        File[] fs = file.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(String.format(".*\\.%s", endWith));

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        });
        return fs;
    }

    /**
     * 获取指定路径下所有文件的绝对路径，按时间排序
     *
     * @param path 指定路径
     * @return ArrayList格式的所有文件路径名
     */
    public static ArrayList<String> get_all_filePath(String path) {
        ArrayList<String> files = new ArrayList<>();

        ArrayList<File> files1 = get_all_file(path);
        files1.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.lastModified() > o2.lastModified()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        for (File f : files1) {
            files.add(f.getAbsolutePath());
        }
        return files;
    }

    /**
     * 获取工程目录下所有文件的绝对路径，按时间排序
     *
     * @return ArrayList格式的所有文件路径名
     */
    public static ArrayList<String> get_all_filePath() {
        String path = FileUtil.get_engineering_path();
        return get_all_filePath(path);
    }

    /**
     * 获取指定类的绝对路径
     *
     * @return 指定类的绝对路径
     */
    public static String get_absolute_path() {
        String classname = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        try {
            Class cls = Class.forName(classname);
            String name = cls.getName().replace('.', '\\') + ".java";
            ArrayList<String> files = get_all_filePath();
            for (String path : files) {
                if (path.endsWith(name)) {
                    return path;
                }
            }
            return "";
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * 获取指定类所在目录的绝对路径
     *
     * @return 指定类所在目录的绝对路径
     */
    public static String get_absolute_dir() {
        String classname = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        try {
            Class cls = Class.forName(classname);
            String name = cls.getName().replace('.', '\\') + ".java";
            String simpleName = cls.getSimpleName() + ".java";
            ArrayList<String> files = get_all_filePath();
            for (String path : files) {
                if (path.endsWith(name)) {
                    int i = path.indexOf(simpleName);
                    String dir = path.substring(0, i);
                    return dir;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     */
    public static void create_file(String path) {
        File file = new File(path);
        if (!file.exists()) {  // 判断文件不存在才创建文件
            try {
                if (!file.getParentFile().exists()) {  // 先判断路径是不是存在，如果不存在要先创建目录
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();  // 创建文件
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    // public static void main(String[] args) {
    //     // System.out.println(FileUtil.class.getName());
    // String src_path = FileUtil.join(FileUtil.get_engineering_path(), PropertiesUtil.getValueByKey("src_apk"));
    // System.out.println(Arrays.toString(get_all_file(src_path, "apk")));
    //     System.out.println(FileUtil.get_all_filePath(src_path));
    // }
}
