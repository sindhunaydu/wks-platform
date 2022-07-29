package com.mmc.bpm.client.cases.instance;

import java.util.List;

public interface CaseInstanceService {

	public List<CaseInstance> find() throws Exception;

	public CaseInstance create(final List<CaseAttribute> attributes) throws Exception;

	public void updateStatus(final String businessKey, final String newStatus) throws Exception;

	public void delete(final String businessKey) throws CaseInstanceNotFoundException, Exception;
}