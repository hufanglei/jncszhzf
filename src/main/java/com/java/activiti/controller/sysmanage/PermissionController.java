package com.java.activiti.controller.sysmanage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.java.activiti.common.controller.BaseController;
import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.common.enums.SysRespEnum;
import com.java.activiti.controller.sysmanage.param.PermissionsTableParam;
import com.java.activiti.model.*;
import com.java.activiti.service.PermissionService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.java.activiti.dao.GroupPermissionMapper;
import com.java.activiti.dao.PermissionMapper;
import com.java.activiti.dao.UserGroupMapper;
import com.java.activiti.util.Constants;


@Controller
public class PermissionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private GroupPermissionMapper groupPermissionMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Resource
    PermissionService permissionService;

    @RequestMapping(value = "/permissions")
    public ResponseEntity<BaseDTO> list() {
        try {
            List<PermissionBean> list = permissionService.selectPermissionByClause(new HashMap());
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @RequestMapping(value = "/permissions/table", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> bootstrapTable(@RequestBody PermissionsTableParam permissionsTableParam) {
        try {
            BootstrapTableDTO bootstrapTableDTO = permissionService.selectPermissionsTable(permissionsTableParam);
            return buildSuccessResp(new HttpHeaders(), bootstrapTableDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @RequestMapping(value = "/permission/getpermission_by_grouptag")
    @ResponseBody
    public List getpermission_by_user_id(HttpServletRequest request) throws IOException {
        String groupTag = request.getParameter("groupTag");
        return permissionService.selectPermissionByGroupTags(groupTag);
    }

    @Transactional
    @RequestMapping(value = "/permission/setuser_group")
    @ResponseBody
    public Map setuser_group(HttpServletRequest request) throws IOException {
        String user_id = request.getParameter("user_id");
        String group_ids = request.getParameter("group_ids");
        String result = "success";
        String errMessage = "";

        try {
            if (StringUtils.isBlank(group_ids)) {
                userGroupMapper.deleteUserGroupByUserID(user_id);
            } else {
                userGroupMapper.deleteUserGroupByUserID(user_id);
                String[] pars = group_ids.split(",");
                for (String group_id : pars) {
                    UserGroupBean ug = new UserGroupBean();
                    ug.setUserId(user_id);
                    ug.setGroupId(group_id);
                    userGroupMapper.insertUserGroup(ug);
                }
            }
        } catch (Exception e) {
            result = "fail";
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }

        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;
    }


    @Transactional
    @RequestMapping(value = "/permission/setgroup_permission")
    @ResponseBody
    public Map setgroup_permission(HttpServletRequest request) throws IOException,
            ParseException {
        String group_type = request.getParameter("group_tag");
        String permission_ids = request.getParameter("permission_ids");
        String result = "success";
        String errMessage = "";
        try {
            if (StringUtils.isBlank(permission_ids)) { // 如果没有传permission_ids，表示要删掉permission_ids
                groupPermissionMapper
                        .deleteGroupPermissionByGroup_Type(group_type);
            } else {
                groupPermissionMapper
                        .deleteGroupPermissionByGroup_Type(group_type);
                String[] pars = permission_ids.split(",");
                for (String permission_id : pars) {
                    GroupPermissionBean gp = new GroupPermissionBean();
                    gp.setGroupType(group_type);
                    gp.setPermissionId(Integer.parseInt(permission_id));
                    groupPermissionMapper.insertGroupPermission(gp);
                }
            }
        } catch (Exception e) {
            result = "fail";
            e.printStackTrace();
            errMessage = e.getCause().toString();
        }

        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;

    }

    @Transactional
    @RequestMapping(value = "/permission/getPermissionIdsByGroupType")
    @ResponseBody
    public Map getPermissionIdsByGroupType(HttpServletRequest request) throws IOException {
        String groupTag = request.getParameter("groupTag");

        Map map1 = new HashMap();
        map1.put("groupTag", groupTag);
        List<Integer> permissionIds = permissionMapper.selectPermissionIdsByGroupTag(map1);

        for (int i = 0; i < permissionIds.size(); i++) {
            System.out.println(permissionIds.get(i));
        }

        Map map = new HashMap();
        map.put("rows", permissionIds);

        return map;
    }




    @Transactional
    @RequestMapping(value = "/permission/toEditPermission")
    public @ResponseBody
    String toEditPermission(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        PermissionBean permissionBean = permissionMapper
                .selectPermissionByPk(Integer.parseInt(id));

        JSONObject jSONObject = JSONObject.fromObject(permissionBean,
                Constants.getJsonConfig());
        return jSONObject.toString();
    }

    @Transactional
    @RequestMapping(value = "/permission/addPermission")
    public @ResponseBody
    Map addPermission(PermissionBean permissionBean, HttpServletRequest request)
            throws IOException, ParseException {

        String result = "success";
        String errMessage = "";
        try {
            permissionBean.setId(null);
            permissionMapper.insertPermission(permissionBean);
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
            errMessage = e.getCause().toString();
        }
        Map map = new HashMap();
        map.put("result", result);
        map.put("errMessage", errMessage);
        return map;

    }

    @Transactional
    @RequestMapping(value = "/permission/updatePermission")
    public @ResponseBody
    Map updatePermission(PermissionBean permissionBean,
                         HttpServletRequest request) throws IOException, ParseException {

        int result = 0;
        String errMessage = "";
        try {
            result = permissionMapper.updatePermission(permissionBean);
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
    @RequestMapping(value = "/permission/deletePermission")
    public @ResponseBody
    Map deletePermission(HttpServletRequest request) throws IOException {

        String id = request.getParameter("id");
        String result = "success";
        String errMessage = "";
        try {
            permissionMapper.deletePermission(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
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
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

    public GroupPermissionMapper getGroupPermissionMapper() {
        return groupPermissionMapper;
    }

    public void setGroupPermissionMapper(
            GroupPermissionMapper groupPermissionMapper) {
        this.groupPermissionMapper = groupPermissionMapper;
    }

    public UserGroupMapper getUserGroupMapper() {
        return userGroupMapper;
    }

    public void setUserGroupMapper(UserGroupMapper userGroupMapper) {
        this.userGroupMapper = userGroupMapper;
    }

}
