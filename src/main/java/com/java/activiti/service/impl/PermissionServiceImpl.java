package com.java.activiti.service.impl;

import com.java.activiti.common.dto.BootstrapTableDTO;
import com.java.activiti.common.param.SortParam;
import com.java.activiti.controller.sysmanage.param.PermissionsTableParam;
import com.java.activiti.dao.PermissionMapper;
import com.java.activiti.model.PermissionBean;
import com.java.activiti.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Integer> selectRolesPermissions(String roleId) {
        Map map = new HashMap();
        map.put("groupTag", roleId);
        return permissionMapper.selectPermissionIdsByGroupTag(map);
    }

    @Override
    public List<PermissionBean> selectPermissionByClause(Map map) {
        return permissionMapper.selectPermissionByClause(map);
    }

    @Override
    public BootstrapTableDTO selectPermissionsTable(PermissionsTableParam permissionsTableParam) {
        Map map = new HashMap();
        map.put("parentId",permissionsTableParam.getParentId());
        int count = permissionMapper.selectPermissionCountByClause(map);

        map.put("begin", permissionsTableParam.getPaginationParam().getPage()*permissionsTableParam.getPaginationParam().getPageSize());
        map.put("pageSize", permissionsTableParam.getPaginationParam().getPageSize());

        if (permissionsTableParam.getSortParam() == null) {
            permissionsTableParam.setSortParam(new SortParam(" fatherid,order_ ", " " + SortParam.ASC + " "));
        } else {
            if (StringUtils.isBlank(permissionsTableParam.getSortParam().getSort())) {
                permissionsTableParam.getSortParam().setSort(" fatherid,order_ ");
            }
            if (StringUtils.isBlank(permissionsTableParam.getSortParam().getOrder())) {
                permissionsTableParam.getSortParam().setOrder(" " +SortParam.ASC + " ");
            }
        }
        map.put("orderString", permissionsTableParam.getSortParam().getSort().concat(permissionsTableParam.getSortParam().getOrder()));
        List<PermissionBean> list = permissionMapper.selectPermissionByClause(map);
        return new BootstrapTableDTO(count,list);
    }

    @Override
    public List<PermissionBean> selectPermissionByGroupTags(String groupTag) {
        Map map = new HashMap();
        map.put("groupType", groupTag);
        List<PermissionBean> permissionBeans = permissionMapper.selectPermissionByGroupTags(map);
        return permissionBeans;
    }
}
