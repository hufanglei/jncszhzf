package com.java.activiti.common.controller;

import com.java.activiti.common.dto.BaseDTO;
import com.java.activiti.common.dto.MsgResultDTO;
import com.java.activiti.common.dto.FieldErrorDTO;
import com.java.activiti.common.enums.SysRespEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */
public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 按照系统错误代码生成系统错误
     *
     * @param sysRespEnum
     * @return
     */
    public static ResponseEntity<BaseDTO> buildErrorResp(SysRespEnum sysRespEnum) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRespCode(sysRespEnum.getCode());
        baseDTO.setRespMsg(sysRespEnum.getMessage());
        ResponseEntity<BaseDTO> responseEntity = new ResponseEntity<>(baseDTO, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 生成自定义异常
     *
     * @param sysRespEnum
     * @param errorMsg
     * @return
     */
    public static ResponseEntity<BaseDTO> buildErrorResp(SysRespEnum sysRespEnum, String errorMsg) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRespCode(sysRespEnum.getCode());
        baseDTO.setRespMsg(sysRespEnum.getMessage());
        baseDTO.setResult(new MsgResultDTO(errorMsg));
        ResponseEntity<BaseDTO> responseEntity = new ResponseEntity<>(baseDTO, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 参数校验错误返回数据构建
     *
     * @param sysRespEnum
     * @param bindingResult
     * @return
     */
    public static ResponseEntity<BaseDTO> buildErrorResp(SysRespEnum sysRespEnum, BindingResult bindingResult) {
        List<FieldErrorDTO> errorList = new ArrayList<>();
        List<String> fieldList= new ArrayList<>();//唯一标志
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for(FieldError fieldError:fieldErrorList){
            if(!fieldList.contains(fieldError.getField())){
                fieldList.add(fieldError.getField());
                FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage());
                errorList.add(fieldErrorDTO);
            }
        }

        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRespCode(sysRespEnum.getCode());
        baseDTO.setRespMsg(sysRespEnum.getMessage());
        baseDTO.setResult(errorList);

        ResponseEntity<BaseDTO> responseEntity = new ResponseEntity<>(baseDTO, HttpStatus.OK);
        return responseEntity;
    }


    /**
     * 构建成功数据
     *
     * @param httpHeaders
     * @param object
     * @return
     */
    public static ResponseEntity<BaseDTO> buildSuccessResp(HttpHeaders httpHeaders, Object object) {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRespCode(SysRespEnum.SUCCESS.getCode());
        baseDTO.setRespMsg(SysRespEnum.SUCCESS.getMessage());
        baseDTO.setResult(object);
        ResponseEntity<BaseDTO> responseEntity = new ResponseEntity<>(baseDTO, httpHeaders, HttpStatus.OK);
        return responseEntity;
    }


}
