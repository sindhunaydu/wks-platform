package com.wks.bpm.engine.camunda.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.wks.bpm.engine.BpmEngine;
import com.wks.bpm.engine.camunda.http.request.C7HttpRequestFactory;
import com.wks.bpm.engine.client.ProcessEngineClient;
import com.wks.bpm.engine.model.impl.DeploymentImpl;
import com.wks.bpm.engine.model.impl.ProcessDefinitionImpl;
import com.wks.bpm.engine.model.spi.ActivityInstance;
import com.wks.bpm.engine.model.spi.ProcessInstance;
import com.wks.bpm.engine.model.spi.ProcessMessage;
import com.wks.bpm.engine.model.spi.Task;
import com.wks.bpm.engine.model.spi.TaskForm;
import com.wks.rest.client.WksHttpRequest;

/**
 * @author victor.franca
 *
 */
@Component
public class C7EngineClient implements ProcessEngineClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private C7HttpRequestFactory camundaHttpRequestFactory;

	@Override
	public DeploymentImpl[] findDeployments(final BpmEngine bpmEngine) {
		return restTemplate
				.getForEntity(camundaHttpRequestFactory.getDeploymentListRequest(bpmEngine).getHttpRequestUrl(),
						DeploymentImpl[].class)
				.getBody();
	}

	@Override
	public ProcessDefinitionImpl[] findProcessDefinitions(final BpmEngine bpmEngine) {
		return restTemplate
				.getForEntity(camundaHttpRequestFactory.getProcessDefinitionListRequest(bpmEngine).getHttpRequestUrl(),
						ProcessDefinitionImpl[].class)
				.getBody();
	}

	@Override
	public String getProcessDefinitionXML(final String processDefinitionId, final BpmEngine bpmEngine) {
		return restTemplate
				.getForEntity(camundaHttpRequestFactory.getProcessDefinitionXmlRequest(processDefinitionId, bpmEngine)
						.getHttpRequestUrl(), ProcessDefinitionImpl.class)
				.getBody().getBpmn20Xml();
	}

	@Override
	public ProcessInstance[] findProcessInstances(final Optional<String> businessKey, final BpmEngine bpmEngine) {
		return restTemplate.getForEntity(
				camundaHttpRequestFactory.getProcessInstanceListRequest(businessKey, bpmEngine).getHttpRequestUrl(),
				ProcessInstance[].class).getBody();
	}

	@Override
	public ProcessInstance startProcess(final String processDefinitionKey, final BpmEngine bpmEngine) {

		WksHttpRequest request = camundaHttpRequestFactory.getProcessInstanceCreateRequest(processDefinitionKey,
				bpmEngine);

		return restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), ProcessInstance.class)
				.getBody();
	}

	@Override
	public ProcessInstance startProcess(final String processDefinitionKey, final String businessKey,
			final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getProcessInstanceCreateRequest(processDefinitionKey,
				businessKey, bpmEngine);

		return restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), ProcessInstance.class)
				.getBody();
	}

	@Override
	public void deleteProcessInstance(String processInstanceId, final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getProcessInstanceDeleteRequest(processInstanceId,
				bpmEngine);

		restTemplate.delete(request.getHttpRequestUrl());
	}

	@Override
	public ActivityInstance[] findActivityInstances(String processInstanceId, final BpmEngine bpmEngine) {
		return restTemplate
				.getForEntity(camundaHttpRequestFactory.getActivityInstancesGetRequest(processInstanceId, bpmEngine)
						.getHttpRequestUrl(), ActivityInstance.class)
				.getBody().getChildActivityInstances();
	}

	@Override
	public Task[] findTasks(final String processInstanceBusinessKey, final BpmEngine bpmEngine) {
		return restTemplate.getForEntity(
				camundaHttpRequestFactory.getTaskListRequest(processInstanceBusinessKey, bpmEngine).getHttpRequestUrl(),
				Task[].class).getBody();
	}

	@Override
	public void claimTask(String taskId, String taskAssignee, final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getTaskClaimRequest(taskId, taskAssignee, bpmEngine);
		restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), String.class);
	}

	@Override
	public void unclaimTask(String taskId, final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getTaskUnclaimRequest(taskId, bpmEngine);
		restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), String.class);
	}

	@Override
	public void complete(String taskId, JsonObject variables, final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getTaskCompleteRequest(taskId, variables, bpmEngine);
		restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), String.class);
	}

	@Override
	public TaskForm getTaskForm(final String taskId, final BpmEngine bpmEngine) {
		return restTemplate
				.getForEntity(camundaHttpRequestFactory.getTaskFormGetRequest(taskId, bpmEngine).getHttpRequestUrl(),
						TaskForm.class)
				.getBody();
	}

	@Override
	public String findVariables(String processInstanceId, final BpmEngine bpmEngine) {
		return restTemplate.getForEntity(
				camundaHttpRequestFactory.getVariablesListRequest(processInstanceId, bpmEngine).getHttpRequestUrl(),
				String.class).getBody();
	}

	@Override
	public void sendMessage(ProcessMessage processMesage, final BpmEngine bpmEngine) {
		WksHttpRequest request = camundaHttpRequestFactory.getMessageSendRequest(processMesage, bpmEngine);
		restTemplate.postForEntity(request.getHttpRequestUrl(), request.getHttpEntity(), String.class);
	}

}
