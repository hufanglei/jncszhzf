package com.java.activiti.util;

import com.java.activiti.model.UploadFileData;
import com.java.activiti.service.ServiceLocator;
import com.java.activiti.service.ZdService;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Constants {

    public static Logger logger = Logger.getLogger("jncszhzf");

    public static JsonConfig getJsonConfig() {
        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return "";
            }
        });
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return "";
            }
        });
        jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return "";
            }
        });
        jsonConfig.registerDefaultValueProcessor(Map.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return "";
            }
        });
        return jsonConfig;
    }

    public static String dateTimeToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ret = sdf.format(date);
        return ret;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ret = sdf.format(date);
        return ret;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 根据办理事项获取该事项对应的委办局用户名
     *
     * @param issueClass 事项分类，包括问题类还是咨询类
     * @param issueType  事项类型，例如私搭乱建、占道经营等
     * @return List<String> 相应委办局账号List
     */
    public static List<String> getUnitUserByIssueClassType(int issueClass, int issueType) {
        //TODO
        List<String> list = new ArrayList<String>();
        list.add("minzhengju01");
        return list;
    }

    /**
     * 将List转化为字符串
     *
     * @param list      待转化的list
     * @param separator 分隔符
     * @return String 按照分隔符连接的字符串
     */
    public static String listToString(List<String> list, String separator) {
        String result = "";
        if (list.size() == 0) {
            return result;
        }

        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (String st : list) {
            if (flag) {
                sb.append(",");
            } else {
                flag = true;
            }
            sb.append(st);
        }
        return sb.toString();
    }


    /**
     * 将获取到的附件写到指定路径下
     *
     * @throws IOException
     */
    public static Map<String, Object> writeFile(String rootDir, MultipartFile file) throws IOException {

        Constants.logger.info("rootDir---" + rootDir);

        int index = rootDir.lastIndexOf("\\");
        String projectPath = rootDir.substring(0, index);

        String annexRelativePath = "";
        String annexAbsolutePath = "";
        String suffix = "";
        String annexName = "";
        String saveAnnexName = "";
        String filesize = "";
        Map<String, Object> map = new HashMap<String, Object>();

        String realPath = "";
        if (!file.isEmpty()) {
            annexRelativePath = File.separator + Const.UPLOAD_FOLD_NAME + File.separator;
            /** 获取本地文件相对目录 **/
            annexAbsolutePath = projectPath + annexRelativePath;
            File Path = new File(annexAbsolutePath);
            if (!Path.exists()) {
                Path.mkdirs();
            }
            /** 获取本地文件绝对目录 **/
            suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            /** 获得文件后缀名 **/
            annexName = file.getOriginalFilename();
            /** 获得文件名 **/
            saveAnnexName = UUID.randomUUID().toString() + suffix;
            /** 拼接文件路径,以UUID生成文件名 **/

            double size = 0;
            if (file.getSize() > 1024 * 1024) {
                size = (double) Math.round(file.getSize() * 100 / 1024 / 1024) / 100.00;
                filesize = Double.toString(size) + "M";
            } else {
                size = (double) Math.round(file.getSize() * 100 / 1024) / 100.00;
                filesize = Double.toString(size) + "K";
            }
            // 还原小数点后两位
            realPath = annexAbsolutePath + saveAnnexName;
            /** 文件真实路径 **/
            File saveFile = new File(realPath);
            /** 创建一个要保存的新文件 **/

            Constants.logger.info("annexRelativePath---" + annexRelativePath);// /fileupload/
            Constants.logger.info("annexAbsolutePath---" + annexAbsolutePath);// /home/ld/coding/javaworkspace/mssq/WebRoot/fileupload/
            Constants.logger.info("suffix---" + suffix);// .py
            Constants.logger.info("annexName---" + annexName);// setBrightness.py
            Constants.logger.info("saveAnnexName---" + saveAnnexName);// 15b53447-c9d1-4ef1-b072-3c9b2d3e8187.py
            Constants.logger.info("filesize---" + filesize);// 0.24K
            Constants.logger.info("realPath---" + realPath);// /home/ld/coding/javaworkspace/mssq/WebRoot/fileupload/15b53447-c9d1-4ef1-b072-3c9b2d3e8187.py

            /** 将文件写入到新文件中 **/
            FileCopyUtils.copy(file.getBytes(), saveFile);
            map.put("annexName", annexName);
            map.put("saveAnnexName", saveAnnexName);
            map.put("filesize", filesize);

        }
        return map;
    }


    public static Map<String, Object> writeFile(String rootDir, UploadFileData fileData) throws IOException {

        Constants.logger.info("rootDir---" + rootDir);

        SystemProperties.loadConfFile();

        String savePath = SystemProperties.getFileupload()+File.separator;

//        int index = rootDir.lastIndexOf(File.separator);
//        String projectPath = rootDir.substring(0, index);

        String annexRelativePath = "";
        String annexAbsolutePath = "";
        String suffix = "";
        String annexName = "";
        String saveAnnexName = "";
        String filesize = "";
        String fileType="";
        Map<String, Object> map = new HashMap<String, Object>();
       /* "fileName": o.response.url,
                "originalFileName": o.response.name,
                "size": o.response.size,
                "type":2*/
        String realPath = "";
        if (fileData != null) {
//            annexRelativePath = File.separator + Const.UPLOAD_FOLD_NAME + File.separator;
            /** 获取本地文件相对目录 **/
//            annexAbsolutePath = rootDir + annexRelativePath;
            File Path = new File(savePath);
            if (!Path.exists()) {
                Path.mkdirs();
            }
            /** 获取本地文件绝对目录 **/
            suffix = fileData.getOriginalFileName().substring(fileData.getOriginalFileName().lastIndexOf("."));
            /** 获得文件后缀名 **/
            annexName = fileData.getOriginalFileName();
            fileType=fileData.getType();
            /** 获得文件名 **/
            saveAnnexName = UUID.randomUUID().toString() + suffix;
            /** 拼接文件路径,以UUID生成文件名 **/

            double size = 0;
            if (fileData.getSize() > 1024 * 1024) {
                size = (double) Math.round(fileData.getSize() * 100 / 1024 / 1024) / 100.00;
                filesize = Double.toString(size) + "M";
            } else {
                size = (double) Math.round(fileData.getSize() * 100 / 1024) / 100.00;
                filesize = Double.toString(size) + "K";
            }
            // 还原小数点后两位
            realPath = savePath + saveAnnexName;
            /** 文件真实路径 **/
            File saveFile = new File(realPath);
            /** 创建一个要保存的新文件 **/

            Constants.logger.info("annexRelativePath---" + annexRelativePath);// /fileupload/
            Constants.logger.info("annexAbsolutePath---" + annexAbsolutePath);// /home/ld/coding/javaworkspace/mssq/WebRoot/fileupload/
            Constants.logger.info("suffix---" + suffix);// .py
            Constants.logger.info("annexName---" + annexName);// setBrightness.py
            Constants.logger.info("saveAnnexName---" + saveAnnexName);// 15b53447-c9d1-4ef1-b072-3c9b2d3e8187.py
            Constants.logger.info("filesize---" + filesize);// 0.24K
            Constants.logger.info("realPath---" + realPath);// /home/ld/coding/javaworkspace/mssq/WebRoot/fileupload/15b53447-c9d1-4ef1-b072-3c9b2d3e8187.py

            /** 将文件写入到新文件中 **/
            FileCopyUtils.copy(new File(rootDir+File.separator+fileData.getFileName()), saveFile);
            map.put("annexName", annexName);
            map.put("saveAnnexName", saveAnnexName);
            map.put("filesize", filesize);
            map.put("fileType",fileType);

        }
        return map;
    }

    private   String getUploadTempDir() {
        String tempPath = this.getClass().getClassLoader().getResource("/").getPath();
        tempPath = tempPath.substring(0, tempPath.indexOf("WEB-INF"));
        String exportDir = tempPath + "tmpUpload" + File.separator;
        return exportDir;
    }

    /**
     * 判断一个字符串是否为NULL或空字符串
     *
     * @param s
     * @return 字符串为NULL，返回true；字符串为空或空格，范围true；其他情况返回false
     */
    public static boolean isEmptyOrNull(String s) {
        if (null == s || "".equals(s.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrNull(Object o) {
        if (null == o || "".equals(o.toString().trim())) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrNull(List l) {
        if (null == l || l.size() == 0) {
            return true;
        }
        return false;
    }

    public static String getMD5PassWord(String password) throws UnsupportedEncodingException {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(md5.digest(password.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 获取两个整数相除的比例，保留小数点后两位小数
     *
     * @param count 被除数
     * @param total 除数
     * @return 百分比字符串
     */
    public static String getRatio(int count, int total) {
        float countInput = count;
        float totalInput = total;
        float percent = countInput / totalInput;
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        String res = nt.format(percent);
        return res;
    }

    /**
     * 将日期字符串转换为Date对象 日期字符串格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getDateByString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static String getDictionaryValue(String key) {
        ServiceLocator service = ServiceLocator.getInstance();
        ZdService zdService = (ZdService) service.getService("zdServiceImpl");
        //ZdService zdService = new ZdServiceImpl();
        HashMap<String, String> map = zdService.getDictionary();
        return map.get(key);
    }
}
