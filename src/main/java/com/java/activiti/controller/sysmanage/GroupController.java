package com.java.activiti.controller.sysmanage;

import com.java.activiti.common.controller.BaseController;
import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.dto.BaseOperDTO;
import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.common.enums.SysRespEnum;
import com.java.activiti.controller.sysmanage.dto.GroupTreeDTO;
import com.java.activiti.controller.sysmanage.param.GroupExistParam;
import com.java.activiti.controller.sysmanage.param.GroupsTableParam;
import com.java.activiti.model.*;
import com.java.activiti.service.GroupService;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoipang on 2017/12/17.
 */
@Controller
public class GroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Resource
    GroupService groupService;

    /**
     * 获取用户组自定义导航菜单
     *
     * @return
     */
    @RequestMapping(value = "/groups2tree", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> tree(@RequestParam("usrId") String userId) {
        try {
            List<GroupTreeDTO> list = groupService.selectGroup2Tree(userId);
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 归档查询组织机构树
     *
     * @return
     */
    @RequestMapping(value = "/archiveOfGroup", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> archiveOfGroup(@RequestParam("usrId") String userId) {
        try {
            List<GroupTreeDTO> list = groupService.archiveOfGroup(userId);
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @RequestMapping(value = "/groups")
    public ResponseEntity<BaseDTO> list() {
        try {
            List<Group> list = groupService.findGroup();
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }


    /**
     * 分页查询用户组
     * @param groupsTableParam 查询参数
     * @return
     */
    @RequestMapping(value = "/groups/table", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> bootstrapTable(@RequestBody GroupsTableParam groupsTableParam) {
        try {
            BootstrapTableDTO bootstrapTableDTO = groupService.selectGroupsTable(groupsTableParam);
            return buildSuccessResp(new HttpHeaders(), bootstrapTableDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 根据字典表中用户组编号查询一级分组
     *
     * @param groupTypeId 用户组类型
     * @return
     */
    @RequestMapping(value = "/groups/{id}/nodes", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> firstLevel(@PathVariable("id") String groupTypeId) {
        try {
            List<Group> list = groupService.selectGroupsFirstLevel(groupTypeId);
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 检查用户组是否存在
     *
     * @param groupExistParam 检查条件
     * @return
     */
    @RequestMapping(value = "/group/exists", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> groupExist(@RequestBody GroupExistParam groupExistParam) {
        try {
            BaseOperDTO baseOperDTO = groupService.selectGroupExists(groupExistParam);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 新增用户组
     *
     * @param group
     * @return
     */
    @RequestMapping(value = "/group/save", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> save(Group group) {
        try {
            BaseOperDTO baseOperDTO = groupService.addGroup(group);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 更新用户组
     *
     * @param group
     * @return
     */
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> update(Group group) {
        try {
            BaseOperDTO baseOperDTO = groupService.updateGroup(group);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    /**
     * 删除用户组
     *
     * @param groupId 用户组ID
     * @return
     */
    @RequestMapping(value = "/groups/{id}/deletion", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> delete(@PathVariable("id") String groupId) {
        try {
            BaseOperDTO baseOperDTO = groupService.deleteGroup(groupId);
            return buildSuccessResp(new HttpHeaders(), baseOperDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @RequestMapping("/group/listAllGroups")
    public String listAllGroups(HttpServletResponse response) throws Exception {
        List<Group> list = groupService.findGroup();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list);
        result.put("groupList", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/group/findGroupByUserId")
    public String findGroupByUserId(HttpServletResponse response, String userId) throws Exception {
        List<Group> groupList = groupService.findByUserId(userId);
//			StringBuffer groups=new StringBuffer();
//			for(Group g:groupList){
//				groups.append(g.getGroupId()+",");
//			}
        JSONObject result = new JSONObject();
        if (groupList.size() > 0) {
//				result.put("groups", groups.deleteCharAt(groups.length()-1).toString());
            result.put("rows", groupList);
        } else {
//				result.put("groups",groups.toString());
            result.put("rows", null);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/group/findGroupByType")
    public String findGroupByType(HttpServletResponse response, String type, HttpSession session, HttpServletRequest request) throws Exception {
        // List<Group> groupList = groupService.findGroupByType(typeId);
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
        List<Group> groupList = groupService.findByType(map1);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(groupList);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }


    @RequestMapping("/group/findGroupByType2")
    public String findGroupByType(HttpServletResponse response, String typeId) throws Exception {
        List<Group> groupList = null;
        //TODO 暂时这样写 以后会处理下
        if("2".equals(typeId)||"4".equals(typeId)||"5".equals(typeId)){
            groupList = groupService.findGroupByType(typeId);
        }else{
            groupList=groupService.findLianbanListByType();
        }
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(groupList);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

}
