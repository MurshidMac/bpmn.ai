package de.viadee.ki.sparkimporter;

import de.viadee.ki.sparkimporter.util.SparkImporterUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KafkaProcessingApplicationIntegrationTest {

    private final static String DATA_PROCESSING_TEST_OUTPUT_DIRECTORY = "integration-test-result-kafka-processing";
    private final static String DATA_PROCESSING_TEST_INPUT_DIRECTORY = "./src/test/resources/integration_test_kafka_processing_data";

    @Test
    public void testKafkaDataProcessing() throws Exception {
        //run main class
        String args[] = {"-fs", DATA_PROCESSING_TEST_INPUT_DIRECTORY, "-fd", DATA_PROCESSING_TEST_OUTPUT_DIRECTORY, "-d", "|", "-sr", "false", "-wd", "./src/test/resources/"};
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[*]");
        SparkSession.builder().config(sparkConf).getOrCreate();

        // run main class
        KafkaProcessingApplication.main(args);

        //start Spark session
        SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("IntegrationTest")
                .getOrCreate();

        //generate Dataset and create hash to compare
        Dataset<Row> importedDataset = sparkSession.read()
                .option("inferSchema", "true")
                .option("delimiter","|")
                .option("header", "true")
                .csv(DATA_PROCESSING_TEST_OUTPUT_DIRECTORY + "/result.csv");

        //check that dataset contains 4 lines
        assertEquals(4, importedDataset.count());

        //check that dataset contains 41 columns
        assertEquals(41, importedDataset.columns().length);

        //check hash of dataset
        String hash = SparkImporterUtils.getInstance().md5CecksumOfObject(importedDataset.collect());
        assertEquals("39497FCD0CE218F5EA46E3B96545D560", hash);

    }
}