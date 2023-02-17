package com.mycompany.myapp.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.akip.domain.enumeration.StatusProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long> {
    Optional<ProcessInstance> findByCamundaProcessInstanceId(String camundaProcessInstanceId);

    List<ProcessInstance> findByProcessDefinitionId(Long processDefinitionId);

    @Modifying
    @Query("update ProcessInstance set data = ?1 where id = ?2")
    void updateDataById(String dataAsString, Long id);

    @Modifying
    @Query("update ProcessInstance set tokenAcessoValidade = ?1, tokenAcessoNumero = ?2 where id = ?3")
    int updateTokenAcessoValidadeAndTokenAcessoNumeroById(LocalDate tokenAcessoValidade, String tokenAcessoNumero, Long id);

    Optional<ProcessInstance> findByIdAndTokenAcessoNumero(Long id, String tokenAcessoNumero);

    List<ProcessInstance> findByProcessDefinitionIdAndStartDateBetween(Long processDefinitionId, LocalDateTime from, LocalDateTime to);

    List<ProcessInstance> findByStatus(StatusProcessInstance status);
}
