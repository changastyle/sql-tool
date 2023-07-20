package com.allianz.sqltool.service;

import com.allianz.sqltool.model.PurchaseWorkflowConfigEntity;
import com.allianz.sqltool.Utilities;
import com.allianz.sqltool.model.DCXEntity;
import com.allianz.sqltool.model.PaymentConfigEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.sql.Insert;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DCXQueryGeneratorService {

    private static final Logger LOG = LoggerFactory.getLogger(DCXQueryGeneratorService.class);

    /**
     * Generates a Conditional Query from a DCXEntity and also the corresponding rollback query
     * <p>
     * params:  DCXEntity
     * returns: String
     */

    public String generateInsertQuery(DCXEntity newDCX, DCXEntity databaseDCX, boolean includeDelete) throws Exception {

        String[] select = Utilities.buildDCXSELECT(newDCX); //Generate the dcx SELECT id Sub Query
        StringBuilder insertQuery = new StringBuilder();

        // Delete all previous configs and insert new ones if includeDelete is set to true
        if(includeDelete) {
            if (databaseDCX != null) {
                insertQuery.append(generateDeleteQuery(databaseDCX));
            } else {
                insertQuery.append(generateDeleteQuery(newDCX));
            }
        }

        insertQuery.append(buildDCXINSERT(newDCX)).append(';').append("\n");
        insertQuery.append(buildPurchaseWorkflowConfigINSERT(newDCX.getWorkflowConfig(), select[0])).append(';').append("\n");

        for (PaymentConfigEntity pc : newDCX.getPaymentConfig()) {
            insertQuery.append(buildPaymentConfigINSERT(pc, select[0])).append(';').append("\n");
        }
        return insertQuery.toString();
    }

    public String generateDeleteQuery(DCXEntity dcxInput) {

        String[] select = Utilities.buildDCXSELECT(dcxInput); //Generate the dcx SELECT id Sub Query
        StringBuilder deleteQueries = new StringBuilder();

        //Delete queries has to be created in inverse order
        //Delete statement for payment config only needed if the dcx has some payment
        deleteQueries.append("DELETE FROM payment_config WHERE dcx = " + select[0] + ";\n");
        deleteQueries.append("DELETE FROM purchase_workflow_config WHERE dcx = " + select[0] + ";\n");
        deleteQueries.append("DELETE FROM dcx WHERE " + select[1] + ";\n");
        return deleteQueries.toString();
    }

    private String buildDCXINSERT(DCXEntity dcx) throws Exception {
        //Populate dcx INSERT QUERY
        Insert insertDCX = new Insert(new MySQLDialect());
        insertDCX.setTableName("dcx");
        insertDCX.addColumn("partner", dcx.getPartner(), new StringType())
                .addColumn("market", dcx.getMarket(), new StringType())
                .addColumn("salesChannel", dcx.getSalesChannel(), new StringType())
                .addColumn("lob", dcx.getLob(), new StringType())
                .addColumn("flowType", dcx.getFlowType(), new StringType())
                .addColumn("locked", dcx.isLocked(), new BooleanType());
        if (StringUtils.isNotBlank(dcx.getTouchpoint()))
            insertDCX.addColumn("touchpoint", dcx.getTouchpoint(), new StringType());

        return insertDCX.toStatementString();
    }

    private String buildPurchaseWorkflowConfigINSERT(PurchaseWorkflowConfigEntity pwc, String dcxSELECT) throws Exception {
        //Populate pwc INSERT QUERY
        Insert insertPWC = new Insert(new MySQLDialect());
        insertPWC.setTableName("purchase_workflow_config");
        insertPWC.addColumn("quotationEngine", pwc.getQuotationEngine(), new StringType());
        if (StringUtils.isNotBlank(pwc.getMessageClient()))
            insertPWC.addColumn("messageClient", pwc.getMessageClient(), new StringType());
        insertPWC.addColumn("exportEngine", pwc.getExportengine(), new StringType())
                .addColumn("channel_id", pwc.getChannel().getId(), new LongType())
                .addColumn("validation", pwc.getValidation(), new BooleanType())
                .addColumn("dcx", dcxSELECT)
                .addColumn("dataEnrichment", pwc.getDataEnrichment(), new BooleanType());
        if (StringUtils.isNotBlank(pwc.getCompanyCode()))
            insertPWC.addColumn("companyCode", pwc.getCompanyCode(), new StringType());
        if (StringUtils.isNotBlank(pwc.getForeignCompanyCode()))
            insertPWC.addColumn("foreignCompanyCode", pwc.getForeignCompanyCode(), new StringType());
        if (StringUtils.isNotBlank(pwc.getServiceEntity()))
            insertPWC.addColumn("serviceEntity", pwc.getServiceEntity(), new StringType());
        if (pwc.getDocument() != null)
            insertPWC.addColumn("documentRefId", pwc.getDocument().getId(), new LongType());
        if (pwc.getModificationChannel() != null &&
                (pwc.getModificationChannel().getId() == 9 || pwc.getModificationChannel().getId() == 10)) {
            insertPWC.addColumn("modif_channel_id", pwc.getModificationChannel().getId(), new LongType());
        }
        return insertPWC.toStatementString();
    }

    public String buildPaymentConfigINSERT(PaymentConfigEntity pc, String dcxSELECT) throws Exception {
        Insert insertPC = new Insert(new MySQLDialect());
        insertPC.setTableName("payment_config");
        insertPC.addColumn("accountName", pc.getAccountName(), new StringType())
                .addColumn("offerType", pc.getOfferType(), new IntegerType())
                .addColumn("paymentMethod", pc.getPaymentMethod(), new StringType())
                .addColumn("dcx", dcxSELECT)
                .addColumn("createToken", pc.getCreateToken(), new BooleanType())
                .addColumn("dtype", "onepay", new StringType())
                .addColumn("offerType", pc.getOfferType(), new IntegerType());
        return insertPC.toStatementString();
    }

    /**
     * Generates the rollback query for the sql script
     * <p>
     * params:  newDCXEntity, databaseDCXEntity
     * returns: String
     */
    public String generateRollbackQuery(DCXEntity newDCX, DCXEntity databaseDCX) throws Exception {
        // Exactly the reverse process than insert.
        return generateInsertQuery(databaseDCX, newDCX, true);
    }


}