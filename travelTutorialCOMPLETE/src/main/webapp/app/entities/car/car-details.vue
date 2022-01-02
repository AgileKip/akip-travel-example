<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="car">
        <h2 class="jh-entity-heading" data-cy="carDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.car.detail.title')">Car</span> {{ car.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.code')">Code</span>
          </dt>
          <dd>
            <span>{{ car.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.passengers')">Passengers</span>
          </dt>
          <dd>
            <span>{{ car.passengers }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.booked')">Booked</span>
          </dt>
          <dd>
            <span>{{ car.booked }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.duration')">Duration</span>
          </dt>
          <dd>
            <span>{{ car.duration }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.price')">Price</span>
          </dt>
          <dd>
            <span>{{ car.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.car.carCo')">Car Co</span>
          </dt>
          <dd>
            <div v-if="car.carCo">
              <router-link :to="{ name: 'CarRentalCompanyView', params: { carRentalCompanyId: car.carCo.id } }">{{
                car.carCo.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="car.id" :to="{ name: 'CarEdit', params: { carId: car.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./car-details.component.ts"></script>
