package com.java.activiti.dao;
import com.java.activiti.model.TMainIssueKhBean;
import com.java.activiti.model.TissueRadioKhBean;
import java.util.List;
import java.util.Map;

public interface TissueRadioKhMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TissueRadioKhBean record);

    int insertSelective(TissueRadioKhBean record);

    TissueRadioKhBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TissueRadioKhBean record);

    int updateByPrimaryKey(TissueRadioKhBean record);

    public abstract List<TissueRadioKhBean> selectListPage(Map<String, Object> map);

    public abstract int selectCount(Map map1);

    List<TissueRadioKhBean> selectByKhyf(Map khyf);

    int updateByKhyf(TissueRadioKhBean tissueRadioKhBean);

    TissueRadioKhBean selectTissue(Map map);

    /**
     * 更新案件比重数据
     * @param tissueRadioKhBean
     * @return
     */
    int updateByQhzdId(TissueRadioKhBean tissueRadioKhBean);

    /**
     * 查询数据案件比重自处置案件数量
     * @param map
     * @return
     */
    double selectJdCountBykhyf(Map map);

    /**
     * 查询年度总共单量avg12345Num 总数据
     * @param map
     * @return
     */

    double  selectSumByKhyf(Map map);

    /**
     * 查询avg12345num 为空,的前一个月的avg2345num 数据
     * @param map3
     * @return
     */
    List<TissueRadioKhBean> selectPageList(Map map3);
}