<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.car.home.createOrEditLabel"
          data-cy="CarCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.car.home.createOrEditLabel')"
        >
          Create or edit a Car
        </h2>
        <div>
          <div class="form-group" v-if="car.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="car.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.license')" for="car-license">License</label>
            <input
              type="text"
              class="form-control"
              name="license"
              id="car-license"
              data-cy="license"
              :class="{ valid: !$v.car.license.$invalid, invalid: $v.car.license.$invalid }"
              v-model="$v.car.license.$model"
              required
            />
            <div v-if="$v.car.license.$anyDirty && $v.car.license.$invalid">
              <small class="form-text text-danger" v-if="!$v.car.license.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.car.license.minLength" v-text="$t('entity.validation.minlength', { min: 2 })">
                This field is required to be at least 2 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.car.license.maxLength" v-text="$t('entity.validation.maxlength', { max: 8 })">
                This field cannot be longer than 8 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.passengers')" for="car-passengers"
              >Passengers</label
            >
            <input
              type="number"
              class="form-control"
              name="passengers"
              id="car-passengers"
              data-cy="passengers"
              :class="{ valid: !$v.car.passengers.$invalid, invalid: $v.car.passengers.$invalid }"
              v-model.number="$v.car.passengers.$model"
              required
            />
            <div v-if="$v.car.passengers.$anyDirty && $v.car.passengers.$invalid">
              <small class="form-text text-danger" v-if="!$v.car.passengers.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.car.passengers.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.booked')" for="car-booked">Booked</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="car-booked"
                  v-model="$v.car.booked.$model"
                  name="booked"
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
                id="car-booked"
                data-cy="booked"
                type="text"
                class="form-control"
                name="booked"
                :class="{ valid: !$v.car.booked.$invalid, invalid: $v.car.booked.$invalid }"
                v-model="$v.car.booked.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.duration')" for="car-duration">Duration</label>
            <input
              type="number"
              class="form-control"
              name="duration"
              id="car-duration"
              data-cy="duration"
              :class="{ valid: !$v.car.duration.$invalid, invalid: $v.car.duration.$invalid }"
              v-model.number="$v.car.duration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.price')" for="car-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="car-price"
              data-cy="price"
              :class="{ valid: !$v.car.price.$invalid, invalid: $v.car.price.$invalid }"
              v-model.number="$v.car.price.$model"
              required
            />
            <div v-if="$v.car.price.$anyDirty && $v.car.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.car.price.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.car.price.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.car.carCo')" for="car-carCo">Car Co</label>
            <select class="form-control" id="car-carCo" data-cy="carCo" name="carCo" v-model="car.carCo" required>
              <option v-if="!car.carCo" v-bind:value="null" selected></option>
              <option
                v-bind:value="car.carCo && carRentalCompanyOption.id === car.carCo.id ? car.carCo : carRentalCompanyOption"
                v-for="carRentalCompanyOption in carRentalCompanies"
                :key="carRentalCompanyOption.id"
              >
                {{ carRentalCompanyOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.car.carCo.$anyDirty && $v.car.carCo.$invalid">
            <small class="form-text text-danger" v-if="!$v.car.carCo.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.car.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./car-update.component.ts"></script>
