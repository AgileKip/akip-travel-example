<template>
  <div>
    <h2 id="page-heading" data-cy="GenericTimelineHeading">
      <span v-text="$t('travelPlanApp.genericTimeline.home.title')" id="generic-timeline-heading">Generic Timelines</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelPlanApp.genericTimeline.home.refreshListLabel')">Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && genericTimelines && genericTimelines.length === 0">
      <span v-text="$t('travelPlanApp.genericTimeline.home.notFound')">No genericTimelines found</span>
    </div>
    <div class="table-responsive" v-if="genericTimelines && genericTimelines.length > 0">
      <table class="table table-striped" aria-describedby="genericTimelines">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelPlanApp.genericTimeline.needTaskH')">Need Task H</span></th>
            <th scope="row"><span v-text="$t('travelPlanApp.genericTimeline.loopEnter')">Loop Enter</span></th>
            <th scope="row"><span v-text="$t('travelPlanApp.genericTimeline.chooseTask')">Choose Task</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="genericTimeline in genericTimelines" :key="genericTimeline.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GenericTimelineView', params: { genericTimelineId: genericTimeline.id } }">{{
                genericTimeline.id
              }}</router-link>
            </td>
            <td>{{ genericTimeline.needTaskH }}</td>
            <td>{{ genericTimeline.loopEnter }}</td>
            <td>{{ genericTimeline.chooseTask }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'GenericTimelineView', params: { genericTimelineId: genericTimeline.id } }"
                  custom
                  v-slot="{ navigate }"
                >
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
          id="travelPlanApp.genericTimeline.delete.question"
          data-cy="genericTimelineDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-genericTimeline-heading" v-text="$t('travelPlanApp.genericTimeline.delete.question', { id: removeId })">
          Are you sure you want to delete this Generic Timeline?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-genericTimeline"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGenericTimeline()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./generic-timeline.component.ts"></script>
