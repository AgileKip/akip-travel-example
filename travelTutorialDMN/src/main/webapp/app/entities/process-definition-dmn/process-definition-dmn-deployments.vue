<template>
  <div>
    <div class="d-flex justify-content-between">
      <h2 id="page-heading" data-cy="ProcessInstanceHeading">
        #{{ processDefinition.id }} - {{ processDefinition.name }} -
        <span>Deployments</span>
      </h2>
      <div>
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('loginProcessosApp.processDeployment.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link
          :to="{ name: 'ProcessDefinitionDeploy' }"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-process-definition"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('loginProcessosApp.processDefinition.deploy.title')">Deploy a Process</span>
        </router-link>
      </div>
    </div>
    <br />
    <div class="card border-success mb-3">
      <h4 class="card-header collapse-link" v-on:click="collapse('showActiveDeployments')">
        Active Deployments
        <font-awesome-icon icon="compress-alt" v-if="collapseController.showActiveDeployments"></font-awesome-icon>
        <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
      </h4>
      <b-collapse v-model="collapseController.showActiveDeployments" id="collapse-active-deployments">
        <div class="alert alert-warning mb-0" v-if="!isFetching && activeProcessDeployments && activeProcessDeployments.length === 0">
          <span v-text="$t('loginProcessosApp.processDeployment.home.notActiveProcessDeployment')">No processDeployments found</span>
        </div>
        <div class="table-responsive mb-0" v-if="processDeployments && activeProcessDeployments.length > 0">
          <table class="table table-striped" aria-describedby="processDeployments">
            <thead>
              <tr>
                <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.status')">Status</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.camundaProcessDefinitionId')">Camunda Process Definition Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.camundaDeploymentId')">Camunda Process Deployment Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.deployDate')">Deploy Date</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.activationDate')">Activation Date</span>
                </th>
                <th scope="row"><span v-text="$t('loginProcessosApp.processDeployment.tenant')">Tenant</span></th>
                <th scope="row"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="processDeployment in activeProcessDeployments" :key="processDeployment.id" data-cy="entityTable">
                <td>{{ processDeployment.id }}</td>
                <td><akip-show-process-deployment-status :status="processDeployment.status"></akip-show-process-deployment-status></td>
                <td>{{ processDeployment.camundaProcessDefinitionId }}</td>
                <td>{{ processDeployment.camundaDeploymentId }}</td>
                <td>{{ processDeployment.deployDate ? $d(Date.parse(processDeployment.deployDate), 'short', 'pt-BR') : '' }}</td>
                <td>{{ processDeployment.activationDate ? $d(Date.parse(processDeployment.activationDate), 'short', 'pt-BR') : '' }}</td>
                <td>{{ processDeployment.tenant ? processDeployment.tenant.name : '' }}</td>
                <td class="text-right">
                  <div class="btn-group">
                    <router-link
                      :to="`/process-definition/${processDeployment.processDefinition.bpmnProcessDefinitionId}/deployment/${processDeployment.id}/view`"
                      class="btn btn-info btn-sm details"
                      data-cy="entityDetailsButton"
                    >
                      <font-awesome-icon icon="eye"></font-awesome-icon>
                      <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                    </router-link>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </b-collapse>
    </div>

    <div class="card border-secondary mb-3">
      <h4 class="card-header collapse-link" v-on:click="collapse('showInactiveDeployments')">
        Inactive Deployments
        <font-awesome-icon icon="compress-alt" v-if="collapseController.showInactiveDeployments"></font-awesome-icon>
        <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
      </h4>
      <b-collapse v-model="collapseController.showInactiveDeployments" id="collapse-inactive-deployments">
        <div class="alert alert-warning mb-0" v-if="!isFetching && inactiveProcessDeployments && inactiveProcessDeployments.length === 0">
          <span v-text="$t('loginProcessosApp.processDeployment.home.notInactiveProcessDeployment')">No processDeployments found</span>
        </div>
        <div class="table-responsive mb-0" v-if="processDeployments && inactiveProcessDeployments.length > 0">
          <table class="table table-striped" aria-describedby="processDeployments">
            <thead>
              <tr>
                <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                <th scope="row"><span v-text="$t('loginProcessosApp.processDeployment.status')">Status</span></th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.camundaProcessDefinitionId')">Camunda Process Definition Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.camundaDeploymentId')">Camunda Process Deployment Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.deployDate')">Deploy Date</span>
                </th>
                <th scope="row">
                  <span v-text="$t('loginProcessosApp.processDeployment.inactivationDate')">Inactivation Date</span>
                </th>
                <th scope="row"><span v-text="$t('loginProcessosApp.processDeployment.tenant')">Tenant</span></th>
                <th scope="row"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="processDeployment in inactiveProcessDeployments" :key="processDeployment.id" data-cy="entityTable">
                <td>{{ processDeployment.id }}</td>
                <td><akip-show-process-deployment-status :status="processDeployment.status"></akip-show-process-deployment-status></td>
                <td>{{ processDeployment.camundaProcessDefinitionId }}</td>
                <td>{{ processDeployment.camundaDeploymentId }}</td>
                <td>{{ processDeployment.deployDate ? $d(Date.parse(processDeployment.deployDate), 'short', 'pt-BR') : '' }}</td>
                <td>
                  {{ processDeployment.inactivationDate ? $d(Date.parse(processDeployment.inactivationDate), 'short', 'pt-BR') : '' }}
                </td>
                <td>{{ processDeployment.tenant ? processDeployment.tenant.name : '' }}</td>
                <td class="text-right">
                  <div class="btn-group">
                    <router-link
                      :to="`/process-definition/${processDeployment.processDefinition.bpmnProcessDefinitionId}/deployment/${processDeployment.id}/view`"
                      class="btn btn-info btn-sm details"
                      data-cy="entityDetailsButton"
                    >
                      <font-awesome-icon icon="eye"></font-awesome-icon>
                      <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                    </router-link>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </b-collapse>
    </div>

    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
    </button>
  </div>
</template>

<script lang="ts" src="./process-definition-deployments.component.ts"></script>

<style scoped>
.table-responsive {
  font-size: 90%;
}
</style>
