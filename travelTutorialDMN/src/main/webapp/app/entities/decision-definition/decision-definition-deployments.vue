<template>
  <div>
    <div class="d-flex justify-content-between">
      <h2 id="page-heading" data-cy="DecisionInstanceHeading">
        #{{ decisionDefinition.id }} - {{ decisionDefinition.name }} -
        <span>Deployments</span>
      </h2>
      <div>
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('decisionDeployment.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link
          :to="{ name: 'DecisionDefinitionDeploy' }"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-decision-definition"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('decisionDeployment.deploy.title')">Deploy a Decision</span>
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
        <div class="alert alert-warning mb-0" v-if="!isFetching && activeDecisionDeployments && activeDecisionDeployments.length === 0">
          <span v-text="$t('decisionDeployment.home.notActiveDecisionDeployment')">No decisionDeployments found</span>
        </div>
        <div class="table-responsive mb-0" v-if="decisionDeployments && activeDecisionDeployments.length > 0">
          <table class="table table-striped" aria-describedby="decisionDeployments">
            <thead>
              <tr>
                <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.status')">Status</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.camundaDecisionDefinitionId')">Camunda Decision Definition Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.camundaDeploymentId')">Camunda Decision Deployment Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.deployDate')">Deploy Date</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.activationDate')">Activation Date</span>
                </th>
                <th scope="row"><span v-text="$t('decisionDeployment.tenant')">Tenant</span></th>
                <th scope="row"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="decisionDeployment in activeDecisionDeployments" :key="decisionDeployment.id" data-cy="entityTable">
                <td>{{ decisionDeployment.id }}</td>
                <td><akip-show-decision-deployment-status :status="decisionDeployment.status"></akip-show-decision-deployment-status></td>
                <td>{{ decisionDeployment.camundaDecisionDefinitionId }}</td>
                <td>{{ decisionDeployment.camundaDeploymentId }}</td>
                <td>{{ decisionDeployment.deployDate ? $d(Date.parse(decisionDeployment.deployDate), 'short', 'pt-BR') : '' }}</td>
                <td>{{ decisionDeployment.activationDate ? $d(Date.parse(decisionDeployment.activationDate), 'short', 'pt-BR') : '' }}</td>
                <td>{{ decisionDeployment.tenant ? decisionDeployment.tenant.name : '' }}</td>
                <td class="text-right">
                  <div class="btn-group">
                    <router-link
                      :to="`/decision-deployment/${decisionDeployment.id}/view`"
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
        <div class="alert alert-warning mb-0" v-if="!isFetching && inactiveDecisionDeployments && inactiveDecisionDeployments.length === 0">
          <span v-text="$t('decisionDeployment.home.notInactiveDecisionDeployment')">No decisionDeployments found</span>
        </div>
        <div class="table-responsive mb-0" v-if="decisionDeployments && inactiveDecisionDeployments.length > 0">
          <table class="table table-striped" aria-describedby="decisionDeployments">
            <thead>
              <tr>
                <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
                <th scope="row"><span v-text="$t('decisionDeployment.status')">Status</span></th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.camundaDecisionDefinitionId')">Camunda Decision Definition Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.camundaDeploymentId')">Camunda Decision Deployment Id</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.deployDate')">Deploy Date</span>
                </th>
                <th scope="row">
                  <span v-text="$t('decisionDeployment.inactivationDate')">Inactivation Date</span>
                </th>
                <th scope="row"><span v-text="$t('decisionDeployment.tenant')">Tenant</span></th>
                <th scope="row"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="decisionDeployment in inactiveDecisionDeployments" :key="decisionDeployment.id" data-cy="entityTable">
                <td>{{ decisionDeployment.id }}</td>
                <td><akip-show-decision-deployment-status :status="decisionDeployment.status"></akip-show-decision-deployment-status></td>
                <td>{{ decisionDeployment.camundaDecisionDefinitionId }}</td>
                <td>{{ decisionDeployment.camundaDeploymentId }}</td>
                <td>{{ decisionDeployment.deployDate ? $d(Date.parse(decisionDeployment.deployDate), 'short', 'pt-BR') : '' }}</td>
                <td>
                  {{ decisionDeployment.inactivationDate ? $d(Date.parse(decisionDeployment.inactivationDate), 'short', 'pt-BR') : '' }}
                </td>
                <td>{{ decisionDeployment.tenant ? decisionDeployment.tenant.name : '' }}</td>
                <td class="text-right">
                  <div class="btn-group">
                    <router-link
                      :to="`/decision-deployment/${decisionDeployment.id}/view`"
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

<script lang="ts" src="./decision-definition-deployments.component.ts"></script>

<style scoped>
.table-responsive {
  font-size: 90%;
}
</style>
