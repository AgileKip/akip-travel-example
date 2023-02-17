package com.mycompany.myapp.service;

import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.myapp.repository.ProcessDeploymentRepository;
import com.mycompany.myapp.repository.ProcessInstanceRepository;
import com.mycompany.myapp.repository.UserRepository;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcessInstance}.
 */
@Service
@Transactional
public class ProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceService.class);

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessInstanceMapper processInstanceMapper;

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentService processDeploymentService;

    private final TaskInstanceService taskInstanceService;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final UserRepository userRepository;

    private final RuntimeService runtimeService;

    private final TenantMapper tenantMapper;

    public ProcessInstanceService(
        ProcessInstanceRepository processInstanceRepository,
        ProcessInstanceMapper processInstanceMapper,
        ProcessDefinitionService processDefinitionService,
        ProcessDeploymentService processDeploymentService,
        TaskInstanceService taskInstanceService,
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDeploymentRepository processDeploymentRepository,
        UserRepository userRepository,
        RuntimeService runtimeService,
        TenantMapper tenantMapper
    ) {
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentService = processDeploymentService;
        this.taskInstanceService = taskInstanceService;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDeploymentRepository = processDeploymentRepository;
        this.userRepository = userRepository;
        this.runtimeService = runtimeService;
        this.tenantMapper = tenantMapper;
    }

    public ProcessInstanceDTO create(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        if (processInstanceDTO.getTenant() == null) {
            return createWithoutTenant(processInstanceDTO);
        }
        return createWithTenant(processInstanceDTO);
    }

    private ProcessInstanceDTO createWithTenant(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        ProcessInstance processInstance = processInstanceMapper.toEntity(processInstanceDTO);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findById(processInstanceDTO.getProcessDefinition().getId())
            .orElseThrow();
        processInstance.setProcessDefinition(processDefinition);

        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantId(processDefinition.getId(), processInstance.getTenant().getId())
            .orElseThrow();

        processInstance.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstanceMapper.toDto(processInstance));

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(processInstance.getBusinessKey())
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceMapper.toDto(processInstanceRepository.save(processInstance));
    }

    private ProcessInstanceDTO createWithoutTenant(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        ProcessDefinition processDefinition = processDefinitionRepository
            .findById(processInstanceDTO.getProcessDefinition().getId())
            .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
            .orElseThrow();

        ProcessInstance processInstance = processInstanceMapper.toEntity(processInstanceDTO);
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstanceMapper.toDto(processInstance));

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(processInstance.getBusinessKey())
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceMapper.toDto(processInstanceRepository.save(processInstance));
    }

    public ProcessInstance create(String bpmnProcessDefinitionId, String businessKey, IProcessEntity processEntity, Tenant tenant) {
        if (tenant == null && SecurityUtils.getCurrentUserLogin().get().equals("anonymousUser")) {
            return createWithoutTenantAndWithoutUser(bpmnProcessDefinitionId, businessKey, processEntity);
        }

        if (tenant == null) {
            return createWithoutTenant(bpmnProcessDefinitionId, businessKey, processEntity);
        }

        if (SecurityUtils.getCurrentUserLogin().get().equals("anonymousUser")) {
            return createWithoutUser(bpmnProcessDefinitionId, businessKey, processEntity, tenant);
        }

        return createWithTenant(bpmnProcessDefinitionId, businessKey, processEntity, tenant);
    }

    private ProcessInstance createWithoutTenant(String bpmnProcessDefinitionId, String businessKey, IProcessEntity processEntity) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
            .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
            .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceRepository.save(processInstance);
    }

    private ProcessInstance createWithoutTenantAndWithoutUser(
        String bpmnProcessDefinitionId,
        String businessKey,
        IProcessEntity processEntity
    ) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
            .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
            .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceRepository.save(processInstance);
    }

    private ProcessInstance createWithTenant(
        String bpmnProcessDefinitionId,
        String businessKey,
        IProcessEntity processEntity,
        Tenant tenant
    ) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
            .orElseThrow();

        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantId(processDefinition.getId(), tenant.getId())
            .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow());
        processInstance.setTenant(tenant);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceRepository.save(processInstance);
    }

    private ProcessInstance createWithoutUser(
        String bpmnProcessDefinitionId,
        String businessKey,
        IProcessEntity processEntity,
        Tenant tenant
    ) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
            .orElseThrow();

        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantId(processDefinition.getId(), tenant.getId())
            .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setTenant(tenant);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(DateUtils.getLocalDateTimeBrt());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceRepository.save(processInstance);
    }

    /**
     * Get all the processInstances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceDTO> findAll() {
        log.debug("Request to get all ProcessInstances");
        return processInstanceRepository
            .findAll()
            .stream()
            .map(processInstanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceDTO> findOne(Long id) {
        log.debug("Request to get ProcessInstance : {}", id);
        return processInstanceRepository.findById(id).map(processInstanceMapper::toDto);
    }

    public Optional<ProcessInstanceBpmnModelDTO> findBpmnModel(Long id) {
        ProcessInstanceDTO processInstance = findOne(id).orElseThrow();
        ProcessInstanceBpmnModelDTO processInstanceBpmnModel = new ProcessInstanceBpmnModelDTO();

        ProcessDeployment processDeployment = processDeploymentRepository
            .findByCamundaProcessDefinitionId(processInstance.getCamundaProcessDefinitionId())
            .orElseThrow();
        processInstanceBpmnModel.setProcessDeploymentBpmnModel(processDeploymentService.findBpmnModel(processDeployment.getId()).get());

        List<TaskInstanceDTO> processInstanceTasks = taskInstanceService.findByProcessInstance(id);

        processInstanceBpmnModel.setRunningTasksDefinitionKeys(
            processInstanceTasks
                .stream()
                .filter(
                    taskInstanceDTO ->
                        taskInstanceDTO.getStatus() == StatusTaskInstance.NEW || taskInstanceDTO.getStatus() == StatusTaskInstance.ASSIGNED
                )
                .map(TaskInstanceDTO::getTaskDefinitionKey)
                .collect(Collectors.toList())
        );

        processInstanceBpmnModel.setCompletedTasksDefinitionKeys(
            processInstanceTasks
                .stream()
                .filter(taskInstanceDTO -> taskInstanceDTO.getStatus() == StatusTaskInstance.COMPLETED)
                .map(TaskInstanceDTO::getTaskDefinitionKey)
                .collect(Collectors.toList())
        );

        return Optional.of(processInstanceBpmnModel);
    }

    public List<ProcessInstanceDTO> findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processInstanceRepository
            .findByProcessDefinitionId(processDefinitionDTO.getId())
            .stream()
            .map(processInstanceMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<TenantDTO> getCandidateTenantsCurrentUser(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinition = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        Set<TenantDTO> processDefinitionTenants = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNotNull(processDefinition.getId())
            .stream()
            .map(ProcessDeployment::getTenant)
            .map(tenantMapper::toDto)
            .collect(Collectors.toSet());

        Set<TenantDTO> userTenants = userRepository
            .findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get())
            .orElseThrow()
            .getTenants()
            .stream()
            .map(tenantMapper::toDto)
            .collect(Collectors.toSet());

        Set<TenantDTO> candidateTenants = new HashSet<>();
        candidateTenants.addAll(processDefinitionTenants);
        candidateTenants.retainAll(userTenants);
        return candidateTenants.stream().sorted(Comparator.comparing(TenantDTO::getName)).collect(Collectors.toList());
    }
}
