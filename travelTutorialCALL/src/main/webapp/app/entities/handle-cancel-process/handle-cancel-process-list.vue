<template>
  <div>
    <h2 class="jh-entity-heading" data-cy="handleCancelProcessDetailsHeading">
      <span v-text="$t('travelTutorialCallApp.handleCancelProcess.home.title')">HandleCancelProcess</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCallApp.handleCancelProcess.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link :to="`/process-definition/HandleUserCancel/init`" tag="button" class="btn btn-primary mr-2">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('travelTutorialCallApp.handleCancelProcess.home.createLabel')"> Create a new Travel Plan Process Instance </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && handleCancelProcessList && handleCancelProcessList.length === 0">
      <span v-text="$t('travelTutorialCallApp.handleCancelProcess.home.notFound')">No handleCancelProcess found</span>
    </div>
    <div class="table-responsive" v-if="handleCancelProcessList && handleCancelProcessList.length > 0">
      <table class="table table-striped" aria-describedby="handleCancelProcessList">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span>Process Instance</span></th>
            <th scope="row"><span>Handle Cancel</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Start Date</span></th>
            <th scope="row"><span>End Date</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="handleCancelProcess in handleCancelProcessList" :key="handleCancelProcess.id" data-cy="entityTable">
            <td>{{ handleCancelProcess.id }}</td>
            <td>
              <div v-if="handleCancelProcess.processInstance">
                <router-link :to="`/process-definition/HandleUserCancel/instance/${handleCancelProcess.processInstance.id}/view`">
                  {{ handleCancelProcess.processInstance.businessKey }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="handleCancelProcess.handleCancel">
                <router-link :to="{ name: 'HandleCancelView', params: { handleCancelId: handleCancelProcess.handleCancel.id } }">{{
                  handleCancelProcess.handleCancel.id
                }}</router-link>
              </div>
            </td>
            <td>
              <akip-show-process-instance-status :status="handleCancelProcess.processInstance.status"></akip-show-process-instance-status>
            </td>
            <td>
              {{
                handleCancelProcess.processInstance.startDate ? $d(Date.parse(handleCancelProcess.processInstance.startDate), 'short') : ''
              }}
            </td>
            <td>
              {{ handleCancelProcess.processInstance.endDate ? $d(Date.parse(handleCancelProcess.processInstance.endDate), 'short') : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/process-definition/HandleUserCancel/instance/${handleCancelProcess.processInstance.id}/view`"
                  tag="button"
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
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
    </button>
  </div>
</template>

<script lang="ts" src="./handle-cancel-process-list.component.ts"></script>
