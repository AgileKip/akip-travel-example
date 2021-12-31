<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="carRentalCompany">
        <h2 class="jh-entity-heading" data-cy="carRentalCompanyDetailsHeading">
          <span v-text="$t('travelTutorialEntitiesApp.carRentalCompany.detail.title')">CarRentalCompany</span> {{ carRentalCompany.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialEntitiesApp.carRentalCompany.name')">Name</span>
          </dt>
          <dd>
            <span>{{ carRentalCompany.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntitiesApp.carRentalCompany.code')">Code</span>
          </dt>
          <dd>
            <span>{{ carRentalCompany.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="carRentalCompany.id"
          :to="{ name: 'CarRentalCompanyEdit', params: { carRentalCompanyId: carRentalCompany.id } }"
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

<script lang="ts" src="./car-rental-company-details.component.ts"></script>
