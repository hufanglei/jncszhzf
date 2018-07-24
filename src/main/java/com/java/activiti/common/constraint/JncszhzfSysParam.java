package com.java.activiti.common.constraint;

/**
 * Created by Administrator on 2017/12/15.
 */
public class JncszhzfSysParam {
    public final static Integer TOTAL_STATUS = -1;

    //系统字典代码名称
    public static class SysZdCodeName{
        //状态
        public final static String TASK_STATUS = "statuscode";
        //案件来源
        public final static String CASE_SOURCE = "ajly";
        //当事人类型
        public final static String PERSON_PARTY = "dsrlx";
        //处置时限
        public final static String HANDLE_TIME = "czsx";
        //紧急程度
        public final static String EMERGENCY = "jjcd";
        //是否
        public final static String IF = "sf";
        //性别
        public final static String GENDER = "xb";
        //角色分类
        public final static String ROLE_NAME = "rolename";
        //涉嫌违规类型
        public final static String OUT_RULES = "violationtype";
        //用户组
        public final static String GROUP_NAME = "groupname";

    }
    //系统字典表排序字段名
    public static class SortFiled{
        //代码编号
        public final static String CODE_NUMBER = "dmbh";
        //代码顺序
        public final static String CODE_ORDER = "dmorder";
    }
    //角色类别
    public static class RoleType {
        //系统管理员
        public final static Integer SYS_ADMIN = 0;
        //网格员
        public final static Integer GRIDMAN = 1;
        //街道中心
        public final static Integer STREET_CENTER = 2;
        //区级中心
        public final static Integer AREA_CENTER = 3;
        //区级领导
        public final static Integer AREA_LEADER = 4;
        //委办局
        public final static Integer BUREAUS = 5;
        //街道科室
        public final static Integer STREET_DEPART = 6;
        //区级中心督察组
        public final static Integer AREA_CENTER_INSPECTOR_GROUP = 7;
        //执法中队
        public final static Integer LAW_SQUADRON = 8;
        //考核小组
        public final static Integer ASSESSMENT_TEAM = 9;
    }

    //角色类别
    public static class GroupType {
        //系统管理员
        public final static Integer SYS_ADMIN = 1;
        //街道中心
        public final static Integer STREET_CENTER = 2;
        //区级中心
        public final static Integer AREA_CENTER = 3;
        //区级领导
        public final static Integer AREA_LEADER = 4;
        //区级中心督察组
        public final static Integer AREA_CENTER_INSPECTOR_GROUP = 5;
        //委办局
        public final static Integer BUREAUS = 6;
    }
    //待办任务状态
    public static class PendingTaskStatus{
        //已暂存
        public static Integer SAVE_TEMP = 1;
        //自处置
        public static Integer HANDLE_SELF = 2;
        //待街道派发
        public static Integer WAIT_SUBDISTRICT_DISTRIBUTE = 3;
        //待区级中心派发
        public static Integer WAIT_DISTRICT_CENTER_DISTRIBUTE = 4;
        //待主管部门接收
        public static Integer COMPETENT_DEPARTMENT_ACCEPT = 5;
        //待主管部门立案
        public static Integer COMPETENT_DEPARTMENT_FIFLING = 6;
        //待主管部门处理
        public static Integer COMPETENT_DEPARTMENT_HANDLE = 7;
        //待修改处理时限
        public static Integer WAIT_MOD_TIME_LIMIT = 8;
        //待评价
        public static Integer WAIT_COMMENT = 9;
        //待联办
        public static Integer WAIT_COOPERATION = 10;
        //待疑难案件处理
        public static Integer WAIT_DIFFICULT_CASE_HANDLE = 11;
        //待街道处理
        public static Integer WAIT_SUBDISTRICT_HANDLE = 12;
        //待街道线下督查
        public static Integer WAIT_SUBDISTRICT_HANDLE_OFFLINE = 13;
        //待督察组线下督查
        public static Integer WAIT_DISTRICT_CENTER_HANDLE_OFFLINE = 14;
        //街道派遣待处理
        public static Integer WAIT_SUBDISTRICT_DISTRUBUTE_CASE_HANDLE = 15;
        //结束
        public static Integer FINISH = 16;
    }

    public static class LimitHandleTime{
        //一天
        public static Integer ONE = 1;
        //两天
        public static Integer TWO = 2;
        //三天
        public static Integer THREE = 3;
        //五天
        public static Integer FIVE = 5;
        //七天
        public static Integer SEVEN = 7;
        //十天
        public static Integer TEN = 10;
        //十五天
        public static Integer FIFTEEN = 15;
        //二十天
        public static Integer TWENTY = 20;
        //二十五天
        public static Integer TWENTY_FIVE = 25;
        //三十天
        public static Integer THIRTY = 30;
    }
}
