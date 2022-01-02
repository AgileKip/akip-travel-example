<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hotelRoom">
        <h2 class="jh-entity-heading" data-cy="hotelRoomDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.hotelRoom.detail.title')">HotelRoom</span> {{ hotelRoom.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.code')">Code</span>
          </dt>
          <dd>
            <span>{{ hotelRoom.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.sleeps')">Sleeps</span>
          </dt>
          <dd>
            <span>{{ hotelRoom.sleeps }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.boodked')">Boodked</span>
          </dt>
          <dd>
            <span>{{ hotelRoom.boodked }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.duration')">Duration</span>
          </dt>
          <dd>
            <span>{{ hotelRoom.duration }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.price')">Price</span>
          </dt>
          <dd>
            <span>{{ hotelRoom.price }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.hotelCo')">Hotel Co</span>
          </dt>
          <dd>
            <div v-if="hotelRoom.hotelCo">
              <router-link :to="{ name: 'HotelCompanyView', params: { hotelCompanyId: hotelRoom.hotelCo.id } }">{{
                hotelRoom.hotelCo.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="hotelRoom.id"
          :to="{ name: 'HotelRoomEdit', params: { hotelRoomId: hotelRoom.id } }"
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

<script lang="ts" src="./hotel-room-details.component.ts"></script>
