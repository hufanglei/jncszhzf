package com.java.activiti.controller.sysmanage;

import com.java.activiti.common.controller.BaseController;
import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.enums.SysRespEnum;
import com.java.activiti.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hoipang on 2017/12/17.
 */
@Controller
public class RoleController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Resource
    PermissionService permissionService;

    @RequestMapping(value = "/roles/{roleId}/permissions", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> rolesPermissions(@PathVariable("roleId") String roleId) {
        try {
            List<Integer> permissionIds = permissionService.selectRolesPermissions(roleId);
            return buildSuccessResp(new HttpHeaders(), permissionIds);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }
}
