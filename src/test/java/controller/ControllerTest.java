package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:activiti-context.xml", "classpath:spring-mvc.xml","classpath:applicationContext.xml"})
//@TransactionConfiguration(defaultRollback = false)
//@Transactional
public class ControllerTest {

	@Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc; 
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void test() {
//        String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//			        post("/statistics/issueCount")    //请求的url,请求的方法是get
//			                .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//			                .param("startDay","2017-02-01 00:00:00")         //添加参数
//			).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//			        .andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//        System.out.println("issueCount--------返回的json = " + responseString);
//		
//	}
	
//	@Test
//	public void testIssueStatus() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/issueStatusPercent")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("issueStatusPercent--------返回的json = " + responseString);
//		
//	}
	
//	@Test
//	public void testStreetIssue() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/streetIssue")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("streetIssue--------返回的json = " + responseString);
//		
//	}
//	
//	@Test
//	public void teststreetFinishedIssue() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/streetFinishedIssue")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("streetFinishedIssue--------返回的json = " + responseString);
//		
//	}
//
//	@Test
//	public void testswbjIssue() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/wbjIssue")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("wbjIssue--------返回的json = " + responseString);
//		
//	}
//
//	@Test
//	public void testwbjFinishedIssue() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/wbjFinishedIssue")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("wbjFinishedIssue--------返回的json = " + responseString);
//		
//	}
//
//	@Test
//	public void tesissueBelong() {
//		String responseString = "";
//		try {
//			responseString = mockMvc.perform(
//					post("/statistics/issueBelong")    //请求的url,请求的方法是get
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
//					.param("startDay","2017-02-01")         //添加参数
//					).andExpect(status().isOk())    //返回的状态是200
////			        .andDo(print())         //打印出请求和相应的内容
//					.andReturn().getResponse().getContentAsString();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   //将相应的数据转换为字符串
//		System.out.println("issueBelong--------返回的json = " + responseString);
//		
//	}

	
	
}
