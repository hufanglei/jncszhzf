package com.java.activiti.dao;

import com.java.activiti.model.TMainIssueKhBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract interface TMainIssueKhDao {

    public abstract TMainIssueKhBean selectTMainIssueKhByPk(int id);

    public abstract int insertTMainIssueKh(TMainIssueKhBean tMainIssueKhBean);

    public abstract int updateTMainIssueKh(TMainIssueKhBean tMainIssueKhBean);

    public abstract List<TMainIssueKhBean> selectListPage(Map<String, Object> map);

    public abstract int selectCount(Map map1);

    //计算上个月 自行处理项 (每个街道的得分)
    public abstract List<HashMap<String, Object>> statistictDealSelf();

    //计算上个月 案件比重(每个街道的得分)
    public abstract List<HashMap<String, Object>> statistictStreetRatio();

    //计算上个月 按时处理(每个街道的得分)
    public abstract List<HashMap<String, Object>> statistictDealOnTime();

    //计算上个月 快速处理（每个街道的得分）
    public abstract List<HashMap<String, Object>> statistictDealQuick();


    void updateTMainIssueXcbd(TMainIssueKhBean tMainIssueKhBean);


    TMainIssueKhBean selectXcbd(Map map1);

    List<HashMap<String,Object>> selectDealSelf(Map map);

    List<HashMap<String,Object>> onTimeList(Map map);

    List<HashMap<String,Object>> quickList(Map map);

    List<HashMap<String,Object>> onTimeListWbj(Map map);

    List<HashMap<String,Object>> quickListWbj(Map map);

    List<HashMap<String,Object>> selectDcdbList(Map map);

    List<HashMap<String,Object>> zfhjList(Map map);

    List<HashMap<String,Object>> zfhjWbjList(Map map);

    List<HashMap<String,Object>> selectDcdbWbjList(Map map);

    /**
     * 年度统计
     * @param map1
     * @return
     */
    List<TMainIssueKhBean> selectZhzf(Map map1);

    /**
     * 年度统计
     * @param map1
     * @return
     */
    int selectCountZhzf(Map map1);

    /**
     * 季度统计
     * @param map1
     * @return
     */
    List<TMainIssueKhBean> zhzfJdPage(Map map1);

    /**
     * 季度统计
     * @param map1
     * @return
     */
    int selectCountjd(Map map1);

    List<HashMap<String,Object>> selectFftsList(Map map1);

    List<HashMap<String,Object>> selectFftsWbjList(Map map1);

    List<HashMap<String,Object>> selectDbwzList(Map map1);

    List<HashMap<String,Object>> selectDbwzWbjList(Map map1);

    List<HashMap<String,Object>> selectGzxtList(Map map1);

    List<HashMap<String,Object>> selectGzxtWbjList(Map map1);

    List<HashMap<String,Object>> selectJdjcListt(Map map1);

    List<HashMap<String,Object>> selectJdjcWbjList(Map map1);

    List<HashMap<String,Object>> selectXcjzList(Map map1);
}
