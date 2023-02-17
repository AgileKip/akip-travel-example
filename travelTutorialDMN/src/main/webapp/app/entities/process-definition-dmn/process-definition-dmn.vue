<template>
  <div>
    <h2 id="page-heading" data-cy="ProcessDefinitionHeading">
      <span v-text="$t('processDefinitionDmn.home.title')" id="process-definition-heading">Process Definitions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('processDefinitionDmn.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link
          :to="{ name: 'ProcessDefinitionDmnDeploy' }"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-process-definition"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('processDefinitionDmn.deploy.title')">Deploy DMN</span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && processDefinitions && processDefinitions.length === 0">
      <span v-text="$t('processDefinitionDmn.home.notFound')">No DMN found</span>
    </div>
    <div class="table-responsive table-sm" v-if="processDefinitions && processDefinitions.length > 0">
      <table class="table table-striped" aria-describedby="processDefinitions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('processDefinitionDmn.dmn.name')">Name</span></th>
            <th scope="row">
              <span v-text="$t('processDefinitionDmn.dmn.dmnProcessDefinitionId')">Dmn Process Definition Id</span>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="processDefinition in processDefinitions" :key="processDefinition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProcessDefinitionView', params: { processDefinitionId: processDefinition.id } }">{{
                processDefinition.id
              }}</router-link>
            </td>
            <td>{{ processDefinition.name }}</td>
            <td>{{ processDefinition.bpmnProcessDefinitionId }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/process-definition/${processDefinition.bpmnProcessDefinitionId}/init`"
                  class="btn btn-success btn-sm details"
                  :disabled="!processDefinition.canBeManuallyStarted"
                >
                  <font-awesome-icon icon="play"></font-awesome-icon>
                  <span class="d-none d-md-inline">Init</span>
                </router-link>

                <router-link
                  :to="`/process-definition/${processDefinition.bpmnProcessDefinitionId}/deployments`"
                  class="btn btn-primary btn-sm details"
                >
                  <font-awesome-icon icon="list"></font-awesome-icon>
                  <span class="d-none d-md-inline">Deployments</span>
                </router-link>

                <router-link
                  :to="`/process-definition/${processDefinition.bpmnProcessDefinitionId}/instances`"
                  class="btn btn-secondary btn-sm details"
                >
                  <font-awesome-icon icon="list"></font-awesome-icon>
                  <span class="d-none d-md-inline">Instances</span>
                </router-link>

                <router-link
                  :to="{ name: 'Dashboard', params: { processDefinitionId: processDefinition.bpmnProcessDefinitionId } }"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>

                <b-button
                  v-on:click="prepareRemove(processDefinition)"
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
        ><span
          id="loginProcessosApp.processDefinition.delete.question"
          data-cy="processDefinitionDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-processDefinition-heading" v-html="$t('processDefinitionDmn.delete.question', { id: removeId })">
          Are you sure you want to delete this Process Definition?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-processDefinition"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProcessDefinition()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./process-definition-dmn.component.ts"></script>

<style scoped>
.table-responsive {
  font-size: 90%;
}
</style>
