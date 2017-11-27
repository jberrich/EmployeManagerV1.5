package ma.jberrich.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import ma.jberrich.dao.Configuration;

public class DatabaseSet extends DBTestCase {
    private static final Logger LOGGER = RootLogger.getLogger(DBTestCase.class);

	private static final String INPUT_DATA_SET_FILENAME = "datasets/input.xml";
	private static final String OUTPUT_DATA_SET_FILENAME = "datasets/output.xml";

	public static final String SERVICE_TABLE_NAME = "DEPT";
	public static final String EMPLOYE_TABLE_NAME = "EMP";

	public static final int SERVICE_ROW_COUNT = 4;
	public static final int EMPLOYE_ROW_COUNT = 3;

	public DatabaseSet(String name) {
		super(name);
		Configuration configuration = ConfigFactory.create(Configuration.class);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, configuration.getDriver());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, configuration.getUrl());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, configuration.getUsername());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, configuration.getPassword());
	}

	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		super.setUpDatabaseConfig(config);
		config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
		config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		FlatXmlDataSet loadedDataSet = new FlatXmlDataSet(new FileInputStream(INPUT_DATA_SET_FILENAME));
		return loadedDataSet;

	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
		return DatabaseOperation.NONE;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	protected QueryDataSet getDatabaseDataSet() throws Exception {
		QueryDataSet loadedDataSet = new QueryDataSet(getConnection());
		loadedDataSet.addTable(SERVICE_TABLE_NAME);
		loadedDataSet.addTable(EMPLOYE_TABLE_NAME);
		return loadedDataSet;
	}

	@Test
	public void testCheckDataLoaded() throws Exception {
		IDataSet databaseDataSet = getDatabaseDataSet();
		assertNotNull(databaseDataSet);
		int deptRowCount = databaseDataSet.getTable(SERVICE_TABLE_NAME).getRowCount();
		assertEquals("Le nombre d'enregistrements dans la table \"" + SERVICE_TABLE_NAME + "\" ne correspond pas", SERVICE_ROW_COUNT, deptRowCount);
		int empRowCount = databaseDataSet.getTable(EMPLOYE_TABLE_NAME).getRowCount();
		assertEquals("Le nombre d'enregistrements dans la table \"" + EMPLOYE_TABLE_NAME + "\" ne correspond pas", EMPLOYE_ROW_COUNT, empRowCount);
	}

	@Test
	public void testCompareTable() throws Exception {
		IDataSet databaseDataSet = getDatabaseDataSet();
		ITable deptTable = databaseDataSet.getTable(SERVICE_TABLE_NAME);
		ITable empTable = databaseDataSet.getTable(EMPLOYE_TABLE_NAME);

		IDataSet expectedDataSet = new FlatXmlDataSet(new File(INPUT_DATA_SET_FILENAME));
		ITable deptExpectedTable = expectedDataSet.getTable(SERVICE_TABLE_NAME);
		ITable empExpectedTable = expectedDataSet.getTable(EMPLOYE_TABLE_NAME);

		Assertion.assertEquals(deptExpectedTable, deptTable);
		Assertion.assertEquals(empExpectedTable, empTable);
	}

	@Test
	public void testCompareQuery() throws Exception {
		IDataSet loadedDataSet = getDataSet();
		QueryDataSet queryDataSet = new QueryDataSet(getConnection());
		queryDataSet.addTable(SERVICE_TABLE_NAME);
		queryDataSet.addTable(EMPLOYE_TABLE_NAME);

		Assertion.assertEquals(loadedDataSet, queryDataSet);
	}

	@Test
	public void testExportData() throws Exception {
		IDataSet dataSet = getDatabaseDataSet();

		File inputFile = new File(INPUT_DATA_SET_FILENAME);
		assertNotNull(inputFile);

		File outputFile = new File(OUTPUT_DATA_SET_FILENAME);
		FlatXmlDataSet.write(dataSet, new FileOutputStream(outputFile));

		String inputDataSetString = FileUtils.readFileToString(inputFile, "UTF8").replace("  ", "\t").trim();
		String outputDataSetString = FileUtils.readFileToString(outputFile, "UTF8").replace("  ", "\t").trim();
		assertEquals(inputDataSetString, outputDataSetString);
	}

}
