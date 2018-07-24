package com.java.activiti.service;

import com.java.activiti.model.PowerListBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
@Service
public interface PowerListService {
    /**
     * 数据字典表数据修改保存
     * @param powerListBean
     * @return
     */
     int updatePowerList(PowerListBean powerListBean);

    /**
     * 数据字典表数据新增保存
     * @param powerListBean
     * @return
     */
     int insertPowerList(PowerListBean powerListBean);

    /**
     * 数据字典表数据根据id删除
     * @param id
     * @return
     */
     int deletePower(String id);

    /**
     * 数据字典表数据 查询所属部门 下拉框
     * @return
     */
    List<PowerListBean> selectSsWeibanju();

    /**
     * 查询数据字典表数据 分页展示
     * @param page
     * @param rows
     * @param ssWeibanju
     * @return
     */
    List<PowerListBean> selectPowerByClause(String page,String rows,String ssWeibanju);
}
