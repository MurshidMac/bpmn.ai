package de.viadee.ki.sparkimporter.util;

import com.beust.jcommander.Parameter;

/**
 * Configures command line parameters of the import application.
 */
public class SparkImporterCSVArguments {

	private static SparkImporterCSVArguments sparkImporterCSVArguments = null;

	@Parameter(names = { "--file-source",
			"-fs" }, required = true, description = "Path an name of the CSV-File to be processed. You can generate the file with a query such as this one: SELECT *\r\n"
					+ "FROM ACT_HI_PROCINST a\r\n" + "JOIN ACT_HI_VARINST v ON a.PROC_INST_ID_ = v.PROC_INST_ID_ \r\n"
					+ "AND a.proc_def_key_ = 'XYZ' \r\n" + "")
	private String fileSource;

	@Parameter(names = { "--data-level",
			"-dl" }, required = false, description = "Which level should the resulting data have. It can be process or activity.")
	private String dataLavel = SparkImporterVariables.DATA_LEVEL_PROCESS;

	@Parameter(names = { "--delimiter",
			"-d" }, required = true, description = "Character or string that separates fields such as [ ;,  | or ||| ]. Please make sure that these are not contained in your data.")
	private String delimiter;

	@Parameter(names = { "--file-destination",
			"-fd" }, required = true, description = "The name of the target folder, where the resulting csv files are being stored, i.e. the data mining table.")
	private String fileDestination;

	@Parameter(names = { "--revision-count", "-rc" }, description = "Boolean toggle to enable the counting of changes "
			+ "to a variable. It results in a number of columns named <VARIABLE_NAME>_rev.", arity = 1)
	private boolean revisionCount = true;

	@Parameter(names = { "--step-results",
			"-sr" }, description = "Should intermediate results be written into CSV files?", arity = 1)
	private boolean writeStepResultsToCSV = false;

	@Parameter(names = { "--working-directory",
			"-wd" }, required = false, description = "Folder where the configuration files are stored or should be stored.")
	private String workingDirectory = "./";

	@Parameter(names = { "--log-directory",
			"-ld" }, required = false, description = "Folder where the log files should be stored.")
	private String logDirectory = "./";

	@Parameter(names = { "--save-mode",
			"-sm" }, required = false, description = "Should the result be appended to the destination or should it be overwritten?")
	private String saveMode = SparkImporterVariables.SAVE_MODE_APPEND;

	@Parameter(names = { "--output-format",
			"-of" }, required = false, description = "In which format should the result be written (parquet or csv)?")
	private String outputFormat = SparkImporterVariables.OUTPUT_FORMAT_PARQUET;

	@Parameter(names = { "--dev-type-cast-check",
			"-devtcc" }, required = false, description = "Development feature: Check for type casting errors of columns.", arity = 1)
	private boolean devTypeCastCheckEnabled = false;

	@Parameter(names = { "--dev-process-state-column-workaround",
			"-devpscw" }, required = false, description = "Development feature: If the process state column is empty in source data (e.g. due to an older Camunda version) the matching is done on variable name column instead. Only works if data level is process!", arity = 1)
	private boolean devProcessStateColumnWorkaroundEnabled = false;
	
	@Parameter(names = { "--process-filter",
	"-pf" }, required = false, description = "Execute pipeline for a specific processDefinitionId.")
	private String processDefinitionId = null;

	/**
	 * Singleton.
	 */
	private SparkImporterCSVArguments() {
	}

	public boolean isRevisionCount() {
		return revisionCount;
	}

	public String getFileSource() {
		return fileSource;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public String getFileDestination() {
		return fileDestination;
	}

	public boolean isWriteStepResultsToCSV() {
		return writeStepResultsToCSV;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public String getLogDirectory() {
		return logDirectory;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public boolean isDevTypeCastCheckEnabled() {
		return devTypeCastCheckEnabled;
	}

	public boolean isDevProcessStateColumnWorkaroundEnabled() {
		return devProcessStateColumnWorkaroundEnabled;
	}

	public String getProcessDefinitionFilterId() {
		return processDefinitionId;
	}

	/**
	 * @return DataExtractorArguments-Instanz as Singleton
	 */
	public static SparkImporterCSVArguments getInstance() {
		if (sparkImporterCSVArguments == null) {
			sparkImporterCSVArguments = new SparkImporterCSVArguments();
		}
		return sparkImporterCSVArguments;
	}

	@Override
	public String toString() {
		return "SpringImporterArguments{" + "fileSource='" + fileSource + '\'' + ", delimiter='" + delimiter
				+ '\'' + ", fileDestination='" + fileDestination + '\'' + ", revisionCount=" + revisionCount
				+ '\'' + ", workingDirectory=" + workingDirectory
				+ '\'' + ", saveMode=" + saveMode
				+ '\'' + ", outputFormat=" + outputFormat
				+ '\'' + ", devTypeCastCheckEnabled=" + devTypeCastCheckEnabled
				+ '\'' + ", devProcessStateColumnWorkaroundEnabled=" + devProcessStateColumnWorkaroundEnabled
				+ '\'' + ", logDirectory=" + logDirectory + '}';
	}
}
