<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.hotelRoom.home.createOrEditLabel"
          data-cy="HotelRoomCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.hotelRoom.home.createOrEditLabel')"
        >
          Create or edit a HotelRoom
        </h2>
        <div>
          <div class="form-group" v-if="hotelRoom.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="hotelRoom.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.code')" for="hotel-room-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="hotel-room-code"
              data-cy="code"
              :class="{ valid: !$v.hotelRoom.code.$invalid, invalid: $v.hotelRoom.code.$invalid }"
              v-model="$v.hotelRoom.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.sleeps')" for="hotel-room-sleeps"
              >Sleeps</label
            >
            <input
              type="number"
              class="form-control"
              name="sleeps"
              id="hotel-room-sleeps"
              data-cy="sleeps"
              :class="{ valid: !$v.hotelRoom.sleeps.$invalid, invalid: $v.hotelRoom.sleeps.$invalid }"
              v-model.number="$v.hotelRoom.sleeps.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.boodked')" for="hotel-room-boodked"
              >Boodked</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="hotel-room-boodked"
                  v-model="$v.hotelRoom.boodked.$model"
                  name="boodked"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="hotel-room-boodked"
                data-cy="boodked"
                type="text"
                class="form-control"
                name="boodked"
                :class="{ valid: !$v.hotelRoom.boodked.$invalid, invalid: $v.hotelRoom.boodked.$invalid }"
                v-model="$v.hotelRoom.boodked.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.duration')" for="hotel-room-duration"
              >Duration</label
            >
            <input
              type="number"
              class="form-control"
              name="duration"
              id="hotel-room-duration"
              data-cy="duration"
              :class="{ valid: !$v.hotelRoom.duration.$invalid, invalid: $v.hotelRoom.duration.$invalid }"
              v-model.number="$v.hotelRoom.duration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.price')" for="hotel-room-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="hotel-room-price"
              data-cy="price"
              :class="{ valid: !$v.hotelRoom.price.$invalid, invalid: $v.hotelRoom.price.$invalid }"
              v-model.number="$v.hotelRoom.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.hotelCo')" for="hotel-room-hotelCo"
              >Hotel Co</label
            >
            <select class="form-control" id="hotel-room-hotelCo" data-cy="hotelCo" name="hotelCo" v-model="hotelRoom.hotelCo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="hotelRoom.hotelCo && hotelCompanyOption.id === hotelRoom.hotelCo.id ? hotelRoom.hotelCo : hotelCompanyOption"
                v-for="hotelCompanyOption in hotelCompanies"
                :key="hotelCompanyOption.id"
              >
                {{ hotelCompanyOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.hotelRoom.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./hotel-room-update.component.ts"></script>
