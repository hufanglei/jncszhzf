package com.java.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.activiti.model.TraceBean;


/**
 * 运动轨迹抽析
 * @author PeiXQ
 *
 */
public class TraceUtils {
	
	private static double [][]gpsList1 = null;
	
//	public static void main(String[] args) {
//		gpsList1 =new double[3][2];
//		gpsList1 [0][0] = 2;
//		System.out.println(gpsList1[0][0]);
//	}

	public double[][] parsePoints(List<TraceBean> traceList){
		return parseTracePointArrList(traceList);
	}
	
	private static double[][] parseTracePointArrList(List<TraceBean> traceList){
		if(traceList == null || traceList.size()==0){
			return null;
		}
		
		gpsList1 = new double[traceList.size()][2];
		
		for (int i = 0; i < traceList.size(); i++) {
			gpsList1[i][0]=traceList.get(i).getX();
			gpsList1[i][1]=traceList.get(i).getY();
		}
		changeGpsArray(gpsList1);
		
		return gpsList1;
	}
	
	
//	{
//	        {30.281380271108,119.99880131858},
//	        {30.281380271108,119.99880131858},
//	        {30.281659447529,119.99892119275},
//	        {30.281996527967,119.99890999305},
//	        {30.282136999942,119.99886798094},
//	        {30.282173951597,119.99902691455},
//	        {30.282278574624,119.99897814188},
//	        {30.282373094605,119.99899571255},
//	        {30.282192120982,119.99898845834},
//	        {30.282002693177,119.99920460195},
//	        {30.28186695871 ,119.99958701625},
//	        {30.281795843482,119.99970348907},
//	        {30.281852851378,119.99926638709},
//	        {30.281928171926,119.99906083642},
//	        {30.281967041169,119.99895847045},
//	        {30.28207587813 ,119.99874480092},
//	        {30.282126417837,119.99868359364},
//	        {30.282255719736,119.99860016078},
//	        {30.282276266146,119.9985552095 },
//	        {30.282305338968,119.99863371877},
//	        {30.282351030605,119.99872278381},
//	        {30.28238915389 ,119.99881645602},
//	        {30.28238245455 ,119.99875391145},
//	        {30.282391019124,119.99877502829},
//	        {30.282270805856,119.99884194562},
//	        {30.282238527557,119.9988419557 },
//	        {30.282246345799,119.99887255021},
//	        {30.282258275039,119.9988346388 },
//	        {30.282241932822,119.9988625331 }
//	};

    //阈值//TODO 待计算
    private static double THRESHOLD_VALUE = 30;

    //对gpsList去值
    private static void changeGpsArray(double gpsList[][]) {
        //lng 经度 lat 维度 lat（x轴） 横 lng(y轴) 竖
        //第一个点坐标
        double x1 = gpsList[0][0];
        double y1 = gpsList[0][1];
        //最后一个点的坐标
        double x2 = gpsList[gpsList.length - 1][0];
        double y2 = gpsList[gpsList.length - 1][1];
        //距离list
        double[] disList = new double[gpsList.length-2];

        for (int i = 1; i < gpsList.length - 1; i++) {
            //1.得到曲线上的点到首末点连成直线的垂足（经纬度）.
            //2.获取垂足与曲线上的点的距离（经纬度转距离）
        double d = getpedal(x1, y1, x2, y2, gpsList[i][0], gpsList[i][1]);
            disList[i-1] = d;
        }
        //获取最大值与最大值在数组中的位置
        double dmax = (double) getDmax(disList).get("value");
        int index = (Integer) getDmax(disList).get("index")+1;
//        System.out.println("最大值：  "+dmax+"    "+index);

        if(dmax<THRESHOLD_VALUE){//dmax<D，则将这条曲线上的中间点全部舍去
            removeGps(x1,y1,x2,y2);
//            System.out.println("< 的长度为：  "+gpsList1.length);
        }else {//dmax≥D，保留dmax对应的坐标点，并以该点为界，把曲线分为两部分,对这两部分重复使用该方法
            double[][] list1 = getNewList(gpsList,0,index-1);//保留点左边
            double[][] list2 = getNewList(gpsList,index+1,gpsList.length-1);//保留点右边
//            System.out.println("list1的长度为：  "+list1.length+"   "+list1[list1.length-1][0]);
//            System.out.println("list2的长度为：  "+list2.length+"   "+list2[0][0]);
//            System.out.println(">= 的长度为：  "+gpsList1.length);
            if(list1.length>2){
                changeGpsArray(list1);
            }
            if(list2.length>2){
                changeGpsArray(list2);
            }
        }
    }
    //获取划分曲线
    private static double[][] getNewList(double list[][],int startIndex,int endIndex){
        double[][] newList = new double[endIndex-startIndex+1][2];
        int j =0;
        for(int i =startIndex;i<endIndex+1;i++){
            newList[j] = list[i];
            j++;
        }
        return newList;
    }
    //曲线上所有点与直线的距离的最大距离值dmax
    private static Map<String,Object> getDmax(double dmaxList[]){
        double dmax = 0;
        int j=0;
        for (int i =0;i<dmaxList.length;i++){
            if(dmax<=dmaxList[i]){
                dmax = dmaxList[i];
                j = i;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("value",dmax);
        map.put("index",j);
        return map;
    }
    //dmax<D，则将这条曲线上的中间点全部舍去
    private static void removeGps (double x1,double y1,double x2,double y2){
        int a=0,b=0;
        for(int i =0;i< gpsList1.length;i++){
            if(gpsList1[i][0] == x1 &&gpsList1[i][1] == y1){
                a = i;
            }
            if(gpsList1[i][0] == x2 &&gpsList1[i][1] == y2){
                b = i;
            }
        }
        List<Double> list = new ArrayList<Double>();
        for(int i =0;i< gpsList1.length+1-b+a;i++){
           if(i>a){
               gpsList1[i] = gpsList1[i+b-a-1];
           }
        }
        gpsList1 = Arrays.copyOf(gpsList1,gpsList1.length+1-b+a);
    }
    //获取点到直线的垂足
    private static double getpedal(double x1, double y1, double x2, double y2, double x3, double y3) {
        double x12 = x1 - x2;
        double y12 = y1 - y2;
        double k1 = y12 / x12;//斜率1
        double k2 = -x12 / y12;//斜率2
        //求垂足的经纬度坐标
        double cx = (y3 - y1 + k1 * x1 - k2 * x3) / (k1 - k2);
        double cy = k2 * (cx - x3) + y3;
        double dis = Distance(cy, cx, y3, x3);
        return dis;
    }

    private static double EARTH_RADIUS = 6378137; // 地球半径
    //计算点到垂足之间的距离
    private static double Distance(double long1, double lat1, double long2, double lat2) {//单位：米
        double a, b;
        lat1 = red(lat1);
        lat2 = red(lat2);
        a = lat1 - lat2;
        b = red(long1) - red(long2);
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2* EARTH_RADIUS* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)* Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    private static double red(double d) {
        return d * Math.PI / 180.0;
    }
}
