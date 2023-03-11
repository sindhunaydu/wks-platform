package com.wks.caseengine.rest.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.wks.caseengine.rest.server.BpmEngineTypesController;

@WebMvcTest(controllers = BpmEngineTypesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BpmEngineTypesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFind() throws Exception {
		this.mockMvc.perform(get("/bpm-engine-type/")).andExpect(status().isOk());
	}

}
