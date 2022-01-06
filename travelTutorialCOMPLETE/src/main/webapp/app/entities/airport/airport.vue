<template>
  <div>
    <h2 id="page-heading" data-cy="AirportHeading">
      <span v-text="$t('travelTutorialCompleteApp.airport.home.title')" id="airport-heading">Airports</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCompleteApp.airport.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AirportCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-airport"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('travelTutorialCompleteApp.airport.home.createLabel')"> Create a new Airport </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && airports && airports.length === 0">
      <span v-text="$t('travelTutorialCompleteApp.airport.home.notFound')">No airports found</span>
    </div>
    <div class="table-responsive" v-if="airports && airports.length > 0">
      <table class="table table-striped" aria-describedby="airports">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.airport.name')">Name</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.airport.country')">Country</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.airport.city')">City</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.airport.code')">Code</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="airport in airports" :key="airport.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AirportView', params: { airportId: airport.id } }">{{ airport.id }}</router-link>
            </td>
            <td>{{ airport.name }}</td>
            <td>{{ airport.country }}</td>
            <td>{{ airport.city }}</td>
            <td>{{ airport.code }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AirportView', params: { airportId: airport.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AirportEdit', params: { airportId: airport.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(airport)"
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
          id="travelTutorialCompleteApp.airport.delete.question"
          data-cy="airportDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-airport-heading" v-text="$t('travelTutorialCompleteApp.airport.delete.question', { id: removeId })">
          Are you sure you want to delete this Airport?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-airport"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAirport()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./airport.component.ts"></script>
