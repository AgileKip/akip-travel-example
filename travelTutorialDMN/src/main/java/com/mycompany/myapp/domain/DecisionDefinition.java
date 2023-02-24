package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "decision_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DecisionDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "specification_file")
    private byte[] specificationFile;

    @Column(name = "specification_file_c_type")
    private String specificationFileContentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "camunda_deployment_message")
    private String camundaDeploymentMessage;

    @Column(name = "camunda_deployment_id")
    private String camundaDeploymentId;

    @Column(name = "camunda_decision_definition_id")
    private String camundaDecisionDefinitionId;

    @NaturalId
    @Column(name = "dmn_decision_definition_id")
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
        if (!(o instanceof DecisionDefinition)) {
            return false;
        }
        return id != null && id.equals(((DecisionDefinition) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
