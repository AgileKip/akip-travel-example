<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="airlineCompany">
        <h2 class="jh-entity-heading" data-cy="airlineCompanyDetailsHeading">
          <span v-text="$t('travelTutorialEntitiesApp.airlineCompany.detail.title')">AirlineCompany</span> {{ airlineCompany.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialEntitiesApp.airlineCompany.name')">Name</span>
          </dt>
          <dd>
            <span>{{ airlineCompany.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntitiesApp.airlineCompany.code')">Code</span>
          </dt>
          <dd>
            <span>{{ airlineCompany.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="airlineCompany.id"
          :to="{ name: 'AirlineCompanyEdit', params: { airlineCompanyId: airlineCompany.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./airline-company-details.component.ts"></script>
