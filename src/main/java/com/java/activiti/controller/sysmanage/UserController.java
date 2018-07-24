package com.java.activiti.controller.sysmanage;

import com.java.activiti.common.constraint.SysMsg;
import com.java.activiti.common.controller.BaseController;
import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.common.enums.SysRespEnum;
import com.java.activiti.controller.sysmanage.param.UsersTableParam;
import com.java.activiti.dao.PermissionMapper;
import com.java.activiti.model.*;
import com.java.activiti.service.*;
import com.java.activiti.util.Constants;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Administrator
 */
@Controller
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private MemberShipService menberShipService;
    @Resource
    private GroupService groupService;

    @Resource
    PermissionMapper permissionMapper;

    private HashMap<String, Integer> issueTypeIdMap = new HashMap<String, Integer>() {
        {
            put("bureau_ZF", 10001);
            put("bureau_JG", 10002);
            put("bureau_LB", 10003);
            put("bureau_HB", 10004);
            put("bureau_AQ", 10005);
            put("bureau_LY", 10006);
            put("bureau_NY", 10007);
            put("bureau_SW", 10008);
            put("bureau_WH", 10009);
            put("bureau_CG", 10010);
            put("bureau_JT", 10011);
            put("bureau_SC", 10012);
            put("bureau_SHW", 10013);
        }
    };
    @Resource
    PermissionService permissionService;

    /**
     * 分页查询用户数据
     * @param usersTableParam
     * @return
     */
    @RequestMapping(value = "/users/table", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> bootstrapTable(@RequestBody UsersTableParam usersTableParam) {
        try {
            BootstrapTableDTO bootstrapTableDTO = userService.selectUsersTable(usersTableParam);
            return buildSuccessResp(new HttpHeaders(), bootstrapTableDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 检查用户名是否存在
     * @param userId 用户Id
     * @return 布尔值，已经存在返回TRUE，不存在则返回FALSE
     */
    @RequestMapping(value = "/user/exists")
    public ResponseEntity<BaseDTO> userExist(@RequestParam("userId") String userId) {
        try {
            BaseOperDTO baseOperDTO = userService.selectUserExists(userId);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 新增用户
     *
     * @param memberShip 对象关系
     * @return
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> save(MemberShip memberShip) {
        try {
            BaseOperDTO baseOperDTO = userService.addUser(memberShip);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> update(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            try {
                user.setPassword(Constants.getMD5PassWord(user.getPassword()));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(SysMsg.ENCRYPT_ERROR);
            }
        }
        try {
            BaseOperDTO baseOperDTO = userService.updateUser(user);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户Id
     * @return
     */
    @RequestMapping(value = "/users/{id}/deletion", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> delete(@PathVariable("id") String userId) {
        try {
            BaseOperDTO baseOperDTO = userService.deleteUser(userId);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 重置密码
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value = "/users/{id}/resetPwd", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> resetPwd(@PathVariable("id") String userId) {
        try {
            BaseOperDTO baseOperDTO = userService.resetPwd(userId);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }



    /**
     * 登入
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/user/userLogin")
    public String userLogin(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", request.getParameter("userName"));
        String passWord = request.getParameter("password");

        map.put("password", Constants.getMD5PassWord(passWord));
        MemberShip memberShip = menberShipService.userLogin(map);
        //MemberShip memberShip = menberShipService.login(map);
        JSONObject result = new JSONObject();
        if (memberShip == null) {
            result.put("success", false);
            result.put("errorInfo", "用户名或者密码错误！");
        } else {
            result.put("success", true);
            result.put("userId", memberShip.getUserId());
            request.getSession().setAttribute("currentMemberShip", memberShip);

            Map permissionClasue = new HashMap();
            permissionClasue.put("groupType", memberShip.getGroup().getGroupTag());
            List<PermissionBean> permissionBeans = permissionMapper.selectPermissionByClause(permissionClasue);
            //List<PermissionBean> permissionBeans = permissionService.selectPermissionByMap(permissionClasue);
            request.getSession().setAttribute("permissionBeans", permissionBeans);

            String groupId = memberShip.getGroup().getGroupId();
            int issueTypeId = 0;
//            Integer roleId = Integer.valueOf(memberShip.getGroup().getGroupTag());
//            if(roleId==6) {
//                ActUserGroup actUserGroup = userGroupService.selectActUserGroupByRoleId(roleId, Integer.valueOf(groupId));
//                if (actUserGroup != null) {
//                    issueTypeId = actUserGroup.getIssueType();
//                }
//            }
            if (issueTypeIdMap.containsKey(groupId)) {
                issueTypeId = issueTypeIdMap.get(groupId);
            }

            request.getSession().setAttribute("issueTypeId", issueTypeId);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/user/userLogout")
    public ModelAndView userLogout(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject result = new JSONObject();
        result.put("success", true);
        request.getSession().setAttribute("currentMemberShip", null);
        //ResponseUtil.write(response, result);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/user/getUserByID")
    public @ResponseBody
    User getUserByID(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userid = request.getParameter("user_id").toString();
        return userService.findById(userid);
    }


    @RequestMapping("/user/selectUserByClause")
    public @ResponseBody
    User selectUserByClause(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map1 = new HashMap();
        String orgNumber = request.getParameter("orgNumber");
        String userTypeID = request.getParameter("userTypeID");

        if (orgNumber != null && orgNumber != "") {
            map1.put("orgNumber", orgNumber);
        }
        if (orgNumber != null && orgNumber != "") {
            map1.put("userTypeID", Integer.parseInt(userTypeID));
        }

        List<User> list = userService.selectUserByClause(map1);
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }


    @RequestMapping("/user/selectUserByMenberShip")
    @ResponseBody
    public List<User> selectUserByMenberShip(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Map map1 = new HashMap();
        //根据会话信息获取当前登录账号
        MemberShip currentMemberShip = (MemberShip) session.getAttribute("currentMemberShip");
        //取出用户
        User currentUser = currentMemberShip.getUser();
        if (currentUser != null) {
            map1.put("userID", currentUser.getId());
        }

        String userTypeID = request.getParameter("userTypeID");
        if (userTypeID != null && userTypeID != "") {
            map1.put("userTypeID", Integer.parseInt(userTypeID));
        }

        return userService.selectUserByMenberShip(map1);
    }

    @RequestMapping("/user/selectUserByMenberShipId")
    @ResponseBody
    public List<User> selectUserByMenberShipId(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        List<User> users= new ArrayList<>();
        String groupId = request.getParameter("groupId");
        if (groupId != null && groupId != "") {
            users = userService.selectUserByMenberShipId(groupId);
        }
        return users;
    }

//    @RequestMapping("/user/listWithGroups")
//    public String listWithGroups(HttpServletResponse response, String rows, String page, User user) throws Exception {
//        PageInfo<User> userPage = new PageInfo<User>();
//        Map<String, Object> userMap = new HashMap<String, Object>();
//        userMap.put("id", user.getId());
//        Integer pageSize = Integer.parseInt(rows);
//        userPage.setPageSize(pageSize);
//
//        // 第几页
//        String pageIndex = page;
//        if (pageIndex == null || pageIndex == "") {
//            pageIndex = "1";
//        }
//        userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
//                * pageSize);
//        // 取得总页数
//        int userCount = userService.userCount(userMap);
//        userPage.setCount(userCount);
//        userMap.put("pageIndex", userPage.getPageIndex());
//        userMap.put("pageSize", userPage.getPageSize());
//
//        List<User> userList = userService.userPage(userMap);
//        for (User users : userList) {
//            StringBuffer buffer = new StringBuffer();
//            List<Group> groupList = groupService.findByUserId(users.getId());
//            for (Group g : groupList) {
//                buffer.append(g.getGroupName() + ",");
//            }
//            if (buffer.length() > 0) {
//                //deleteCharAt 删除最后一个元素
//                users.setGroups(buffer.deleteCharAt(buffer.length() - 1).toString());
//            } else {
//                user.setGroups(buffer.toString());
//            }
//        }
//        JSONArray jsonArray = JSONArray.fromObject(userList);
//        JSONObject result = new JSONObject();
//        result.put("rows", jsonArray);
//        result.put("total", userCount);
//        ResponseUtil.write(response, result);
//        return null;
//    }

    @RequestMapping("/user/modifyPassword")
    public String modifyPassword(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //获取参数
        String userId = request.getParameter("userId");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPassword2 = request.getParameter("newPassword2");
        JSONObject json = new JSONObject();
        if (userId == "" || userId == null
                || oldPassword == "" || oldPassword == null
                || newPassword == "" || newPassword == null
                || newPassword2 == "" || newPassword2 == null) {
            json.put("success", false);
            json.put("message", "所有输入项不允许为空");
            ResponseUtil.write(response, json);
            return null;
        }

        if (!newPassword.equals(newPassword2)) {
            //新密码两次不一样
            json.put("success", false);
            json.put("message", "确认密码与新密码输入不一致");
            ResponseUtil.write(response, json);
            return null;
        }

        //验证用户与原密码是否正确
        User user = userService.findById(userId);
        if (user.getPassword() == null || !user.getPassword().equals(Constants.getMD5PassWord(oldPassword))) {
            json.put("success", false);
            json.put("message", "原密码输入错误");
            ResponseUtil.write(response, json);
            return null;
        }

        user.setPassword(Constants.getMD5PassWord(newPassword));
        userService.updateUser(user);
        json.put("success", true);
        ResponseUtil.write(response, json);
        return null;
    }


    @RequestMapping("/user/userResetPassword")
    public String userResetPassword(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //获取参数
        String userId = request.getParameter("userId");
        String resetPassword = request.getParameter("resetPassword");
        String reResetPassword = request.getParameter("reResetPassword");
        JSONObject json = new JSONObject();
        if (userId == "" || userId == null
                || resetPassword == "" || resetPassword == null
                || reResetPassword == "" || reResetPassword == null) {
            json.put("success", false);
            json.put("message", "所有输入项不允许为空");
            ResponseUtil.write(response, json);
            return null;
        }

        if (!resetPassword.equals(reResetPassword)) {
            //新密码两次不一样
            json.put("success", false);
            json.put("message", "确认密码与新密码输入不一致");
            ResponseUtil.write(response, json);
            return null;
        }

        User user = new User();
        user.setId(userId);
        user.setPassword(Constants.getMD5PassWord(resetPassword));
        userService.updateUser(user);
        json.put("success", true);
        ResponseUtil.write(response, json);
        return null;
    }
}
