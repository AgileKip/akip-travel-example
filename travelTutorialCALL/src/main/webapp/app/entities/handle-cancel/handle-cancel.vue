<template>
  <div>
    <h2 id="page-heading" data-cy="HandleCancelHeading">
      <span v-text="$t('travelTutorialCallApp.handleCancel.home.title')" id="handle-cancel-heading">Handle Cancels</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCallApp.handleCancel.home.refreshListLabel')">Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && handleCancels && handleCancels.length === 0">
      <span v-text="$t('travelTutorialCallApp.handleCancel.home.notFound')">No handleCancels found</span>
    </div>
    <div class="table-responsive" v-if="handleCancels && handleCancels.length > 0">
      <table class="table table-striped" aria-describedby="handleCancels">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCallApp.handleCancel.reason')">Reason</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="handleCancel in handleCancels" :key="handleCancel.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HandleCancelView', params: { handleCancelId: handleCancel.id } }">{{
                handleCancel.id
              }}</router-link>
            </td>
            <td>{{ handleCancel.reason }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HandleCancelView', params: { handleCancelId: handleCancel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="travelTutorialCallApp.handleCancel.delete.question"
          data-cy="handleCancelDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-handleCancel-heading" v-text="$t('travelTutorialCallApp.handleCancel.delete.question', { id: removeId })">
          Are you sure you want to delete this Handle Cancel?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-handleCancel"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeHandleCancel()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./handle-cancel.component.ts"></script>
