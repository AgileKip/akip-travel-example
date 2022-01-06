<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="travelPlan">
        <h2 class="jh-entity-heading" data-cy="travelPlanDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.travelPlan.detail.title')">TravelPlan</span> {{ travelPlan.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.name')">Name</span>
          </dt>
          <dd>
            <span>{{ travelPlan.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.startDate')">Start Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.startDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.endDate')">End Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.endDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.numPax')">Num Pax</span>
          </dt>
          <dd>
            <span>{{ travelPlan.numPax }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.price')">Price</span>
          </dt>
          <dd>
            <span>{{ travelPlan.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.payment')">Payment</span>
          </dt>
          <dd>
            <span>{{ travelPlan.payment }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.hotelStartDate')">Hotel Start Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.hotelStartDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.hotelDuration')">Hotel Duration</span>
          </dt>
          <dd>
            <span>{{ travelPlan.hotelDuration }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.carStartDate')">Car Start Date</span>
          </dt>
          <dd>
            <span>{{ travelPlan.carStartDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.carDuration')">Car Duration</span>
          </dt>
          <dd>
            <span>{{ travelPlan.carDuration }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('travelTutorialCompleteApp.PlanStatus.' + travelPlan.status)">{{ travelPlan.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.proceedToCheckOut')">Proceed To Check Out</span>
          </dt>
          <dd>
            <span>{{ travelPlan.proceedToCheckOut }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.car')">Car</span>
          </dt>
          <dd>
            <div v-if="travelPlan.car">
              <router-link :to="{ name: 'CarView', params: { carId: travelPlan.car.id } }">{{ travelPlan.car.license }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.flight')">Flight</span>
          </dt>
          <dd>
            <div v-if="travelPlan.flight">
              <router-link :to="{ name: 'FlightView', params: { flightId: travelPlan.flight.id } }">{{
                travelPlan.flight.code
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.travelPlan.hotelRoom')">Hotel Room</span>
          </dt>
          <dd>
            <div v-if="travelPlan.hotelRoom">
              <router-link :to="{ name: 'HotelRoomView', params: { hotelRoomId: travelPlan.hotelRoom.id } }">{{
                travelPlan.hotelRoom.roomID
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
