package com.allianz.sqltool.controllers;

import com.allianz.sqltool.model.*;
import com.allianz.sqltool.Utilities;
import com.allianz.sqltool.repo.DcxRepository;
import com.allianz.sqltool.service.ConfToolServiceSearch;
import com.allianz.sqltool.service.DCXQueryGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(value = "sql")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SQLWS
{
    @Autowired
    private DcxRepository dcxRepository;

    @Autowired
    private ConfToolServiceSearch confToolServiceSearch;
    @Autowired
    private DCXQueryGeneratorService dcxQueryGeneratorService;

    public static final String PROPERTIES_PATH = "excel.properties";
    private static final Properties arrProps = Utilities.loadProperties(PROPERTIES_PATH);

//    private static final String WORKSHEET_NAME = "Purchase Configurations";

    @RequestMapping
    (
        path = "/test",
        method = RequestMethod.POST,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String test
    (
            @RequestPart("excelFile") MultipartFile excelFile
    )
    {
        System.out.println("EXCEL: "+  excelFile);

        String ticketNum = "356";
        String filePath = "C:\\Users\\e107580\\Downloads";
        boolean insertDatabase = true;
        boolean writeScripts = true;

        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());

            System.out.println("WORKBOOK:" + workbook.getNumberOfSheets());

            List<DCXEntity> arrDCXExcel = getDCXFromExcel(workbook);

            System.out.println("ARR DCX: " + arrDCXExcel.size() );

            boolean generatedOneOrMoreDCX = false;

            for (int i = 0; i < arrDCXExcel.size(); i++)
            {
                System.out.println("Executing SQL generator for dcx number " + i);
                DCXEntity dcx = arrDCXExcel.get(i);
                String[] output = sqlGenerator(dcx, insertDatabase, writeScripts);

                if(StringUtils.isNotBlank(output[0]))
                {
                    generatedOneOrMoreDCX = true;
                    System.out.println("Completed sql generator. Writing files...");
                    String dcxName = dcx.getPartner() + "_" + dcx.getMarket();
                    Utilities.writeFiles(filePath, output, String.valueOf(i), Integer.parseInt(ticketNum), dcxName);
                }
                else
                {
                    System.out.println("Completed sql generator. No files to write. Process completed");
                }
            }
            if (generatedOneOrMoreDCX && insertDatabase)
            {
                System.out.println("Refreshing sale-purchase-bsv-v2");
//               TODO: confToolService.refreshEnvironment();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return excelFile.getName();
    }
    @RequestMapping
    (
        path = "/",
        method = RequestMethod.POST,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<DCXEntity> process
    (
            @RequestPart("excelFile") MultipartFile excelFile,
            @RequestParam(defaultValue = "356") String ticketNum,
            @RequestParam(defaultValue = "C:\\Users\\e107580\\Downloads") String filePath,
            @RequestParam(defaultValue = "true")boolean insertDatabase,
            @RequestParam(defaultValue = "true")boolean writeScripts
    )
    {
        List<DCXEntity> arrDCX = new ArrayList<>();

        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());

            List<DCXEntity> dcxFromExcel = getDCXFromExcel(workbook);
            boolean generatedOneOrMoreDCX = false;

            //Iterate for each dcx and perform database actions & write output files
            for (int i = 0; i < dcxFromExcel.size(); i++)
            {
                System.out.println("Executing SQL generator for dcx number " + i);
                DCXEntity dcx = dcxFromExcel.get(i);
                String[] output = sqlGenerator(dcx, insertDatabase, writeScripts);
                if(StringUtils.isNotBlank(output[0]))
                {
                    generatedOneOrMoreDCX = true;
                    System.out.println("Completed sql generator. Writing files...");
                    String dcxName = dcx.getPartner() + "_" + dcx.getMarket();
                    Utilities.writeFiles(filePath, output, String.valueOf(i), Integer.parseInt(ticketNum), dcxName);
                }
                else
                {
                    System.out.println("Completed sql generator. No files to write. Process completed");
                }
            }
            if (generatedOneOrMoreDCX && insertDatabase) {
                System.out.println("Refreshing sale-purchase-bsv-v2");
//               TODO: confToolService.refreshEnvironment();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        return generatedOneOrMoreDCX;
        return arrDCX;
    }
    public XSSFSheet dameSheet(XSSFWorkbook workbook , boolean verbose)
    {
        XSSFSheet rta = null;

        for(int i = 0; i < workbook.getNumberOfSheets(); i++)
        {
            XSSFSheet actual = workbook.getSheetAt(i);
            String nombreHojaBuscado = arrProps.get("WORKSHEET_NAME").toString();
            if(actual.getSheetName().equalsIgnoreCase(nombreHojaBuscado))
            {
                rta = workbook.getSheetAt(i);

                if(verbose)
                {
                    System.out.println("SHEET: " + actual.getSheetName());
                }

                break;

            }
        }

        return rta;
    }
    private List<DCXEntity> getDCXFromExcel(XSSFWorkbook workbook)
    {
        //Create DCX from Excel Data
        List<DCXEntity> dcxEntityList = new ArrayList<>();

        try
        {

            Properties prop = Utilities.loadProperties(PROPERTIES_PATH);

            System.out.println("Starting Excel Reader...");
            if(prop.isEmpty())
            {
                System.out.println("ERROR : PROPERTY EMPTY");
//                throw new ErrorLoadingPropertiesException();
            }

            //Select Worksheet
            XSSFSheet worksheet = dameSheet(workbook, true);

            if(worksheet == null)
            {
                String errorMsg = "ERROR: ";
                String worksheetName = arrProps.getProperty("WORKSHEET_NAME");
                errorMsg = errorMsg + worksheetName + " sheet not found on file!";
                System.out.println(errorMsg);

                System.out.println("ERROR: WRONG SHEET -> " + worksheetName);
//                throw new WrongTemplateException(prop.getProperty(WORKSHEET_NAME));
            }

            //Read data from excel
            int numDCX = Integer.parseInt(prop.getProperty("NUM_DCX"));
            List<List<Map.Entry<String, String>>> excelData = new ArrayList<>();
            int initialDataRow = Integer.parseInt(prop.getProperty("DATA_ROW"));

            for(int i = 0; i < numDCX; i++){
                int row = initialDataRow + i;
                List<Map.Entry<String, String>> data = readData(worksheet, row);
                if(data.isEmpty())
                    break;
                excelData.add(data);
            }


            for (List<Map.Entry<String, String>> excelDatum : excelData) {
                dcxEntityList.add(createDCXFromData(excelDatum));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return dcxEntityList;
    }

    private void saveDCX(Boolean insertDatabase, DCXEntity newDCX, DCXEntity databaseDCX) {
        if (insertDatabase) {
            if (databaseDCX != null) {
                dcxRepository.delete(databaseDCX);
            }
            dcxRepository.save(newDCX);
        } else {
            System.out.println("Database insertion deactivated.");
        }
    }

    public String[] sqlGenerator(DCXEntity dcx, Boolean insertDatabase, Boolean writeScripts) {

        String[] strings = null;
        String insertStrings = null;
        String rollbackStrings = null;
        List<DCXEntity> dcxEntityList = confToolServiceSearch.searchDCX(dcx);
        DCXEntity databaseDCX = null;
        try
        {
            if (!dcxEntityList.isEmpty())
            {
                System.out.println("DCX found in database.");
                databaseDCX = dcxEntityList.get(0);
                saveDCX(insertDatabase, dcx, databaseDCX);
                rollbackStrings = dcxQueryGeneratorService.generateRollbackQuery(dcx, databaseDCX);
            }
            else
            {

                System.out.println("DCX not found in database.");
                saveDCX(insertDatabase, dcx, databaseDCX);
                rollbackStrings = dcxQueryGeneratorService.generateDeleteQuery(dcx);
            }
            insertStrings = dcxQueryGeneratorService.generateInsertQuery(dcx, databaseDCX, true);
            strings = new String[]{insertStrings, rollbackStrings};

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //If script feature is deactivated files won't be written
        if (Boolean.FALSE.equals(writeScripts))
        {
            System.out.println("Scripts writing deactivated.");
            assert strings != null;
            strings[0] = "";
            strings[1] = "";
        }
        else
        {
            System.out.println("Writing scripts...");
        }
        return strings;
    }

    private List<Map.Entry<String, String>> readData(XSSFSheet worksheet, int row){
        System.out.println("Reading Excel Data...");
        List<Map.Entry<String, String>> excelData = new ArrayList<>();
        XSSFRow headersRow = worksheet.getRow(Integer.parseInt(arrProps.getProperty("HEADERS_ROW")));
        XSSFRow dataRow = worksheet.getRow(row);
        if(dataRow == null)
            return excelData;
        //Check how many columns to read
        int colCount = headersRow.getFirstCellNum();
        int headersEnd= headersRow.getLastCellNum();
        int dataEnd = dataRow.getLastCellNum();
        int endFile = Math.min(headersEnd, dataEnd);

        //Search the start of the data
        while(StringUtils.isBlank(headersRow.getCell(colCount).toString()) ||
                StringUtils.isBlank(dataRow.getCell(colCount).toString())){
            colCount++;
            if(colCount == endFile)
                break;
        }
        //Read each column and row
        for(int i = colCount; i < endFile; i++){
            String header;
            if(headersRow.getCell(i).getCellType() == CellType.FORMULA) {
                header = headersRow.getCell(i).getRawValue();
            }else{
                header = headersRow.getCell(i).toString();
            }
            if(StringUtils.isBlank(header)){
                break;
            }
            String data;
            if(dataRow.getCell(i).getCellType() == CellType.FORMULA){
                data = dataRow.getCell(i).getRawValue().trim();
            }else{
                data = dataRow.getCell(i).toString();
            }

            if(i == colCount && (StringUtils.isBlank(data) || data.equals("0") || data.equals("0.0"))){
                excelData.clear();
                return excelData;
            }
            Map.Entry<String, String> field = new AbstractMap.SimpleEntry<>(header, data);
            excelData.add(field);
        }
        if(excelData.isEmpty()) {
            String errorMesg = "ERROR: Failed to read data from excel at row " + (row + 1);
            System.out.println(errorMesg);
            return excelData;
        }
        return excelData;
    }
    private DCXEntity createDCXFromData(List<Map.Entry<String, String>> excelData){

        //Create Entities and populate them
        DCXEntity dcx = new DCXEntity();

        List<PaymentConfigEntity> payments = createPaymentConfig(excelData, dcx);
        PurchaseWorkflowConfigEntity workflowConfigEntity = createPurchaseWorkflow(excelData, dcx);

        //Populate DCX
        System.out.println("Populating DCX...");
        String partner = arrProps.getProperty("Partner");
        String market = arrProps.getProperty("Market");

        String salesChannel = arrProps.getProperty("SalesChannel");
        String lob = arrProps.getProperty("Lob");
        String flowType = arrProps.getProperty("FlowType");
        String touchpoint = arrProps.getProperty("Touchpoint");

        for(Map.Entry<String, String> field : excelData){
            if(field.getKey().equals(partner)) dcx.setPartner(field.getValue().trim());
            else if(field.getKey().equals(market)) dcx.setMarket(field.getValue().trim());
            else if(field.getKey().equals(salesChannel)) dcx.setSalesChannel(field.getValue().trim());
            else if(field.getKey().equals(lob)) dcx.setLob(field.getValue().trim());
            else if(field.getKey().equals(flowType)) dcx.setFlowType(field.getValue().toUpperCase().trim());
            else if(field.getKey().equals(touchpoint)){
                if(!(field.getValue().trim().equalsIgnoreCase("ALL") || field.getValue().equals("0")))
                    dcx.setTouchpoint(field.getValue().trim());
            }
        }
        //Add payment configs to dcx
        dcx.setWorkflowConfig(workflowConfigEntity);
        dcx.setPaymentConfig(payments);
        System.out.println("ExcelReader process finished!");
//        TODO:validateDCX(dcx);
        return dcx;
    }

    private List<PaymentConfigEntity> createPaymentConfig(List<Map.Entry<String, String>> excelData, DCXEntity dcx ){
        System.out.println("Setting Payment Configurations...");
        List<PaymentConfigEntity> paymentConfigList = new ArrayList<>();

        //Each DCX can have multiple PaymentConfigs or none
        //Load the headers and search the start of the payment configurations
        String paymentHeader1 = arrProps.getProperty("PaymentMethod1");
        String paymentHeader2 = arrProps.getProperty("PaymentMethod2");
        String paymentHeader3 = arrProps.getProperty("PaymentMethod3");
        String paymentHeader4 = arrProps.getProperty("PaymentMethod4");
        String tokenHeader = arrProps.getProperty("createToken");
        String cardAccountHeader = arrProps.getProperty("cardAccountName");
        String eWalletAccountHeader = arrProps.getProperty("eWalletAccountName");
        int paymentIndex = 0;
        for(int i = 0; i < excelData.size(); i++){
            Map.Entry<String, String> field = excelData.get(i);
            if(field.getKey().equals(paymentHeader1)) {
                paymentIndex = i;
                break;
            }
        }
        if(paymentIndex == 0){
            String error = "ERROR: Field " + paymentHeader1 + "not found in Template.";
            System.out.println(error);
//            throw new WrongTemplateException(paymentHeader1);
        }

        //Read and set all existing payment  for both types of offer
        for(int i = 0; i < 2; i++){
            String paymentMethod1 = "";
            String createToken = "";
            String cardsAccountName = "";
            String paymentMethod2 = "";
            String paymentMethod3 = "";
            String eWalletAccountName = "";
            String paymentMethod4 = "";

            if(excelData.get(paymentIndex).getKey().equals(paymentHeader1))
                paymentMethod1 = excelData.get(paymentIndex).getValue().trim();
            paymentIndex++;
            if(excelData.get(paymentIndex).getKey().equals(tokenHeader))
                createToken = excelData.get(paymentIndex).getValue();
            //We skip merchant field, since it's not used
            paymentIndex = paymentIndex + 2;
            if(excelData.get(paymentIndex).getKey().equals(cardAccountHeader))
                cardsAccountName = excelData.get(paymentIndex).getValue().trim();
            paymentIndex++;
            if(excelData.get(paymentIndex).getKey().equals(paymentHeader2))
                paymentMethod2 = excelData.get(paymentIndex).getValue().trim();
            paymentIndex++;
            if(excelData.get(paymentIndex).getKey().equals(paymentHeader3))
                paymentMethod3 = excelData.get(paymentIndex).getValue().trim();
            //We skip merchant field, since it's not used
            paymentIndex = paymentIndex + 2;
            if(excelData.get(paymentIndex).getKey().equals(eWalletAccountHeader))
                eWalletAccountName = excelData.get(paymentIndex).getValue().trim();
            paymentIndex++;
            if(excelData.get(paymentIndex).getKey().equals(paymentHeader4))
                paymentMethod4 = excelData.get(paymentIndex).getValue().trim();
            paymentIndex++;

            //Create PaymentConfigs Entites
            if(StringUtils.isNotBlank(paymentMethod1)){
                PaymentConfigEntity paymentConfig = setPaymentConfigEntity(cardsAccountName, paymentMethod1, dcx, i, paymentConfigList, createToken);
                if(paymentConfig != null)
                    paymentConfigList.add(paymentConfig);
            }
            if(StringUtils.isNotBlank(paymentMethod2)){
                PaymentConfigEntity paymentConfig = setPaymentConfigEntity(cardsAccountName, paymentMethod2, dcx, i, paymentConfigList, createToken);
                if(paymentConfig != null)
                    paymentConfigList.add(paymentConfig);
            }
            if(StringUtils.isNotBlank(paymentMethod3)){
                PaymentConfigEntity paymentConfig = setPaymentConfigEntity(eWalletAccountName, paymentMethod3, dcx, i, paymentConfigList, createToken);
                if(paymentConfig != null)
                    paymentConfigList.add(paymentConfig);
            }
            if(StringUtils.isNotBlank(paymentMethod4)){
                PaymentConfigEntity paymentConfig = setPaymentConfigEntity(eWalletAccountName, paymentMethod4, dcx, i, paymentConfigList, createToken);
                if(paymentConfig != null)
                    paymentConfigList.add(paymentConfig);
            }
        }

        String info = "Found and added " + paymentConfigList.size() + " payment configurations";
        System.out.println(info);
        return paymentConfigList;
    }

    private PaymentConfigEntity setPaymentConfigEntity(String accountName, String paymentMethod,
                                                       DCXEntity dcx, int offerType,
                                                       List<PaymentConfigEntity> paymentConfigList, String createToken) {
        PaymentConfigEntity paymentConfig = new PaymentConfigEntity();
        if(StringUtils.isBlank(accountName) || accountName.equals("0")){
            System.out.println("Error seting Payment Config. Field accountName is blank");
            return null;
        }
        if(StringUtils.isBlank(paymentMethod) || paymentMethod.equals("0")){
            System.out.println("Error seting Payment Config. Field paymentMethod is blank");
            return null;
        }
        paymentConfig.setAccountName(accountName);
        paymentConfig.setPaymentMethod(paymentMethod);
        paymentConfig.setPaymentPlatform(arrProps.getProperty("dtype"));
        paymentConfig.setDcx(dcx);
        boolean exists = false;
        if(StringUtils.isNotBlank(createToken)){
            if(Integer.parseInt(createToken) == 0)
                paymentConfig.setCreateToken(false);
            if(Integer.parseInt(createToken) == 1)
                paymentConfig.setCreateToken(true);
        }else{
            paymentConfig.setCreateToken(false);
        }
        //offer type: -1 for All, 0 for short terms/fixed terms, 1 for Annual
        if(offerType == 0)
            paymentConfig.setOfferType(offerType);
        //Check if paymentConfig exists for offerType 0 and if it exists means must be All (-1)
        if(offerType == 1){
            for(PaymentConfigEntity payment : paymentConfigList){
                if(payment.getAccountName().equals(paymentConfig.getAccountName()) &&
                        payment.getPaymentMethod().equals(paymentConfig.getPaymentMethod()) &&
                        payment.getCreateToken().equals(paymentConfig.getCreateToken())){
                    payment.setOfferType(-1);
                    exists = true;
                    break;
                }
            }
            if(!exists)
                paymentConfig.setOfferType(offerType);
        }

        if(exists)
            paymentConfig = null;
        return paymentConfig;
    }
    private PurchaseWorkflowConfigEntity createPurchaseWorkflow(List<Map.Entry<String, String>> excelData, DCXEntity dcx ){
        System.out.println("Setting Purchase Workflow Configuration...");
        PurchaseWorkflowConfigEntity purchaseWorkflowConfig = new PurchaseWorkflowConfigEntity();
        String flowType = "";
        String confirmationEmail = "";
        String exportEngine = "";
        String companyCode = "";
        String foreignCompanyCode = "";
        String serviceEntity = "";
        String channelId = "";
        String modificationChannelId = "";
        String documentRefId="";
        String quotationEngine="";

        for (Map.Entry<String, String> field : excelData) {
            if (field.getKey().equals(arrProps.getProperty("FlowType"))) flowType = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("messageClient"))) confirmationEmail = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("exportEngine"))) exportEngine = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("CompanyCode"))) companyCode = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("ForeignCompanyCode"))) foreignCompanyCode = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("ServiceEntity"))) serviceEntity = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("ChannelId"))) channelId = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("ModificationChannelId"))) modificationChannelId = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("DocumentRefId"))) documentRefId = field.getValue().trim();
            if (field.getKey().equals(arrProps.getProperty("QuotationEngine"))) quotationEngine = field.getValue().trim();
        }
        if (StringUtils.isNotBlank(quotationEngine)) {
            purchaseWorkflowConfig.setQuotationEngine(quotationEngine);
        } else {
            if (flowType.trim().equalsIgnoreCase("HARMONIZED"))
                purchaseWorkflowConfig.setQuotationEngine(arrProps.getProperty("Harmonized_quotationEngine"));
            if (flowType.trim().equalsIgnoreCase("BS"))
                purchaseWorkflowConfig.setQuotationEngine(arrProps.getProperty("BS_quotationEngine"));
        }
        if (confirmationEmail.trim().equalsIgnoreCase("Y"))
            purchaseWorkflowConfig.setMessageClient(arrProps.getProperty("messageClient_Value"));
        if (StringUtils.isBlank(exportEngine) || exportEngine.equals("0") || exportEngine.equals("0.0")){
            purchaseWorkflowConfig.setExportengine("exports_v2");
        }else {
            purchaseWorkflowConfig.setExportengine(exportEngine);
        }
        if (!(StringUtils.isBlank(companyCode) || companyCode.equals("0") || companyCode.equals("0.0")))
            purchaseWorkflowConfig.setCompanyCode(companyCode);
        if (!(StringUtils.isBlank(foreignCompanyCode) || foreignCompanyCode.equals("0") || foreignCompanyCode.equals("0.0")))
            purchaseWorkflowConfig.setForeignCompanyCode(foreignCompanyCode);
        if (!(StringUtils.isBlank(serviceEntity) || serviceEntity.equals("0") || serviceEntity.equals("0.0")))
            purchaseWorkflowConfig.setServiceEntity(serviceEntity);
        DocumentEntity doc = new DocumentEntity();

        if(StringUtils.isNotBlank(documentRefId)){
            Long documentRefIdValue = (long) Double.parseDouble(documentRefId.trim());
            if(documentRefIdValue == 1 || documentRefIdValue == 2){
                doc.setId(documentRefIdValue);
                purchaseWorkflowConfig.setDocument(doc);
            }else if(documentRefIdValue != 0){
                System.out.println("Invalid value " + documentRefId + " for documentRedId read from excel, setting default value");
                doc.setId(1L);
                purchaseWorkflowConfig.setDocument(doc);
            }
        }
        purchaseWorkflowConfig.setValidation(false);
        // SALES-3469: setup for enrichment flag to true on harmonized flow type
        purchaseWorkflowConfig.setDataEnrichment(flowType.trim().equalsIgnoreCase("HARMONIZED"));
        purchaseWorkflowConfig.setFreeOfPremium(false);
        ChannelEntity channel = new ChannelEntity();
        try {
            float channelNum = Float.parseFloat(channelId);
            channel.setId(Math.round(channelNum));
        }catch (NumberFormatException e){
            System.out.println("ERROR: Wrong channel Id");
            System.out.println(e.getMessage());
//            throw  new WrongTemplateException(channelId);
        }
        purchaseWorkflowConfig.setChannel(channel);

        if(StringUtils.isNotBlank(modificationChannelId)) {
            ChannelEntity channelModification = new ChannelEntity();
            try {
                float channelModificationNum = Float.parseFloat(modificationChannelId);
                channelModification.setId(Math.round(channelModificationNum));
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Wrong modification channel Id");
                System.out.println(e.getMessage());
//                throw new WrongTemplateException(modificationChannelId);
            }
            purchaseWorkflowConfig.setModificationChannel(channelModification);
        }
        purchaseWorkflowConfig.setDcx(dcx);

        return purchaseWorkflowConfig;
    }


}
