package com.allianz.sqltool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "purchase_workflow_config",
uniqueConstraints = {
        @UniqueConstraint(name = "pwc_unik", columnNames = {
                "dcx"
        })
}
)
@Entity
public class PurchaseWorkflowConfigEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String quotationEngine;
    
    private String messageClient;
    
    @NotNull
    private String exportengine;
    
    private String companyCode;
    
    private String foreignCompanyCode;
    
    private String serviceEntity;

    private Boolean freeOfPremium;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(name = "FK_CHANNEL"))
    private ChannelEntity channel;
    
    private Boolean validation;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documentRefId", nullable = true, foreignKey = @ForeignKey(name = "FK_documents"))
    private DocumentEntity document;
    
    @NotNull
    @OneToOne
    @JoinColumn(name = "dcx", foreignKey = @ForeignKey(name = "FK_DCX"), unique = true)
    @JsonBackReference
    private DCXEntity dcx;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modif_channel_id", nullable = true, foreignKey = @ForeignKey(name = "FK_MODIF_CHANNEL"))
    private ChannelEntity modificationChannel;
    
    @Column(columnDefinition = "bit(1) DEFAULT 0")
    private Boolean dataEnrichment;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the quotationEngine
     */
    public String getQuotationEngine() {
        return quotationEngine;
    }

    /**
     * @param quotationEngine the quotationEngine to set
     */
    public void setQuotationEngine(String quotationEngine) {
        this.quotationEngine = quotationEngine;
    }

    /**
     * @return the messageClient
     */
    public String getMessageClient() {
        return messageClient;
    }

    /**
     * @param messageClient the messageClient to set
     */
    public void setMessageClient(String messageClient) {
        this.messageClient = messageClient;
    }

    /**
     * @return the exportengine
     */
    public String getExportengine() {
        return exportengine;
    }

    /**
     * @param exportengine the exportengine to set
     */
    public void setExportengine(String exportengine) {
        this.exportengine = exportengine;
    }

    /**
     * @return the companyCode
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * @param companyCode the companyCode to set
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @return the foreignCompanyCode
     */
    public String getForeignCompanyCode() {
        return foreignCompanyCode;
    }

    /**
     * @param foreignCompanyCode the foreignCompanyCode to set
     */
    public void setForeignCompanyCode(String foreignCompanyCode) {
        this.foreignCompanyCode = foreignCompanyCode;
    }

    /**
     * @return the serviceEntity
     */
    public String getServiceEntity() {
        return serviceEntity;
    }

    /**
     * @param serviceEntity the serviceEntity to set
     */
    public void setServiceEntity(String serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    /**
     * @return the channel
     */
    public ChannelEntity getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(ChannelEntity channel) {
        this.channel = channel;
    }

    /**
     * @return the validation
     */
    public Boolean getValidation() {
        return validation;
    }

    /**
     * @param validation the validation to set
     */
    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    /**
     * @return the document
     */
    public DocumentEntity getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(DocumentEntity document) {
        this.document = document;
    }

    /**
     * @return the dcx
     */
    public DCXEntity getDcx() {
        return dcx;
    }

    /**
     * @param dcx the dcx to set
     */
    public void setDcx(DCXEntity dcx) {
        this.dcx = dcx;
    }

    /**
     * @return the modificationChannel
     */
    public ChannelEntity getModificationChannel() {
        return modificationChannel;
    }

    /**
     * @param modificationChannel the modificationChannel to set
     */
    public void setModificationChannel(ChannelEntity modificationChannel) {
        this.modificationChannel = modificationChannel;
    }

    /**
     * @return the dataEnrichment
     */
    public Boolean getDataEnrichment() {
        return dataEnrichment;
    }

    /**
     * @param dataEnrichment the dataEnrichment to set
     */
    public void setDataEnrichment(Boolean dataEnrichment) {
        this.dataEnrichment = dataEnrichment;
    }

    /**
     * @return the freeOfPremium
     */
    public Boolean getFreeOfPremium() { return freeOfPremium; }

    /**
     * @param freeOfPremium the freeOfPremium to set
     */
    public void setFreeOfPremium(Boolean freeOfPremium) { this.freeOfPremium = freeOfPremium; }

    
}
