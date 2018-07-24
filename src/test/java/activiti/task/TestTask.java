package activiti.task;


import com.java.activiti.util.ProcessInstanceConst;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:activiti-context.xml", "classpath:spring-mvc.xml","classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = false)
@Transactional*/

public class TestTask {

	@Resource
	private ProcessEngine processEngine;  

	@Before
	public void setUp() throws Exception {
//		processEngine = ProcessEngines.getDefaultProcessEngine();  
	}

	@After
	public void tearDown() throws Exception {
	}
	
//	@Test
//	public void deploy(){
//        DeploymentBuilder deploymentBuilder = processEngine
//                .getRepositoryService().createDeployment();
//
//        // 方式一：读取单个的流程定义文件
//        deploymentBuilder.addClasspathResource("activiti/activitiJncszhzfProcess.bpmn");
//        deploymentBuilder.addClasspathResource("activiti/activitiJncszhzfProcess.png");
//        Deployment deployment = deploymentBuilder.deploy();
//        // 部署查询对象，查询表act_re_deployment
//        DeploymentQuery query = processEngine.getRepositoryService()
//                .createDeploymentQuery();
//        List<Deployment> list = query.list();
//        for (Deployment deploymentEle : list) {
//            String id = deploymentEle.getId();
//            System.out.println(id);
//        }
//        Assert.assertEquals(1, 1);
//	}

	//@Test
	public void test() {
		
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("userIds", "baoli01");
//		String processDefinitionKey = "activitiJncszhzfProcessnew6";
//		// 启动流程
//		ProcessInstance processInstance = processEngine.getRuntimeService() .startProcessInstanceByKey(processDefinitionKey, variables);
//        System.out.println(processInstance.getId());
//		Assert.assertNotNull(processInstance.getId());
////		fail("Not yet implemented");
	}

	//部署流程定义
	//@Test
	public void deploy(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment()
				.addClasspathResource("activiti/"+ProcessInstanceConst.BPMN+".bpmn")
				.addClasspathResource("activiti/"+ProcessInstanceConst.PNG+".png")
				.name("江宁流程Final118")
				.category("市政网格")
				.deploy();
		System.out.println("部署的id"+deploy.getId());
		System.out.println("部署的名称"+deploy.getName());
	      // 部署查询对象，查询表act_re_deployment
//        DeploymentQuery query = processEngine.getRepositoryService()
//                .createDeploymentQuery();
//        List<Deployment> list = query.list();
//        for (Deployment deploymentEle : list) {
//            String id = deploymentEle.getId();
//            System.out.println(id);
//        }
	}


	//执行流程
	//@Test
	public void startProcess(){
		//  取运行时服务
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userIds", "baoli01");
		String processDefiKey = ProcessInstanceConst.KEY;
		//取得流程实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefiKey,variables);//通过流程定义的key 来执行流程

		System.out.println("流程实例id:"+pi.getId());//流程实例id
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());//输出流程定义的id
	}

	//删除流程
	//@Test
	public void deleteDeployment(){
		String deployment = "1477501";
		processEngine.getRepositoryService().deleteDeployment(deployment);
 		System.out.println("删除部署成功");
	}


	//执行任务
	//@Test
	public void complete(){
//		String taskId = "800005";
//		processEngine.getTaskService().complete(taskId);
	}

}
