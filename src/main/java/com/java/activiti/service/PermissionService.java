package com.java.activiti.service;

import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.controller.sysmanage.param.PermissionsTableParam;
import com.java.activiti.model.PermissionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface PermissionService {

    List<Integer> selectRolesPermissions(String roleId);

    /**
     * 根据条件查询权限
     * @param map
     * @return
     */
    List<PermissionBean> selectPermissionByClause(Map map);

    /**
     * 列表数据
     * @param permissionsTableParam
     * @return
     */
    BootstrapTableDTO selectPermissionsTable(PermissionsTableParam permissionsTableParam);

    List<PermissionBean> selectPermissionByGroupTags(String groupTag);
}
