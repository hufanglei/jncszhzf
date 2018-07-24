package com.java.activiti.controller.sysmanage;

import com.java.activiti.common.controller.BaseController;
import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.enums.SysRespEnum;
import com.java.activiti.controller.sysmanage.param.SysZdParam;
import com.java.activiti.model.ZdBean;
import com.java.activiti.service.SysZdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
@Controller
public class SysZdController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysZdController.class);
    @Resource
    SysZdService sysZdService;

    /**
     * 根据条件查询字典表相关数据
     *
     * @return
     */
    @RequestMapping(value = "/zd/list", method = RequestMethod.POST)
    public ResponseEntity<BaseDTO> list(@RequestBody SysZdParam sysZdParam) {
        try {
            List<ZdBean> list = sysZdService.selectSysZdList(sysZdParam);
            return buildSuccessResp(new HttpHeaders(), list);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return buildErrorResp(SysRespEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }
}
