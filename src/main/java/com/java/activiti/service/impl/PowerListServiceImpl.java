package com.java.activiti.service.impl;

import com.java.activiti.dao.PowerListMapper;
import com.java.activiti.model.PowerListBean;
import com.java.activiti.service.PowerListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guoguo
 */
@Service("powerListService")
public class PowerListServiceImpl implements PowerListService {
    @Resource
    private PowerListMapper powerListMapper;

    /**
     * 查询数据字典表数据 分页展示
     *
     * @param page
     * @param rows
     * @param ssWeibanju
     * @return
     */
    @Override
    public List<PowerListBean> selectPowerByClause(String page, String rows, String ssWeibanju) {
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
        Map map = new HashMap<String, Object>();
        map.put("page", Integer.parseInt(page) - 1);
        map.put("pageSize", Integer.parseInt(rows));
        map.put("ssWeibanju", ssWeibanju);
        List<PowerListBean> list = powerListMapper.selectPowerByClause(map);
        return list;
    }

    /**
     * 数据字典表数据 新增保存
     *
     * @param powerListBean
     * @return
     */
    @Override
    public int insertPowerList(PowerListBean powerListBean) {
        int result = powerListMapper.insertPowerList(powerListBean);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 数据字典表数据  更新保存
     *
     * @param powerListBean
     * @return
     */
    @Override
    public int updatePowerList(PowerListBean powerListBean) {
        int result = powerListMapper.updatePowerList(powerListBean);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 数据字典表数据 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public int deletePower(String id) {
        int result = 0;
        result = powerListMapper.deletePower(id);
        if (result > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 数据字典表数据 查询所属部门下拉框
     *
     * @return
     */
    @Override
    public List<PowerListBean> selectSsWeibanju() {
        return powerListMapper.selectSsWeibanju();
    }


}
