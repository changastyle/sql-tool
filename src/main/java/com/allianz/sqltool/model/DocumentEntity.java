package com.allianz.sqltool.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "documents")
@Entity
public class DocumentEntity {
    
    @Id
    private Long id;

    @NotNull
    private String docUpload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocUpload() {
        return docUpload;
    }

    public void setDocUpload(String docUpload) {
        this.docUpload = docUpload;
    }
}
