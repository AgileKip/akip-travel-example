<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.airport.home.createOrEditLabel"
          data-cy="AirportCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.airport.home.createOrEditLabel')"
        >
          Create or edit a Airport
        </h2>
        <div>
          <div class="form-group" v-if="airport.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="airport.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.airport.name')" for="airport-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="airport-name"
              data-cy="name"
              :class="{ valid: !$v.airport.name.$invalid, invalid: $v.airport.name.$invalid }"
              v-model="$v.airport.name.$model"
              required
            />
            <div v-if="$v.airport.name.$anyDirty && $v.airport.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.airport.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.airport.country')" for="airport-country">Country</label>
            <input
              type="text"
              class="form-control"
              name="country"
              id="airport-country"
              data-cy="country"
              :class="{ valid: !$v.airport.country.$invalid, invalid: $v.airport.country.$invalid }"
              v-model="$v.airport.country.$model"
              required
            />
            <div v-if="$v.airport.country.$anyDirty && $v.airport.country.$invalid">
              <small class="form-text text-danger" v-if="!$v.airport.country.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.airport.city')" for="airport-city">City</label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="airport-city"
              data-cy="city"
              :class="{ valid: !$v.airport.city.$invalid, invalid: $v.airport.city.$invalid }"
              v-model="$v.airport.city.$model"
              required
            />
            <div v-if="$v.airport.city.$anyDirty && $v.airport.city.$invalid">
              <small class="form-text text-danger" v-if="!$v.airport.city.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.airport.code')" for="airport-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="airport-code"
              data-cy="code"
              :class="{ valid: !$v.airport.code.$invalid, invalid: $v.airport.code.$invalid }"
              v-model="$v.airport.code.$model"
              required
            />
            <div v-if="$v.airport.code.$anyDirty && $v.airport.code.$invalid">
              <small class="form-text text-danger" v-if="!$v.airport.code.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.airport.code.minLength" v-text="$t('entity.validation.minlength', { min: 3 })">
                This field is required to be at least 3 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.airport.code.maxLength" v-text="$t('entity.validation.maxlength', { max: 3 })">
                This field cannot be longer than 3 characters.
              </small>
            </div>
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
            :disabled="$v.airport.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./airport-update.component.ts"></script>
