<template>
  <div v-if="decisionDefinitions">
    <h2 id="page-heading" data-cy="DecisionDefinitionHeading">
      <span v-text="$t('decisionDefinition.home.title')" id="decision-definition-heading">Decision Definitions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('decisionDefinition.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link
          :to="{ name: 'DecisionDefinitionDeploy' }"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-decision-definition"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('decisionDefinition.deploy.title')">Deploy DMN</span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && decisionDefinitions && decisionDefinitions.length === 0">
      <span v-text="$t('decisionDefinition.home.notFound')">No DMN found</span>
    </div>
    <div class="table-responsive table-sm" v-if="decisionDefinitions && decisionDefinitions.length > 0">
      <table class="table table-striped" aria-describedby="decisionDefinitions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('decisionDefinition.dmn.name')">Name</span></th>
            <th scope="row">
              <span v-text="$t('decisionDefinition.dmn.dmnDecisionDefinitionId')">Dmn Decision Definition Id</span>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="decisionDefinition in decisionDefinitions" :key="decisionDefinition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DecisionDefinitionView', params: { decisionDefinitionId: decisionDefinition.id } }">{{
                decisionDefinition.id
              }}</router-link>
            </td>
            <td>{{ decisionDefinition.name }}</td>
            <td>{{ decisionDefinition.dmnDecisionDefinitionId }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/decision-definition/${decisionDefinition.dmnDecisionDefinitionId}/deployments`"
                  class="btn btn-primary btn-sm details"
                >
                  <font-awesome-icon icon="list"></font-awesome-icon>
                  <span class="d-none d-md-inline">Deployments</span>
                </router-link>

                <router-link
                  :to="{ name: 'Dashboard', params: { decisionDefinitionId: decisionDefinition.dmnDecisionDefinitionId } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>

                <b-button
                  v-on:click="prepareRemove(decisionDefinition)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="decisionDefinition.delete.question" data-cy="decisionDefinitionDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-decisionDefinition-heading" v-html="$t('decisionDefinition.delete.question', { id: removeId })">
          Are you sure you want to delete this decision Definition?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-decisionDefinition"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDecisionDefinition()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./decision-definition.component.ts"></script>

<style scoped>
.table-responsive {
  font-size: 90%;
}
</style>
