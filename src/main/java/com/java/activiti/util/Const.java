package com.java.activiti.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义类
 *
 * @author wujj@sugon.com
 *
 */
public class Const {

	/**
	 * 青山区地区编码
	 */
	public static String AREACODE = "150204";

	/**
	 * 事件暂存代码
	 */
	public static int ST_SAVE_TEMP = 1;

	/**
	 * 事件暂存描述
	 */
	public static String ST_SAVE_TEMP_DESC = "已暂存";

	/**
	 * 事件上报代码
	 */
	public static int ST_SUBMIT = 2;

	/**
	 * 事件暂存描述
	 */
	public static String ST_SUBMIT_DESC = "已上报";

	/**
	 * 附件上传文件夹名称
	 */
	public static String UPLOAD_FOLD_NAME = "fileupload";

	/**
	 * 附件上传文件的类型
	 */
	public static Integer UPLOAD_FILE_TYPE = 1;

	/**
	 * 事件分发，有接收单位
	 */
	public static Integer DISTRIBUTE_STATUS_HAS_RECEIVER = 1;

	/**
	 * 事件分发，无接收单位
	 */
	public static Integer DISTRIBUTE_STATUS_NO_RECEIVER = 2;

	/**
	 * 事件分发，问题特办
	 */
	public static Integer DISTRIBUTE_STATUS_SPECIAL_ISSUE = 3;

	/**
	 * 事件分发，问题驳回
	 */
	public static Integer DISTRIBUTE_STATUS_TURN_DOWN = 4;

	/**
	 * 时间分发，问题联办
	 */
	public static Integer DISTRIBUTE_STATUS_COOPERATION = 5;

	/**
	 * 状态代码-已暂存
	 */
	public static String STATUS_SAVE_TEMP = "1";

	/**
	 * 状态码-自处置
	 */
	public static String STATUS_HANDLE_SELF = "2";

	/**
	 * 状态代码-待街道派发
	 */
	public static String STATUS_WAIT_SUBDISTRICT_DISTRIBUTE = "3";

	/**
	 * 状态代码-待区级中心派发
	 */
	public static String STATUS_WAIT_DISTRICT_CENTER_DISTRIBUTE = "4";



	/**
	 * 状态代码-待主管部门立案
	 */
	public static String STATUS_COMPETENT_DEPARTMENT_FIFLING = "5";

	/**
	 * 状态代码-待主管部门派遣
	 */
	public static String STATUS_COMPETENT_DEPARTMENT_PAIQIAN = "6";

	/**
	 * //状态代码-待主管部门(执法中队)处理
	 * 状态代码-待执法中队处理
	 */
	public static String STATUS_COMPETENT_DEPARTMENT_HANDLE = "7";

	/**
	 * 状态代码-待修改处理时限
	 */
	public static String STATUS_WAIT_MOD_TIME_LIMIT = "8";

	/**
	 * 状态代码-待评价
	 */
	public static String STATUS_WAIT_COMMENT = "9";

	/**
	 * 状态码-待联办
	 */
	public static String STATUS_WAIT_COOPERATION = "10";

	/**
	 * 状态码-待疑难案件处理
	 */
	public static String STATUS_WAIT_DIFFICULT_CASE_HANDLE = "11";

	/**
	 * 状态码-待街道处理
	 */
	public static String STATUS_WAIT_SUBDISTRICT_HANDLE = "12";

	/**
	 * 状态码-待街道线下督查
	 */
	public static String STATUS_WAIT_SUBDISTRICT_HANDLE_OFFLINE = "13";

	/**
	 * 状态码-待督察组线下督查
	 */
	public static String STATUS_WAIT_DISTRICT_CENTER_HANDLE_OFFLINE = "14";

	/**
	 * 状态代码-街道派遣待处理
	 */
	public static String STATUS_WAIT_SUBDISTRICT_DISTRUBUTE_CASE_HANDLE = "15";

	/**
	 * 状态码-结束
	 */
	public static String STATUS_FINISH = "16";
	/**
	 * 状态码-已归档
	 */
	public static String STATUS_YWJ = "17";
	/**
	 * 状态码-待督办处理
	 */
	public static String STATUS_DDB = "18";


	/**
	 * 已修改处理时限
	 */
	public static String HAS_MOD_TIME_LIMIT = "1";

	/**
	 * 评价满意
	 */
	public static int EVALUATION_STATUS_SATISFIED = 1;

	/**
	 * 评价不满意
	 */
	public static int EVALUATION_STATUS_UNSATISFIED = 2;

	/**
	 * 灰色灯
	 */
	public static final int LIGHT_GRAY_STATUS = 0;

	/**
	 * 红色灯
	 */
	public static final int LIGHT_RED_STATUS = 1;

	/**
	 * 绿色灯
	 */
	public static final int LIGHT_GREEN_STATUS = 2;

	/**
	 * 蓝色灯
	 */
	public static final int LIGHT_BLUE_STATUS = 3;

	/**
	 * 分发处理时限
	 */
	public static final int DISTRIBUTE_TIME_LIMIT = 1;

	/**
	 * 问题接收处理时限
	 */
	public static final int RECEIVE_TIME_LIMIT = 1;

	/**
	 * 短信息类型——任务到达提醒
	 */
	public static final int SMS_TYPEID_NEW_TASK = 1;

	/**
	 * 短信息类型——咨询类回复
	 */
	public static final int SMS_TYPEID_CONSULT_REPLOY = 2;

	//**********************案件上报方式***********************************/

	/**
	 * 上报人类型: 网格员上报
	 */
	public static  final String WANGGE_REPORT = "1";
	/**
	 * 上报人类型: 区中心上报
	 */
	public static  final String DISTINCT_REPORT = "3";

	//**********************案件处理方式***********************************/
	//  `deal_way` varchar(2) DEFAULT NULL COMMENT '处理方法 1:自处置 2:街道(科室)处理 3:街道线下督办 4:主管部门处理 5:区级中心线下督察 6:联办处理',
	public static  final String DEAL_WAY_ZCL = "1";
	public static  final String DEAL_WAY_JDKECL = "2";
	public static  final String DEAL_WAY_XXCL = "3";
	public static  final String DEAL_WAY_WBJCL = "4";
	public static  final String DEAL_WAY_QJXXCL = "5";
	public static  final String DEAL_WAY_LBCL = "6";

	//**************案件是否经过区级中心***********************************/
	// `is_pass_districtcenter` varchar(2) DEFAULT '0' COMMENT '是否经过区级中心 1：是 ',
	public static  final String J_QJ_YES = "1";
	public static  final String J_QJ_NO = "0";


    //*******************督办的违规类型*******************************************************//
	public static final String BD_TYPE_GZXT = "70"; //工作协调
	public static final String BD_TYPE_SLJB = "71"; //受理交办
	public static final String BD_TYPE_FFTS = "69"; //反复投诉

	//************督办案件处理状态***********************//
	public static final String DB_DISTRICT_DISTRIBUTE = "1"; //待区中心派发
	public static final String DB_EXAMINE_GROUP_CHECK = "2"; //待考核组审核
	public static final String DB_EXAMINE_GROUP_DISTRIBUTE   = "3"; //待考核组派发
	public static final String DB_DEPT_DISPATCH = "4"; //待主管部门派遣
	public static final String DB_LOCHUS_DEAL = "5"; //待执法中队处理
	public static final String DB_STREET_DISTRIBUTE = "6"; //待街道派发
	public static final String DB_STREETDEPART_DEAL = "7"; //待街道科室处理
	public static final String DB_EXAMINE_GROUP_EVALUATE = "8"; //待考核组评价
	public static final String DB_FINISH = "9"; //已归档


	//**************考核小组的组id************************//
	public static final String KHXZ_GROUP_ID = "khxz";

	//****************区中心组id*****************************//
	public static final String QUZHONGXIN_GROUP_ID = "districtCenter";
	/**
	 * 普通案件 附件上传类型
	 */
	public static final String PT_FJTYPE_ONE="1"; //上报时附件 处理前
	public static final String PT_FJTYPE_TWO="2"; //处理时附件
	public static final String PT_FJTYPE_THRE="3"; //评价时附件
	public static final String PT_FJTYPE_FOUR="4"; //立案时附件
	public static final String PT_FJTYPE_FIVE="5"; //网格员  街道科室  执法中队处理时时附件

	/**
	 * 案件处理过程中时间限制
	 */
	public static final int TIME_LIMIT_TWO=2;
	public static final int TIME_LIMIT_FIVE=5;
	public static final int TIME_LIMIT_SEVEN=7;


}
