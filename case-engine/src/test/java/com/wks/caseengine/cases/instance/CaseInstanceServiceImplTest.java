package com.wks.caseengine.cases.instance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.wks.caseengine.cases.businesskey.GenericBusinessKeyGenerator;
import com.wks.caseengine.cases.definition.CaseDefinition;
import com.wks.caseengine.cases.definition.CaseDefinitionServiceImpl;
import com.wks.caseengine.cases.definition.event.CaseEventExecutor;
import com.wks.caseengine.cases.instance.CaseInstance;
import com.wks.caseengine.cases.instance.CaseInstanceCreateServiceImpl;
import com.wks.caseengine.cases.instance.CaseInstanceServiceImpl;
import com.wks.caseengine.repository.DataBaseConfig;
import com.wks.caseengine.repository.JDBCDataRepository;

public class CaseInstanceServiceImplTest {

	private final String DATABASE_URL = "jdbc:h2:file:~/wks_bpm_interface_test";
	private Connection connection;

	private JDBCDataRepository jdbcDataRepository;

	@Before
	public void init() throws Exception {
		this.connection = DriverManager.getConnection(DATABASE_URL);

		deleteTables();

		DataBaseConfig dataBaseConfig = new DataBaseConfig();
		dataBaseConfig.setDatabaseURL(DATABASE_URL);
		jdbcDataRepository = new JDBCDataRepository(dataBaseConfig);
		jdbcDataRepository.postConstruct();

	}

	@Test
	public void shouldCreateCaseInstanceTest() throws Exception {
		
		//Given
		CaseDefinitionServiceImpl caseDefinitionServiceImpl = new CaseDefinitionServiceImpl();
		caseDefinitionServiceImpl.setDataRepository(jdbcDataRepository);

		CaseDefinition caseDefinition = CaseDefinition.builder().id("1").build();
		caseDefinitionServiceImpl.create(caseDefinition);

		CaseInstanceServiceImpl caseInstanceServiceImpl = new CaseInstanceServiceImpl();

		caseInstanceServiceImpl.setDataRepository(jdbcDataRepository);
		CaseInstanceCreateServiceImpl caseInstanceCreateServiceImpl = new CaseInstanceCreateServiceImpl();
		caseInstanceCreateServiceImpl.setDataRepository(jdbcDataRepository);
		caseInstanceCreateServiceImpl.setBusinessKeyCreator(new GenericBusinessKeyGenerator());
		caseInstanceServiceImpl.setCaseInstanceCreateService(caseInstanceCreateServiceImpl);
		caseInstanceServiceImpl.setCaseEventExecutor(new CaseEventExecutor());

		CaseInstance caseInstanceParam = CaseInstance.builder().caseDefinitionId("1").businessKey("1").build();
		
		//When
		CaseInstance caseInstance = caseInstanceServiceImpl.create(caseInstanceParam);

		//Then
		assertTrue(caseInstance.getBusinessKey().startsWith(GenericBusinessKeyGenerator.PREFIX));
		assertEquals("1", caseInstance.getCaseDefinitionId());
	}

	private void deleteTables() throws Exception {
		try (var statement = connection.createStatement();) {

			statement.executeUpdate("DROP TABLE IF EXISTS case_definition");

		} catch (SQLException ex) {
			// TODO error handling
			throw new Exception(ex);
		}
	}

}