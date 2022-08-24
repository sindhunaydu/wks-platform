package com.mmc.bpm.client.cases.definition.hook.create;

import java.util.ArrayList;
import java.util.List;

import com.mmc.bpm.client.cases.definition.event.CaseEvent;
import com.mmc.bpm.client.cases.definition.hook.CaseHook;

import lombok.Getter;

@Getter
public class PostCaseCreateHook implements CaseHook {

	private List<CaseEvent> caseEvents = new ArrayList<>();

	@Override
	public void attach(CaseEvent caseEvent) {
		caseEvents.add(caseEvent);
	}

}
