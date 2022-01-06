<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="airport">
        <h2 class="jh-entity-heading" data-cy="airportDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.airport.detail.title')">Airport</span> {{ airport.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.airport.name')">Name</span>
          </dt>
          <dd>
            <span>{{ airport.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.airport.country')">Country</span>
          </dt>
          <dd>
            <span>{{ airport.country }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.airport.city')">City</span>
          </dt>
          <dd>
            <span>{{ airport.city }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.airport.code')">Code</span>
          </dt>
          <dd>
            <span>{{ airport.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="airport.id" :to="{ name: 'AirportEdit', params: { airportId: airport.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./airport-details.component.ts"></script>
