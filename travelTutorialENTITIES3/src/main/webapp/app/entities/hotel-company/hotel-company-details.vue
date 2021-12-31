<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hotelCompany">
        <h2 class="jh-entity-heading" data-cy="hotelCompanyDetailsHeading">
          <span v-text="$t('travelTutorialEntities3App.hotelCompany.detail.title')">HotelCompany</span> {{ hotelCompany.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialEntities3App.hotelCompany.name')">Name</span>
          </dt>
          <dd>
            <span>{{ hotelCompany.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities3App.hotelCompany.code')">Code</span>
          </dt>
          <dd>
            <span>{{ hotelCompany.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="hotelCompany.id"
          :to="{ name: 'HotelCompanyEdit', params: { hotelCompanyId: hotelCompany.id } }"
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

<script lang="ts" src="./hotel-company-details.component.ts"></script>
