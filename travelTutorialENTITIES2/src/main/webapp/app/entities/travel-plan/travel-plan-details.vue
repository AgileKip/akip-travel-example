<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="travelPlan">
        <h2 class="jh-entity-heading" data-cy="travelPlanDetailsHeading">
          <span v-text="$t('travelTutorialEntities2App.travelPlan.detail.title')">TravelPlan</span> {{ travelPlan.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.name')">Name</span>
          </dt>
          <dd>
            <span>{{ travelPlan.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.startDate')">Start Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.startDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.endDate')">End Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.endDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.hotelBookingNumber')">Hotel Booking Number</span>
          </dt>
          <dd>
            <span>{{ travelPlan.hotelBookingNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.airlineTicketNumber')">Airline Ticket Number</span>
          </dt>
          <dd>
            <span>{{ travelPlan.airlineTicketNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.carBookingNumber')">Car Booking Number</span>
          </dt>
          <dd>
            <span>{{ travelPlan.carBookingNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.finalRemarks')">Final Remarks</span>
          </dt>
          <dd>
            <span>{{ travelPlan.finalRemarks }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.airlineCompany')">Airline Company</span>
          </dt>
          <dd>
            <div v-if="travelPlan.airlineCompany">
              <router-link :to="{ name: 'AirlineCompanyView', params: { airlineCompanyId: travelPlan.airlineCompany.id } }">{{
                travelPlan.airlineCompany.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.hotelCompany')">Hotel Company</span>
          </dt>
          <dd>
            <div v-if="travelPlan.hotelCompany">
              <router-link :to="{ name: 'HotelCompanyView', params: { hotelCompanyId: travelPlan.hotelCompany.id } }">{{
                travelPlan.hotelCompany.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialEntities2App.travelPlan.carRentalCompany')">Car Rental Company</span>
          </dt>
          <dd>
            <div v-if="travelPlan.carRentalCompany">
              <router-link :to="{ name: 'CarRentalCompanyView', params: { CarRentalCompanyId: travelPlan.carRentalCompany.id } }">{{
                travelPlan.carRentalCompany.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./travel-plan-details.component.ts"></script>
