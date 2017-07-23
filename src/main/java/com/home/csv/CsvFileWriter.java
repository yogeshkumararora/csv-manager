package com.home.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by yogeshkumararora on 22/07/17.
 */
public class CsvFileWriter {

    final static Logger logger = Logger.getLogger(CsvFileWriter.class);

    //Delimiter used in CSV file
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String[] FILE_HEADER = {"StaffId", "FolderName", "JobCount", "GBGF", "ServiceLine"};

    /**
     * Write the pipeline information to csv file
     *
     * @param fileName  the output file to be created.
     * @param pipelines the input list to read the content from.
     */
    public void writeCsvFile(String fileName, List<Pipeline> pipelines, Map<String, Mapping> mappings) {

        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;

        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {

            File file = new File(fileName);
            boolean masterFileExists = file.exists();

            //initialize FileWriter object
            fileWriter = new FileWriter(fileName, true);

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header only once, when the master file is created.
            if (!masterFileExists) {
                csvFilePrinter.printRecord(FILE_HEADER);
            }

            //Write a new pipeline object list to the CSV file i.e. one pipeline data row
            for (Pipeline pipeline : pipelines) {
                List pipelineDataRecord = new ArrayList();
                String staffId = String.valueOf(pipeline.getStaffId());
                pipelineDataRecord.add(staffId);
                pipelineDataRecord.add(pipeline.getFolderName());
                pipelineDataRecord.add(String.valueOf(pipeline.getJobCount()));
                Mapping mapping = mappings.get(staffId);

                if (mapping != null) {
                    logger.info("mapping found for staffId: " + mapping);
                    String gbGf = mapping.getGbGf();
                    pipelineDataRecord.add(mapping.getGbGf());
                    pipelineDataRecord.add(mapping.getSvcLine());
                    logger.info("pipelineRecord:" + pipelineDataRecord);

                } else {
                    logger.info("mapping not found for staffId:" + staffId);
                }
                csvFilePrinter.printRecord(pipelineDataRecord);
            }

            logger.info("The file <" + fileName + "> created successfully !!!");

        } catch (IOException e) {
            logger.error("Error in CsvFileWriter !!!", e);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                logger.error("Error while flushing/closing fileWriter/csvPrinter !!!", e);
            }
        }
    }
}
