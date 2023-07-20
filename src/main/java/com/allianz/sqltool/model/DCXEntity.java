package com.allianz.sqltool.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dcx",
        uniqueConstraints = {
                @UniqueConstraint(name = "par_mark_sale_touch", columnNames = {
                        "partner", "market", "salesChannel", "lob", "flowType"
                })
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DCXEntity
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @NotNull
        private String partner;

        @NotBlank
        @NotNull
        private String market;

        @NotBlank
        @NotNull
        private String salesChannel;

        @NotBlank
        @NotNull
        private String lob;

        @NotNull
        private String flowType;

        private String touchpoint;

        private boolean locked;

        @OneToOne(mappedBy = "dcx",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )

        private PurchaseWorkflowConfigEntity workflowConfig;

        @OneToMany(mappedBy = "dcx",
                cascade = CascadeType.ALL,
                orphanRemoval = true,
                fetch = FetchType.EAGER
        )

        private List<PaymentConfigEntity> paymentConfig = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public String getSalesChannel() {
            return salesChannel;
        }

        public void setSalesChannel(String salesChannel) {
            this.salesChannel = salesChannel;
        }

        public String getLob() {
            return lob;
        }

        public void setLob(String lob) {
            this.lob = lob;
        }

        public String getFlowType() {
            return flowType;
        }

        public void setFlowType(String flowType) {
            this.flowType = flowType;
        }

        public String getTouchpoint() {
            return touchpoint;
        }

        public void setTouchpoint(String touchpoint) {
            this.touchpoint = touchpoint;
        }

        public PurchaseWorkflowConfigEntity getWorkflowConfig() {
            return workflowConfig;
        }

        public void setWorkflowConfig(PurchaseWorkflowConfigEntity workflowConfig) {
            this.workflowConfig = workflowConfig;
        }

        public List<PaymentConfigEntity> getPaymentConfig() {
            return paymentConfig;
        }

        public void setPaymentConfig(List<PaymentConfigEntity> paymentConfig) {
            this.paymentConfig = paymentConfig;
        }

        public Boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }


}
