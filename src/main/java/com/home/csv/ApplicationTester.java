package com.home.csv;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yogeshkumararora on 22/07/17.
 */
public class ApplicationTester {

    final static Logger logger = Logger.getLogger(ApplicationTester.class);

    //Input CSV file header
    final static String[] INPUT_FILE_HEADER_MAPPING = {"StaffId", "FolderName", "JobCount"};
    //Mapping CSV file header
    final static String[] MAPPING_FILE_HEADER_MAPPING = {"StaffId", "GB_GF", "ServiceLine"};

    /**
     * @param args
     */
    public static void main(String[] args) {

        String INPUT_BASE_PATH = "/Users/yogeshkumararora/IdeaProjects/csv-manager/src/main/resources/com/home/csv/input";
        String[] inputCsvFiles = getAllCsvFilesForFolder(new File(INPUT_BASE_PATH));

        String mappingCsvFile = "/Users/yogeshkumararora/IdeaProjects/csv-manager/src/main/resources/com/home/csv/mapping/mapping.csv";

        String outputMasterCsvFile = "/Users/yogeshkumararora/IdeaProjects/csv-manager/pipeline-master-data.csv";

        //Create a fresh master file every time
        File masterFile = new File(outputMasterCsvFile);
        if (masterFile.exists()) {
            logger.info("Deleting an already existing master file " + outputMasterCsvFile);
            masterFile.delete();
        }

        CsvFileReader mappingCsvFileReader = new CsvFileReader();
        Map<String, Mapping> mappings = mappingCsvFileReader.readMappingCsvFile(mappingCsvFile, ApplicationTester.MAPPING_FILE_HEADER_MAPPING);
        if (logger.isDebugEnabled()) {
            logger.debug("The number of rows in mapping file are:" + mappings.size());
        }

        CsvFileReader inputCsvFileReader = new CsvFileReader();
        CsvFileWriter csvFileWriter = new CsvFileWriter();

        for (String inputCsvFile : inputCsvFiles) {
            logger.info("Reading Jenkins " + inputCsvFile + " file:");
            List<Pipeline> jenkinsFolderRecords = inputCsvFileReader.readInputCsvFile(INPUT_BASE_PATH + File.separator + inputCsvFile, ApplicationTester.INPUT_FILE_HEADER_MAPPING);

            for (Pipeline jenkinsFolderRecord : jenkinsFolderRecords) {
                if (logger.isDebugEnabled()) {
                    logger.debug(jenkinsFolderRecord.toString());
                }
            }

            logger.info("Writing " + inputCsvFile + " to Master data file:<" + outputMasterCsvFile + ">");
            csvFileWriter.writeCsvFile(outputMasterCsvFile, jenkinsFolderRecords, mappings);
            logger.info("Successfully written data of " + inputCsvFile + " to Master data file: <" + outputMasterCsvFile +">");
        }
    }

    /**
     * Get all the csv files in a folder and sub-folers, if any.
     *
     * @param folder the folder to find csv files from.
     * @return String[] of csv files
     */
    private static String[] getAllCsvFilesForFolder(final File folder) {

        List<String> csvFiles = new ArrayList();

        //Filter all csv files.
        for (final File fileEntry : folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".csv");
            }
        })) {
            if (fileEntry.isDirectory()) {
                getAllCsvFilesForFolder(fileEntry);
            } else {
                csvFiles.add(fileEntry.getName());
            }
        }
        return csvFiles.toArray(new String[csvFiles.size()]);
    }
}
