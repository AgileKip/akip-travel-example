<template>
  <div>
    <h2 id="page-heading" data-cy="FlightHeading">
      <span v-text="$t('travelTutorialCompleteApp.flight.home.title')" id="flight-heading">Flights</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCompleteApp.flight.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FlightCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-flight"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('travelTutorialCompleteApp.flight.home.createLabel')"> Create a new Flight </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && flights && flights.length === 0">
      <span v-text="$t('travelTutorialCompleteApp.flight.home.notFound')">No flights found</span>
    </div>
    <div class="table-responsive" v-if="flights && flights.length > 0">
      <table class="table table-striped" aria-describedby="flights">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.code')">Code</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.departure')">Departure</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.duration')">Duration</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.emptySeats')">Empty Seats</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.price')">Price</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.from')">From</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.to')">To</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.flight.airline')">Airline</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="flight in flights" :key="flight.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FlightView', params: { flightId: flight.id } }">{{ flight.id }}</router-link>
            </td>
            <td>{{ flight.code }}</td>
            <td>{{ flight.departure ? $d(Date.parse(flight.departure), 'short') : '' }}</td>
            <td>{{ flight.duration }}</td>
            <td>{{ flight.emptySeats }}</td>
            <td>{{ flight.price }}</td>
            <td>
              <div v-if="flight.from">
                <router-link :to="{ name: 'AirportView', params: { airportId: flight.from.id } }">{{ flight.from.code }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="flight.to">
                <router-link :to="{ name: 'AirportView', params: { airportId: flight.to.id } }">{{ flight.to.code }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="flight.airline">
                <router-link :to="{ name: 'AirlineCompanyView', params: { airlineCompanyId: flight.airline.id } }">{{
                  flight.airline.code
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FlightView', params: { flightId: flight.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FlightEdit', params: { flightId: flight.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(flight)"
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
        ><span id="travelTutorialCompleteApp.flight.delete.question" data-cy="flightDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-flight-heading" v-text="$t('travelTutorialCompleteApp.flight.delete.question', { id: removeId })">
          Are you sure you want to delete this Flight?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-flight"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFlight()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./flight.component.ts"></script>
