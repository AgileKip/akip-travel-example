<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="flight">
        <h2 class="jh-entity-heading" data-cy="flightDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.flight.detail.title')">Flight</span> {{ flight.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.code')">Code</span>
          </dt>
          <dd>
            <span>{{ flight.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.departure')">Departure</span>
          </dt>
          <dd>
            <span v-if="flight.departure">{{ $d(Date.parse(flight.departure), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.duration')">Duration</span>
          </dt>
          <dd>
            <span>{{ flight.duration }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.emptySeats')">Empty Seats</span>
          </dt>
          <dd>
            <span>{{ flight.emptySeats }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.price')">Price</span>
          </dt>
          <dd>
            <span>{{ flight.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.from')">From</span>
          </dt>
          <dd>
            <div v-if="flight.from">
              <router-link :to="{ name: 'AirportView', params: { airportId: flight.from.id } }">{{ flight.from.code }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.to')">To</span>
          </dt>
          <dd>
            <div v-if="flight.to">
              <router-link :to="{ name: 'AirportView', params: { airportId: flight.to.id } }">{{ flight.to.code }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.flight.airline')">Airline</span>
          </dt>
          <dd>
            <div v-if="flight.airline">
              <router-link :to="{ name: 'AirlineCompanyView', params: { airlineCompanyId: flight.airline.id } }">{{
                flight.airline.code
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="flight.id" :to="{ name: 'FlightEdit', params: { flightId: flight.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./flight-details.component.ts"></script>
