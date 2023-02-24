package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

public class DecisionDefinitionDTO implements Serializable {

    private static final long serialVersionUID = -1213556574365030378L;

    private Long id;

    private String name;

    private String description;

    private byte[] specificationFile;

    private String specificationFileContentType;

    private String camundaDeploymentMessage;

    private String camundaDeploymentId;

    private String camundaDecisionDefinitionId;

    private String dmnDecisionDefinitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getSpecificationFile() {
        return specificationFile;
    }

    public void setSpecificationFile(byte[] specificationFile) {
        this.specificationFile = specificationFile;
    }

    public String getSpecificationFileContentType() {
        return specificationFileContentType;
    }

    public void setSpecificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
    }

    public String getCamundaDeploymentMessage() {
        return camundaDeploymentMessage;
    }

    public void setCamundaDeploymentMessage(String camundaDeploymentMessage) {
        this.camundaDeploymentMessage = camundaDeploymentMessage;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaDecisionDefinitionId() {
        return camundaDecisionDefinitionId;
    }

    public void setCamundaDecisionDefinitionId(String camundaDecisionDefinitionId) {
        this.camundaDecisionDefinitionId = camundaDecisionDefinitionId;
    }

    public String getDmnDecisionDefinitionId() {
        return dmnDecisionDefinitionId;
    }

    public void setDmnDecisionDefinitionId(String dmnDecisionDefinitionId) {
        this.dmnDecisionDefinitionId = dmnDecisionDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecisionDefinitionDTO)) {
            return false;
        }

        DecisionDefinitionDTO decisionDefinitionDTO = (DecisionDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, decisionDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
