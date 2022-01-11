<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.hotelCompany.home.createOrEditLabel"
          data-cy="HotelCompanyCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.hotelCompany.home.createOrEditLabel')"
        >
          Create or edit a HotelCompany
        </h2>
        <div>
          <div class="form-group" v-if="hotelCompany.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="hotelCompany.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelCompany.name')" for="hotel-company-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="hotel-company-name"
              data-cy="name"
              :class="{ valid: !$v.hotelCompany.name.$invalid, invalid: $v.hotelCompany.name.$invalid }"
              v-model="$v.hotelCompany.name.$model"
              required
            />
            <div v-if="$v.hotelCompany.name.$anyDirty && $v.hotelCompany.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.hotelCompany.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.hotelCompany.name.minLength"
                v-text="$t('entity.validation.minlength', { min: 2 })"
              >
                This field is required to be at least 2 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.hotelCompany.name.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 20 })"
              >
                This field cannot be longer than 20 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.hotelCompany.place')" for="hotel-company-place"
              >Place</label
            >
            <input
              type="text"
              class="form-control"
              name="place"
              id="hotel-company-place"
              data-cy="place"
              :class="{ valid: !$v.hotelCompany.place.$invalid, invalid: $v.hotelCompany.place.$invalid }"
              v-model="$v.hotelCompany.place.$model"
              required
            />
            <div v-if="$v.hotelCompany.place.$anyDirty && $v.hotelCompany.place.$invalid">
              <small class="form-text text-danger" v-if="!$v.hotelCompany.place.required" v-text="$t('entity.validation.required')">
                This field is required.
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
            :disabled="$v.hotelCompany.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./hotel-company-update.component.ts"></script>
