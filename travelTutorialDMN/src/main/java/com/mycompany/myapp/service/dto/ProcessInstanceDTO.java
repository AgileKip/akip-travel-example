package com.mycompany.myapp.service.dto;

import br.com.loginlogistica.domain.enumeration.StatusProcessInstance;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;

/**
 * A DTO for the {@link br.com.loginlogistica.domain.ProcessInstance} entity.
 */
public class ProcessInstanceDTO implements Serializable {

    private static final long serialVersionUID = 659025866125958534L;

    private Long id;

    private String businessKey;

    private String camundaDeploymentId;

    private String camundaProcessDefinitionId;

    private String camundaProcessInstanceId;

    private String camundaProcessVariables;

    private Map<String, String> props;

    private Map<String, String> data = new HashMap<>();

    private TenantDTO tenant;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private StatusProcessInstance status;

    private ProcessDefinitionDTO processDefinition;

    private UserDTO user;

    private String tokenAcessoNumero;

    private LocalDate tokenAcessoValidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaProcessDefinitionId() {
        return camundaProcessDefinitionId;
    }

    public void setCamundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
    }

    public String getCamundaProcessInstanceId() {
        return camundaProcessInstanceId;
    }

    public void setCamundaProcessInstanceId(String camundaProcessInstanceId) {
        this.camundaProcessInstanceId = camundaProcessInstanceId;
    }

    public String getCamundaProcessVariables() {
        return camundaProcessVariables;
    }

    public void setCamundaProcessVariables(String camundaProcessVariables) {
        this.camundaProcessVariables = camundaProcessVariables;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public StatusProcessInstance getStatus() {
        return status;
    }

    public void setStatus(StatusProcessInstance status) {
        this.status = status;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getTokenAcessoNumero() {
        return tokenAcessoNumero;
    }

    public void setTokenAcessoNumero(String tokenAcessoNumero) {
        this.tokenAcessoNumero = tokenAcessoNumero;
    }

    public LocalDate getTokenAcessoValidade() {
        return tokenAcessoValidade;
    }

    public void setTokenAcessoValidade(LocalDate tokenAcessoValidade) {
        this.tokenAcessoValidade = tokenAcessoValidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstanceDTO)) {
            return false;
        }

        ProcessInstanceDTO processInstanceDTO = (ProcessInstanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processInstanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceDTO{" +
            "id=" + getId() +
            ", businessKey='" + getBusinessKey() + "'" +
            ", camundaDeploymentId='" + getCamundaDeploymentId() + "'" +
            ", camundaProcessDefinitionId='" + getCamundaProcessDefinitionId() + "'" +
            ", camundaProcessInstanceId='" + getCamundaProcessInstanceId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
