package activiti.task;

import com.java.activiti.util.ProcessInstanceConst;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/*@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:activiti-context.xml", "classpath:spring-mvc.xml","classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = false)*/
public class TestDbTask {
    @Resource
    private ProcessEngine processEngine;

    //部署流程定义
    //@Test
    public void deploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("activiti/"+ ProcessInstanceConst.DB_BPMN+".bpmn")
                .addClasspathResource("activiti/"+ProcessInstanceConst.DB_PNG+".png")
                .name("督办流程")
                .category("市政督办201803")
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

    //删除流程
    //@Test
    public void deleteDeployment(){
		String deployment = "1057501";
		processEngine.getRepositoryService().deleteDeployment(deployment);
		System.out.println("删除部署成功");
    }

    //执行流程
   //@Test
    public void startProcess(){
        //  取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("dbUsers", "baoli01");
        String processDefiKey = ProcessInstanceConst.DB_KEY;
        //取得流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefiKey,variables);//通过流程定义的key 来执行流程

        System.out.println("流程实例id:"+pi.getId());//流程实例id
        System.out.println("流程定义id:"+pi.getProcessDefinitionId());//输出流程定义的id
    }

    //执行任务
   //@Test
    public void complete(){
		String taskId = "1040011";
		TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = new HashMap<String, Object>();

        variables.put("dealWay",0);
        variables.put("JDGroupId","dongshanjiedao");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstancesId=task.getProcessInstanceId();
        System.out.println(processInstancesId);
             Authentication.setAuthenticatedUserId("cmc"); // 添加批注时候的审核人，通常应该从session获取
        taskService.addComment(taskId,processInstancesId,"尝试添加批注");
        taskService.complete(taskId,variables);

    }


}
