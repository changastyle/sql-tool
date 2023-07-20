package com.allianz.sqltool.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "payment_config",
uniqueConstraints = {
        @UniqueConstraint(name = "acc_ot_pm", columnNames = {
                "dcx", "offerType", "paymentMethod"
        })
}
)
@Entity
public class PaymentConfigEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @NotNull
    private String accountName;
    
    @Column(columnDefinition = "INT(3) default -1")
    private Integer offerType;
    
    @NotBlank
    @NotNull
    private String paymentMethod;
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "dcx", foreignKey = @ForeignKey(name = "FK_PAY_DCX"))
    @JsonBackReference
    private DCXEntity dcx;
    
    private Boolean createToken = false;
    
    @Column(name = "dtype")
    private String paymentPlatform = "onepay";

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
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the offerType
     */
    public Integer getOfferType() {
        return offerType;
    }

    /**
     * @param offerType the offerType to set
     */
    public void setOfferType(Integer offerType) {
        this.offerType = offerType;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
     * @return the createToken
     */
    public Boolean getCreateToken() {
        return createToken;
    }

    /**
     * @param createToken the createToken to set
     */
    public void setCreateToken(Boolean createToken) {
        this.createToken = createToken;
    }

    /**
     * @return the paymentPlatform
     */
    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    /**
     * @param paymentPlatform the paymentPlatform to set
     */
    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

}
