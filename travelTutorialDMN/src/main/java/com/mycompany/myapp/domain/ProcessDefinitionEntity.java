package com.mycompany.myapp.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProcessDefinitionEntity.
 */
@Entity
@Table(name = "process_definition_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    private Long id;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "entity_name")
    private String entityName;

    @ManyToOne
    @JoinColumn(name = "bpmn_process_definition_id", referencedColumnName = "bpmn_process_definition_id")
    private ProcessDefinition processDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinitionEntity)) {
            return false;
        }
        return id != null && id.equals(((ProcessDefinitionEntity) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "ProcessDefinitionEntity {" + "id=" + getId() + ", entityId=" + getEntityId() + ", entityName='" + getEntityName() + "'" + "}"
        );
    }
}
