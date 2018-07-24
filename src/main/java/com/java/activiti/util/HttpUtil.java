package com.java.activiti.util;

import com.java.activiti.model.FileBean;
import com.java.activiti.model.QwgBodyBean;
import com.java.activiti.model.QwgRowBean;
import com.java.activiti.model.TQwgDataBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;


/**
 * 使用普通的HTTP发送GET和POST请求
 *
 * @author Administrator
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     * @throws IOException 网络连接失败时抛出异常
     */

    public static String sendGet(String url, String param) throws IOException {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = "";
            if (param == null || param.equals("")) {
                urlName = url;
            } else {
                urlName = url + "?" + param;
            }
//			System.out.println("访问链接为"+urlName);
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //建立实际的连接
            conn.connect();
            //获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            //遍历所有的响应头字段
//			for (String key: map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "  " + line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("发送GET请求出现异常！" + e);
            throw e;
        }

        //使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(param);
            //flush输出流的缓冲
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "  " + line;
            }
        } catch (Exception e) {
            logger.error("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 使用httpclient的Get方法
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String response = EntityUtils
                        .toString(httpResponse.getEntity());
                return response;
            } else {
                System.out.println("错误");
                System.out.println(httpResponse.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw e;
        }
        return "";
    }


    /**
     * 获取初始化公共参数
     *
     * @return
     */
    private static String getInitParam() {
        SystemProperties.loadConfFile();

        String params = "";
        params += "appKey=" + SystemProperties.getTianque_appKey();
        params += "&userName=" + SystemProperties.getTianque_userName();
        params += "&passWordMD5=" + SystemProperties.getTianque_passwordmd5();

        return params;
    }

    /**
     * 获取Sign值
     *
     * @return sign值 或 null（如果失败）
     */
    public static String getSign(String appendParams) {
        String result = sendPost(SystemProperties.getTianque_sign_host() + SystemProperties.getTianque_sign_url(), appendParams);
        JSONObject json = JSONObject.fromObject(result);

        if (json.getBoolean("success")) {
            System.out.println(json.getString("sign"));
            return json.getString("sign");
        } else {
            return null;
        }
    }

    /**
     * 查询数据
     *
     * @return
     */
    public static QwgBodyBean queryData(int page, int rows) {

        String params = getInitParam();
        params += "&page=" + page;
        params += "&rows=" + rows;

        String sign = getSign(params);
        params += "&sign=" + sign;

        String result = sendPost(SystemProperties.getTianque_events_host() + SystemProperties.getTianque_query_url(), params);


        logger.info("HttpUtil.queryData 返回数据：%s ", result);
        JSONObject jsonResult = JSONObject.fromObject(result);

        if (jsonResult.getJSONObject("body").toString() == "null") {
            return null;
        }

        Map map = new HashMap();
        map.put("rows", QwgRowBean.class);
        map.put("flowAttachFileDubbos", FileBean.class);
        QwgBodyBean qwgBodyBean = (QwgBodyBean) JSONObject.toBean(jsonResult.getJSONObject("body"), QwgBodyBean.class, map);

        return qwgBodyBean;
    }

    /**
     * 反馈事件状态
     *
     * @param orderCode
     * @param operationType : 0 : 退回； 1: 办结； 2：受理；
     * @return
     */

    public static final int OPT_REJECT = 0;
    public static final int OPT_COMPLETE = 1;
    public static final int OPT_RECIEVE = 2;

    public static Map feedback(String orderCode, String comments, String fileNames, Map<String,String> filePaths, int operationType) {


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appKey", SystemProperties.getTianque_appKey());
        paramMap.put("userName", SystemProperties.getTianque_userName());
        paramMap.put("passWordMD5", SystemProperties.getTianque_passwordmd5());
        paramMap.put("orderCode", orderCode);
        paramMap.put("operationType", operationType);
        paramMap.put("dealView.suggestion", comments);
        paramMap.put("dealView.dealAttachFileNames", fileNames);

        String sign = null;
        try {
            sign = getSignByMultiPartPost(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        paramMap.put("sign", sign);

//        List<String> filePathList = new ArrayList<>();
//        if(StringUtils.isNotBlank(filePaths)){
//            filePathList = Arrays.asList(filePaths.split(","));
//        }
        String result = null;
        try {
            result = sendMultiPartPost(SystemProperties.getTianque_events_host() + SystemProperties.getTianque_feedback_url(), paramMap, filePaths);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.fromObject(result);

        boolean isSuccess = (jsonObject.getString("status").equals("200"));
        Map resMap = new HashMap();
        resMap.put("success", isSuccess);

        if (!isSuccess) {
            resMap.put("errMsg", jsonObject.getString("error"));
        }

        return resMap;
    }

    private static String getSignByMultiPartPost(Map<String, Object> paramMap) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(SystemProperties.getTianque_sign_host() + SystemProperties.getTianque_sign_url());
        //加上该header访问会404,不知道原因...
        //httpPost.setHeader("Content-Type", "multipart/form-data; boundary=-----ZR8KqAYJyI2jPdddL");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(CharsetUtils.get("UTF-8"));
        //以浏览器兼容模式访问,否则就算指定编码格式,中文文件名上传也会乱码


        for (String key : paramMap.keySet()) {
            builder.addPart(key, new StringBody(String.valueOf(paramMap.get(key)), Charset.defaultCharset()));
        }

        HttpEntity reqEntity = builder.build();
        httpPost.setEntity(reqEntity);
        HttpResponse response = httpClient.execute(httpPost);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

            HttpEntity entitys = response.getEntity();
            if (entitys != null) {
                String signData = EntityUtils.toString(entitys);
                JSONObject json = JSONObject.fromObject(signData);
                return json.getString("sign");
            }
        }

        return null;
    }

    private static String sendMultiPartPost(String url, Map<String, Object> paramMap, Map<String,String> filePathMap) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        //加上该header访问会404,不知道原因...
        //httpPost.setHeader("Content-Type", "multipart/form-data; boundary=-----ZR8KqAYJyI2jPdddL");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(CharsetUtils.get("UTF-8"));
        //以浏览器兼容模式访问,否则就算指定编码格式,中文文件名上传也会乱码


        for (String key : paramMap.keySet()) {
            builder.addPart(key, new StringBody(String.valueOf(paramMap.get(key)), Charset.defaultCharset()));
        }

        if (filePathMap != null) {
            for (String key : filePathMap.keySet()) {
                builder.addPart(key, new FileBody(new File(filePathMap.get(key))));
            }
        }


        HttpEntity reqEntity = builder.build();
        httpPost.setEntity(reqEntity);
        HttpResponse response = httpClient.execute(httpPost);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

            HttpEntity entitys = response.getEntity();
            if (entitys != null) {
                return EntityUtils.toString(entitys);
            }
        }

        return null;
    }

    public static Map feedback(String orderCode, int operationType) {
        return feedback(orderCode, "", "", null, operationType);
    }

    public static void downLoadFileToLocal(String remoteFileURL, String localFileName) throws IOException {

        if (StringUtils.isBlank(remoteFileURL)) {
            throw new IOException("文件不存在！");
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(remoteFileURL);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        File file = new File(localFileName);
        try {
            OutputStream os = new FileOutputStream(file);

            int read = 0;
            byte[] temp = new byte[1024 * 1024];

            while ((read = in.read(temp)) > 0) {
                byte[] bytes = new byte[read];
                System.arraycopy(temp, 0, bytes, 0, read);
                os.write(bytes);
            }
            os.flush();
        } finally {
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }


    public static void main(String[] args) {
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        QwgBodyBean qwgBodyBean = queryData(1, 20);

        if (qwgBodyBean == null) {
            return;
        }
        int page = 1;
        int totalPage = qwgBodyBean.getTotal();
        int records = qwgBodyBean.getRecords();

        List<TQwgDataBean> tQwgDataBeans = new ArrayList<TQwgDataBean>();
        while (page <= totalPage) {
            List<QwgRowBean> qwgRowBeans = qwgBodyBean.getRows();
            for (QwgRowBean qwgRowBean : qwgRowBeans) {
                TQwgDataBean tQwgDataBean = new TQwgDataBean();
                tQwgDataBean.setCompletedata(qwgRowBean.getCompleteData());
                tQwgDataBean.setContent(qwgRowBean.getContent());

                List<FileBean> attatches = qwgRowBean.getFlowAttachFileDubbos();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < attatches.size(); i++) {
                    sb.append(attatches.get(i));
                }
                String attatchesStr = sb.toString();
                tQwgDataBean.setFlowattachfiledubbos(attatchesStr);
                tQwgDataBean.setIssuetypeid(qwgRowBean.getIssueTypeId());
                tQwgDataBean.setOccurdate(qwgRowBean.getOccurDate());
                tQwgDataBean.setOccurlocation(qwgRowBean.getOccurLocation());
                tQwgDataBean.setOccurorg(qwgRowBean.getOccurOrg());
                tQwgDataBean.setOrderno(qwgRowBean.getOrderNo());
                tQwgDataBean.setSourceid(qwgRowBean.getSourceId());
                tQwgDataBean.setSourcemobile(qwgRowBean.getSourceMobile());
                tQwgDataBean.setSourcename(qwgRowBean.getSourceName());
                tQwgDataBean.setSourceperson(qwgRowBean.getSourcePerson());
                tQwgDataBean.setTitle(qwgRowBean.getTitle());

                tQwgDataBeans.add(tQwgDataBean);
            }
            page++;
        }

        String str = "{\"sourceId\": \"3\",\"orderNo\": \"ISSUE20171013113351881003\",\"sourcePerson\": \"测试江宁综合执法14\",\"sourceMobile\": \"13111111111\",\"flowAttachFileDubbos\": [{\"fileNam\":\"fsfds\"}],\"occurLocation\": \"测试江宁综合执法14\",\"completeData\": \"\",\"title\": \"测试江宁综合执法14\",\"occurDate\": \"2017-10-12 00:00:00\",\"content\": \"测试江宁综合执法14\",\"issueTypeId\": 10011,\"occurOrg\": \"测试江宁综合执法14\",\"id\": 103,\"sourceName\": \"12345系统\"}";
        JSONObject json = JSONObject.fromObject(str);
        QwgRowBean qwgRowBean = (QwgRowBean) JSONObject.toBean(json, QwgRowBean.class);
        List<FileBean> attatches = qwgRowBean.getFlowAttachFileDubbos();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < attatches.size(); i++) {
            sb.append(attatches.get(i).getPhysicsFullFileName());
        }

//		System.out.println(sb.toString());

//		System.out.println(getInitParam());
    }

}