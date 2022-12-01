package com.wks.caseengine.rest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wks.bpm.engine.client.BpmEngineClientFacade;
import com.wks.caseengine.bpm.BpmEngineService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("process-definition")
@Tag(name = "Process Definition", description = "Access information about processes definitions in Camunda")
public class ProcessDefinitionController {

	@Autowired
	private BpmEngineClientFacade processEngineClient;

	@Autowired
	private BpmEngineService bpmEngineService;

	@GetMapping(value = "/{bpmEngineId}/{processDefinitionId}/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public String get(@PathVariable final String bpmEngineId, @PathVariable final String processDefinitionId)
			throws Exception {
		return processEngineClient.getProcessDefinitionXMLById(processDefinitionId, bpmEngineService.get(bpmEngineId));
	}

}
