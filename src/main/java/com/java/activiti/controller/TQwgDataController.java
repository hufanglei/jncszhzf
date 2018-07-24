package com.java.activiti.controller;

import com.java.activiti.dao.TQwgDataMapper;
import com.java.activiti.dao.TQwgStatusMapper;
import com.java.activiti.model.*;
import com.java.activiti.service.AttachmentService;
import com.java.activiti.util.*;
import com.java.core.util.ImageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TQwgDataController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TQwgDataMapper tQwgDataMapper;

    @Resource
    private AttachmentService attachmentService;

    @Autowired
    private TQwgStatusMapper tQwgStatusMapper;

    public TQwgStatusMapper gettQwgStatusMapper() {
        return tQwgStatusMapper;
    }

    public void settQwgStatusMapper(TQwgStatusMapper tQwgStatusMapper) {
        this.tQwgStatusMapper = tQwgStatusMapper;
    }

    public TQwgDataMapper getTQwgDataMapper() {
        return tQwgDataMapper;
    }

    public void setTQwgDataMapper(TQwgDataMapper tQwgDataMapper) {
        this.tQwgDataMapper = tQwgDataMapper;
    }


    /**
     * 同步全网格数据入库
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/frame/refreshTQwgData")
    public
    @ResponseBody
    Map refreshTQwgData(HttpServletRequest request) throws IOException {

        Map map1 = new HashMap();
        int page = 1;
        int pageSize = 20;
        QwgBodyBean qwgBodyBean = HttpUtil.queryData(page, pageSize);
        if (qwgBodyBean != null) {
            map1.put("success", true);
            map1.put("rowNum", qwgBodyBean.getRecords());
        } else {
            map1.put("success", false);
            map1.put("rowNum", 0);

            return map1;
        }

        int totalPage = qwgBodyBean.getTotal();
        int records = qwgBodyBean.getRecords();
        Map map = new HashMap();
        List<TQwgDataBean> list = null;
        List<TQwgStatusBean> list2 = null;
        while (page <= totalPage) {
            //获取接口过来的每一条数据
            List<QwgRowBean> qwgRowBeans = qwgBodyBean.getRows();
            //遍历shuju

            for (QwgRowBean qwgRowBean : qwgRowBeans) {
                map.put("orderNo", qwgRowBean.getOrderNo());
                //查询数据库中数据
                list = tQwgDataMapper.selectTQwgDataByOrderNo(map);
                list2 = tQwgStatusMapper.selectTQwgStatusByOrderNo(map);
                if (list.size()>0) {
                    for (TQwgDataBean tQwgDataBean1 : list) {
                        tQwgDataBean1.setCompletedata(qwgRowBean.getCompleteData());
                        tQwgDataBean1.setContent(qwgRowBean.getContent());

                        List<FileBean> attatches = qwgRowBean.getFlowAttachFileDubbos();
                        List<String> localFileNames = new ArrayList<>();
                        if (attatches != null && attatches.size() > 0) {
                            for (FileBean fileMap : attatches) {
                                String fileRemoteURL = String.valueOf(fileMap.getPhysicsFullFileName());
                                if (StringUtils.isNotBlank(fileRemoteURL)) {
                                    String destFileName = System.currentTimeMillis() + fileRemoteURL.substring(fileRemoteURL.indexOf("."), fileRemoteURL.length());
                                    ImageUtil.readImageToSaveLocal(SystemProperties.getTianque_events_host() + File.separator + fileRemoteURL, getUploadDir(), destFileName);
                                    localFileNames.add(destFileName);
                                }

                            }
                            tQwgDataBean1.setFlowattachfiledubbos(StringUtils.join(localFileNames, ","));
                        }
                        tQwgDataBean1.setIssuetypeid(qwgRowBean.getIssueTypeId());
                        tQwgDataBean1.setOccurdate(qwgRowBean.getOccurDate());
                        tQwgDataBean1.setOccurlocation(qwgRowBean.getOccurLocation());
                        tQwgDataBean1.setOccurorg(qwgRowBean.getOccurOrg());
                        tQwgDataBean1.setOrderno(qwgRowBean.getOrderNo());
                        tQwgDataBean1.setSourceid(qwgRowBean.getSourceId());
                        tQwgDataBean1.setSourcemobile(qwgRowBean.getSourceMobile());
                        tQwgDataBean1.setSourcename(qwgRowBean.getSourceName());
                        tQwgDataBean1.setSourceperson(qwgRowBean.getSourcePerson());
                        tQwgDataBean1.setTitle(qwgRowBean.getTitle());
                        tQwgDataMapper.updateTQwgData( tQwgDataBean1);
                    }
                } else {
                    TQwgDataBean tQwgDataBean = new TQwgDataBean();
                    tQwgDataBean.setCompletedata(qwgRowBean.getCompleteData());
                    tQwgDataBean.setContent(qwgRowBean.getContent());

                    List<FileBean> attatches = qwgRowBean.getFlowAttachFileDubbos();
                    List<String> localFileNames = new ArrayList<>();
                    if (attatches != null && attatches.size() > 0) {
                        for (FileBean fileMap : attatches) {
                            String fileRemoteURL = String.valueOf(fileMap.getPhysicsFullFileName());
                            if (StringUtils.isNotBlank(fileRemoteURL)) {
                                String destFileName = System.currentTimeMillis() + fileRemoteURL.substring(fileRemoteURL.indexOf("."), fileRemoteURL.length());
                                ImageUtil.readImageToSaveLocal(SystemProperties.getTianque_events_host() + File.separator + fileRemoteURL, getUploadDir(), destFileName);
                                localFileNames.add(destFileName);
                            }

                        }
                        tQwgDataBean.setFlowattachfiledubbos(StringUtils.join(localFileNames, ","));
                    }


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

                    tQwgDataMapper.insertTQwgData(tQwgDataBean);
                }

            }
            page++;
            if (page <= totalPage) {
                qwgBodyBean = HttpUtil.queryData(page, pageSize);
                if (qwgBodyBean == null) {
                    map1.put("success", false);
                    map1.put("rowNum", 0);
                    return map1;
                }
            }
            if (list2.size()>0){
                for (QwgRowBean qwgRowBean2 : qwgRowBeans) {
                    Map resMap = HttpUtil.feedback(qwgRowBean2.getOrderNo(),
                            HttpUtil.OPT_RECIEVE);

                    for (TQwgStatusBean tQwgStatusBean : list2) {
                        tQwgStatusBean.setOrderno(qwgRowBean2.getOrderNo());
//                        tQwgStatusBean.setProcessTime(new SimpleDateFormat(
//                                "yyyy-MM-dd hh:mm:ss").format(new Date()));
                        tQwgStatusBean.setStatus("RECIEVE");
                        tQwgStatusBean.setIssuccess(resMap.get("success").toString());

                        tQwgStatusMapper.updateTQwgStatus(tQwgStatusBean);
                    }

                }
            }else{
                for (QwgRowBean qwgRowBean2 : qwgRowBeans) {
                    Map resMap = HttpUtil.feedback(qwgRowBean2.getOrderNo(),
                            HttpUtil.OPT_RECIEVE);

                    TQwgStatusBean tQwgStatusBean = new TQwgStatusBean();
                    tQwgStatusBean.setOrderno(qwgRowBean2.getOrderNo());
                    tQwgStatusBean.setProcessTime(new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss").format(new Date()));
                    tQwgStatusBean.setStatus("RECIEVE");
                    tQwgStatusBean.setIssuccess(resMap.get("success").toString());

                    tQwgStatusMapper.insertTQwgStatus(tQwgStatusBean);
                }
        }


        }





        return map1;
    }


    /***
     * 委办局拒绝全网格案件
     * @param request
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/frame/rejectTQwgData")
    public
    @ResponseBody
    String rejectTQwgData(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String orderNo = request.getParameter("orderNo");
        String comments = request.getParameter("comments");
        String files = request.getParameter("files");
        String tmpFolder = System.getProperty("java.io.tmpdir")+"\\jncszhzf\\";
        Map<String, String> filePathMap = new HashMap<>();
        if (StringUtils.isNotBlank(files)) {
            for (String file : files.split(",")) {
                filePathMap.put(file, tmpFolder + file);
            }
        }
        if (StringUtils.isBlank(comments)) {
            comments = "案件驳回";
        }
        Map resMap = HttpUtil.feedback(orderNo, comments, files, filePathMap, HttpUtil.OPT_REJECT);

        List<TQwgStatusBean> list3 = null;
        Map map=new HashMap();
        map.put("orderNo",orderNo);
        list3 = tQwgStatusMapper.selectTQwgStatusByOrderNo(map);
        if(list3.size()>0){
            for (TQwgStatusBean tQwgStatusBean : list3) {
                tQwgStatusBean.setComments(comments);
                tQwgStatusBean.setOrderno(orderNo);
                tQwgStatusBean.setProcessTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                tQwgStatusBean.setStatus("COMPLETE");
                tQwgStatusBean.setIssuccess(resMap.get("success").toString());

                tQwgStatusMapper.updateTQwgStatus(tQwgStatusBean);
            }
        }


        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(resMap, jsonConfig);
        result.put("result", jsonArray);

        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
      /*  JSONObject jSONObject = JSONObject.fromObject(resMap, Constants.getJsonConfig());
        return jSONObject.toString();*/
    }

    /***
     * 委办局完成全网格案件
     * @param request
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/frame/completeTQwgData")
    public
    @ResponseBody
    String completeTQwgData(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String orderNo = request.getParameter("orderNo");
        String comments = request.getParameter("comments");
        String files = request.getParameter("files");
        List<String> filePath = new ArrayList<>();
        String tmpFolder = System.getProperty("java.io.tmpdir")+"\\jncszhzf\\";

        Map<String, String> fileMap = new HashMap<>();
        if (StringUtils.isNotBlank(files)) {
            for (String file : files.split(",")) {
//                fileMap.put(file, getUploadDir() + file);
                fileMap.put(file, tmpFolder+ file);
            }
        }
        if (StringUtils.isBlank(comments)) {
            comments = "案件办结";
        }
        Map map=new HashMap();
        map.put("orderNo",orderNo);
        List<TQwgStatusBean> list3 = null;
        Map resMap = HttpUtil.feedback(orderNo, comments, files, fileMap, HttpUtil.OPT_COMPLETE);
        if (resMap.get("success") != null && (Boolean) resMap.get("success")) {
             list3 = tQwgStatusMapper.selectTQwgStatusByOrderNo(map);
             if(list3.size()>0){
                 for (TQwgStatusBean tQwgStatusBean : list3) {
                     tQwgStatusBean.setComments(comments);
                     tQwgStatusBean.setOrderno(orderNo);
                     tQwgStatusBean.setProcessTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                     tQwgStatusBean.setStatus("COMPLETE");
                     tQwgStatusBean.setIssuccess(resMap.get("success").toString());

                     tQwgStatusMapper.updateTQwgStatus(tQwgStatusBean);
                 }
             }
        }
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(resMap, jsonConfig);
        result.put("result", jsonArray);

        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    @Transactional
    @RequestMapping(value = "/frame/listTQwgData")
    public
    @ResponseBody
    String listTQwgData(HttpServletRequest request,HttpServletResponse response) throws IOException {

        String rows = request.getParameter("rows");
        String page = request.getParameter("page");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String issueTypeId = request.getParameter("issueTypeId");

        if (rows == null || "".equals(rows)) {
            rows = "10";
        }
        if (page == null || "".equals(page)) {
            page = "1";
        } else {
            int p = Integer.parseInt(page);
            int r = Integer.parseInt(rows);
            int pr = (p - 1) * r + 1;
            page = String.valueOf(pr);
        }
        if (sort == null || "".equals(sort)) {
            sort = "orderno ";
        }
        if (order == null || "".equals(order)) {
            order = " desc";
        }
        String orderString = sort + " " + order;


        Map map1 = new HashMap();
        map1.put("issueTypeId", issueTypeId);
        int count = tQwgDataMapper.selectTQwgDataCountByClause(map1);
        map1.put("begin", Integer.parseInt(page) - 1);
        map1.put("pageSize", Integer.parseInt(rows));
        map1.put("orderString", orderString);
        List<TQwgDataBean> list = tQwgDataMapper.selectTQwgDataByClause(map1);


      /*  Map map2 = new HashMap();
        map2.put("total", count);
        map2.put("rows", list);

        JSONObject jSONObject = JSONObject.fromObject(map2, Constants.getJsonConfig());*/


        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", count);
        try {
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Transactional
    @RequestMapping(value = "/frame/toEditTQwgData")
    public
    @ResponseBody
    String toEditTQwgData(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        TQwgDataBean tQwgDataBean = tQwgDataMapper.selectTQwgDataByPk(Integer.parseInt(id));

        JSONObject jSONObject = JSONObject.fromObject(tQwgDataBean, Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/frame/addTQwgData")
    public
    @ResponseBody
    Map addTQwgData(TQwgDataBean tQwgDataBean, HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            tQwgDataBean.setOrderno(null);
            result = tQwgDataMapper.insertTQwgData(tQwgDataBean);
        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;

    }

    @Transactional
    @RequestMapping(value = "/frame/updateTQwgData")
    public
    @ResponseBody
    Map updateTQwgData(TQwgDataBean tQwgDataBean, HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            result = tQwgDataMapper.updateTQwgData(tQwgDataBean);
        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;

    }

    @Transactional
    @RequestMapping(value = "/frame/deleteTQwgData")
    public
    @ResponseBody
    Map deleteTQwgData(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        int result = 0;
        String errMessage = "";
        try {
            result = tQwgDataMapper.deleteTQwgData(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "/frame/uploadAttachment", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, String examId, Model model, HttpServletRequest request) throws Exception {

        Map<String, Object> res = new HashMap<>();

        //上传文件到临时目录
        String tmpFolder = System.getProperty("java.io.tmpdir")+"\\jncszhzf";

        File folder = new File(tmpFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String orginFileName = file.getOriginalFilename();

        String filename =  UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
        String filePath = tmpFolder + File.separator + filename;
        File saveFile = new File(filePath);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        file.transferTo(saveFile);

        res.put("name", orginFileName);
        res.put("url", filename);
        res.put("size", file.getSize());
        return res;
    }

    private String getUploadDir() {
        String tempPath = this.getClass().getClassLoader().getResource("/").getPath();
        tempPath = tempPath.substring(0, tempPath.indexOf("WEB-INF"));
        String exportDir = tempPath + "tmpUpload" + File.separator;
        return exportDir;
    }

}
