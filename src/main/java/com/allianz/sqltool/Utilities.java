package com.allianz.sqltool;

import com.allianz.sqltool.model.DCXEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Utilities {

    private static final Logger LOG = LoggerFactory.getLogger(Utilities.class);

    private Utilities() {
    }

    /**
     * Writes Queries and Rollback Queries into two separated files
     *
     * @param path     The path the file are to be created
     * @param contents Two strings with the query and the rollback
     */
    public static void writeFiles(String path, String[] contents, String iteration, int ticketNum, String dcxName) {

        File insertFile = new File(path + File.separator + "SALES-" + ticketNum + "-dml-" + iteration + "-" + dcxName + ".sql");


        File deleteFile = new File(path + File.separator + "SALES-" + ticketNum + "-rollback-" + iteration + "-" + dcxName + ".sql");

        try (FileWriter insert =
                     new FileWriter(insertFile)) {
            insert.write(contents[0]);
        } catch (IOException e) {
            LOG.error("Error writing files", e);
        }

        try (FileWriter delete =
                     new FileWriter(deleteFile)) {
            delete.write(contents[1]);
        } catch (IOException e) {
            LOG.error("Error writing files", e);
        }

    }

    /**
     * Generates the SELECT id FROM dcx statement
     * and the WHERE partner = '' ... statement
     * Calls generateDCXSELECT() method inside
     *
     * @param dcx The DCXEntity
     * @return "SELECT id FROM dcx WHERE ..." and WHERE clause
     */
    public static String[] buildDCXSELECT(DCXEntity dcx) {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();

        if (dcx.getPartner() != null) {
            columns.add("partner");
            values.add(dcx.getPartner());
        }
        if (dcx.getMarket() != null) {
            columns.add("market");
            values.add(dcx.getMarket());
        }
        if (dcx.getSalesChannel() != null) {
            columns.add("salesChannel");
            values.add(dcx.getSalesChannel());
        }
        if (dcx.getLob() != null) {
            columns.add("lob");
            values.add(dcx.getLob());
        }
        if (dcx.getFlowType() != null) {
            columns.add("flowType");
            values.add(dcx.getFlowType());
        }

        //At this point dcx PRIMARY_KEY is complete.
        return generateDCXSELECT(columns, values);
    }


    private static String[] generateDCXSELECT(List<String> columns, List<String> values) {
        StringBuilder select = new StringBuilder("(SELECT id FROM dcx WHERE ");
        StringBuilder where = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            String s = columns.get(i) + " = '"
                    + values.get(i) + "'"
                    + (i != columns.size() - 1 ? " AND " : "");
            select.append(s);
            where.append(s);
        }
        select.append(")");

        return new String[]{select.toString(), where.toString()};
    }

    public static Properties loadProperties(String propertiesPath){
        Properties prop = new Properties();
        try  {
            InputStream input = new ClassPathResource(propertiesPath).getInputStream();
            prop.load(input);
        } catch (IOException ex) {
            LOG.error("Error: could not load properties");
        }
        return prop;
    }
}