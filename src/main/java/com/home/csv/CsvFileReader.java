package com.home.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yogeshkumararora on 22/07/17.
 */
public final class CsvFileReader {

    final static Logger logger = Logger.getLogger(CsvFileReader.class);


    //Pipeline attributes
    private static final String STAFF_ID = "StaffId";
    private static final String FOLDER_NAME = "FolderName";
    private static final String JOB_COUNT = "JobCount";
    private static final String GB_GF = "GB_GF";
    private static final String SERVICE_LINE = "ServiceLine";

    /**
     * Read the pipeline's input csv file
     *
     * @param fileName
     * @return the List of pipeline.
     */
    public List<Pipeline> readInputCsvFile(final String fileName, final String[] fileHeaderMapping) {

        List<CSVRecord> csvRecords = readCsvFile(fileName, fileHeaderMapping);

        List<Pipeline> pipelines = new ArrayList();

        //Read the CSV file records starting from the second record to skip the header
        for (int i = 1; i < csvRecords.size(); i++) {
            CSVRecord record = csvRecords.get(i);

            //Create a new pipeline object and fill his data
            Pipeline pipeline = new Pipeline(Long.parseLong(record.get(STAFF_ID)), record.get(FOLDER_NAME), Integer.parseInt(record.get(JOB_COUNT)));
            pipelines.add(pipeline);
        }
        return pipelines;
    }

    /**
     * Read the mapping csv file
     *
     * @param fileName
     * @return the List of Mapping object.
     */
    public Map<String, Mapping> readMappingCsvFile(final String fileName, final String[] fileHeaderMapping) {

        List<CSVRecord> csvRecords = readCsvFile(fileName, fileHeaderMapping);

        Map<String, Mapping> mappings = new HashMap<String, Mapping>();

        //Read the CSV file records starting from the second record to skip the header
        for (int i = 1; i < csvRecords.size(); i++) {
            CSVRecord record = csvRecords.get(i);
            String staffId = record.get(STAFF_ID);
            String gbGf = record.get(GB_GF);
            if(gbGf != null && gbGf.length() > 0) {
                gbGf = gbGf.trim();
            }

            String svcLine = record.get(SERVICE_LINE);
            if(svcLine != null && svcLine.length() > 0) {
                svcLine = svcLine.trim();
            }

            //Create a new mapping object and fill his data
            Mapping mapping = new Mapping(Long.parseLong(staffId), gbGf, svcLine);
            logger.info("Populating mapping map (key:"+ staffId + ", value:"+ mapping);
            mappings.put(staffId, mapping);
        }
        return mappings;
    }

    private List<CSVRecord> readCsvFile(final String fileName, final String[] fileHeaderMapping) {

        logger.info("Reading file:<" + fileName + ">");

        FileReader fileReader = null;

        CSVParser csvFileParser = null;

        //Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaderMapping);
        List<CSVRecord> csvRecords = null;

        try {

            //Create a new list of student to be filled by CSV file data

            //initialize FileReader object
            fileReader = new FileReader(fileName);

            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);

            //Get a list of CSV file records
            csvRecords = csvFileParser.getRecords();
        } catch (IOException e) {
            logger.error("Error in CsvFileReader !!!", e);
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                logger.error("Error while closing fileReader/csvFileParser !!!", e);
                e.printStackTrace();
            }
        }
        return csvRecords;
    }
}
