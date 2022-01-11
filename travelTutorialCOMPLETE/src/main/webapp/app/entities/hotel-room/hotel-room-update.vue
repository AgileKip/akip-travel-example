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
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.roomID')" for="hotel-room-roomID"
              >Room ID</label
            >
            <input
              type="text"
              class="form-control"
              name="roomID"
              id="hotel-room-roomID"
              data-cy="roomID"
              :class="{ valid: !$v.hotelRoom.roomID.$invalid, invalid: $v.hotelRoom.roomID.$invalid }"
              v-model="$v.hotelRoom.roomID.$model"
              required
            />
            <div v-if="$v.hotelRoom.roomID.$anyDirty && $v.hotelRoom.roomID.$invalid">
              <small class="form-text text-danger" v-if="!$v.hotelRoom.roomID.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.hotelRoom.roomID.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.hotelRoom.roomID.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 20 })"
              >
                This field cannot be longer than 20 characters.
              </small>
            </div>
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
              required
            />
            <div v-if="$v.hotelRoom.sleeps.$anyDirty && $v.hotelRoom.sleeps.$invalid">
              <small class="form-text text-danger" v-if="!$v.hotelRoom.sleeps.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.hotelRoom.sleeps.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
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
              required
            />
            <div v-if="$v.hotelRoom.price.$anyDirty && $v.hotelRoom.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.hotelRoom.price.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.hotelRoom.price.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelRoom.hotelCo')" for="hotel-room-hotelCo"
              >Hotel Co</label
            >
            <select class="form-control" id="hotel-room-hotelCo" data-cy="hotelCo" name="hotelCo" v-model="hotelRoom.hotelCo" required>
              <option v-if="!hotelRoom.hotelCo" v-bind:value="null" selected></option>
              <option
                v-bind:value="hotelRoom.hotelCo && hotelCompanyOption.id === hotelRoom.hotelCo.id ? hotelRoom.hotelCo : hotelCompanyOption"
                v-for="hotelCompanyOption in hotelCompanies"
                :key="hotelCompanyOption.id"
              >
                {{ hotelCompanyOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.hotelRoom.hotelCo.$anyDirty && $v.hotelRoom.hotelCo.$invalid">
            <small class="form-text text-danger" v-if="!$v.hotelRoom.hotelCo.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
