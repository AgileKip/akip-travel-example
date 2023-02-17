package com.mycompany.myapp.domain;

import br.com.loginlogistica.domain.enumeration.StatusProcessDefinition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

/**
 * A ProcessDefinition.
 */
@Entity
@Table(name = "process_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcessDefinition status;

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

    @Column(name = "camunda_process_definition_id")
    private String camundaProcessDefinitionId;

    @NaturalId
    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    @Column(name = "can_be_manually_started")
    private Boolean canBeManuallyStarted;

    @OneToMany(mappedBy = "processDefinition")
    private List<ProcessDeployment> processDeployments = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public StatusProcessDefinition getStatus() {
        return status;
    }

    public void setStatus(StatusProcessDefinition status) {
        this.status = status;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public Boolean getCanBeManuallyStarted() {
        return canBeManuallyStarted;
    }

    public void setCanBeManuallyStarted(Boolean canBeManuallyStarted) {
        this.canBeManuallyStarted = canBeManuallyStarted;
    }

    public List<ProcessDeployment> getProcessDeployments() {
        return processDeployments;
    }

    public void setProcessDeployments(List<ProcessDeployment> processDeployments) {
        this.processDeployments = processDeployments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinition)) {
            return false;
        }
        return id != null && id.equals(((ProcessDefinition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            "}";
    }
}
