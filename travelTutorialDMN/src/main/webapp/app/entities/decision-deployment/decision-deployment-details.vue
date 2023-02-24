<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="decisionDeployment">
        <h2 class="jh-entity-heading" data-cy="decisionDeploymentDetailsHeading">
          <span v-text="$t('decisionDeploymentDmn.detail.title')">Decision Deployment</span>
        </h2>
        <div class="card mb-3">
          <h5 class="card-header">
            <span class="title">
              <span v-text="$t('decisionDeploymentDmn.detail.card-header')">Decision Deployment</span>
              #{{ decisionDeployment.id }}
            </span>
            <akip-show-decision-deployment-status :status="decisionDeployment.status"></akip-show-decision-deployment-status>
          </h5>
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm">
                <span class="label" v-text="$t('decisionInstance.decisionDefinition')">Decision Definition </span>:
                <br />
                <span class="link" v-if="decisionDeployment.decisionDefinition">
                  <router-link
                    class="link"
                    :to="`/decision-definition/${decisionDeployment.decisionDefinition.dmnDecisionDefinitionId}/view`"
                  >
                    {{ decisionDeployment.decisionDefinition.name }}
                  </router-link>
                </span>
              </div>

              <div class="col-sm">
                <span class="label" v-text="$t('decisionDeployment.camundaDecisionDefinitionId')">Decision Definition </span>:
                <br />
                <strong>{{ decisionDeployment.camundaDecisionDefinitionId }}</strong>
              </div>

              <div class="col-sm">
                <span class="label" v-text="$t('decisionDeploymentDmn.camundaDeploymentId')">Decision Definition </span>:
                <br />
                <strong>{{ decisionDeployment.camundaDeploymentId }}</strong>
              </div>

              <div class="col-sm">
                <span class="label" v-text="$t('decisionDeploymentDmn.tenant')">Tenant </span>:
                <br />
                <strong v-if="decisionDeployment.tenant">{{ decisionDeployment.tenant.name }}</strong>
              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-3">
                <span class="label" v-text="$t('decisionDeploymentDmn.deployDate')">Deploy Date</span>:
                <br />
                <strong>{{ decisionDeployment.deployDate ? $d(Date.parse(decisionDeployment.deployDate), 'short', 'pt-BR') : '' }}</strong>
              </div>

              <div class="col-sm-3" v-if="isActive">
                <span class="label" v-text="$t('decisionDeploymentDmn.activationDate')">Activation Date</span>:
                <br />
                <strong>{{
                  decisionDeployment.activationDate ? $d(Date.parse(decisionDeployment.activationDate), 'short', 'pt-BR') : ''
                }}</strong>
              </div>
              <div class="col-sm-3" v-else>
                <span class="label" v-text="$t('decisionDeploymentDmn.inactivationDate')">Inactivation Date</span>:
                <br />
                <strong>{{
                  decisionDeployment.inactivationDate ? $d(Date.parse(decisionDeployment.inactivationDate), 'short', 'pt-BR') : ''
                }}</strong>
              </div>
            </div>
            <div class="card">
              <h4 class="card-header collapse-link" v-on:click="collapse('showDecision')">
                Decision
                <font-awesome-icon icon="compress-alt" v-if="collapseController.showDecision"></font-awesome-icon>
                <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
              </h4>
              <b-collapse v-model="collapseController.showDecision" id="collapse-decision">
                <akip-show-decision-deployment-bpmn-model
                  :decisionDeploymentId="decisionDeployment.id"
                ></akip-show-decision-deployment-bpmn-model>
              </b-collapse>
            </div>
          </div>
        </div>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./decision-deployment-details.component.ts"></script>

<style scoped>
.title {
  float: left;
  padding-right: 0.55em;
}
</style>
