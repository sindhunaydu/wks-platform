package com.wks.caseengine.rest.server;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonParser;
import com.wks.bpm.engine.model.spi.Task;
import com.wks.caseengine.tasks.TaskService;

@RestController
@RequestMapping("task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping(value = "/")
	public List<Task> find(final @RequestParam(required = false) String bpmEngineId,
			@RequestParam(required = false) String processInstanceBusinessKey) throws Exception {
		return taskService.find(processInstanceBusinessKey, Optional.ofNullable(bpmEngineId));
	}

	@PostMapping(value = "/{bpmEngineId}/{taskId}/claim/{taskAssignee}")
	public void claim(final @PathVariable String bpmEngineId, @PathVariable String taskId,
			@PathVariable String taskAssignee) throws Exception {
		taskService.claim(taskId, taskAssignee, bpmEngineId);
	}

	@PostMapping(value = "/{bpmEngineId}/{taskId}/unclaim")
	public void unclaim(final @PathVariable String bpmEngineId, @PathVariable String taskId) throws Exception {
		taskService.unclaim(taskId, bpmEngineId);
	}

	@PostMapping(value = "/{bpmEngineId}/{taskId}/complete")
	public void complete(final @PathVariable String bpmEngineId, @PathVariable String taskId,
			@RequestBody String variables) throws Exception {
		taskService.complete(taskId, JsonParser.parseString(variables).getAsJsonObject(), bpmEngineId);
	}

}
