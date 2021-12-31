<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialEntitiesApp.hotelCompany.home.createOrEditLabel"
          data-cy="HotelCompanyCreateUpdateHeading"
          v-text="$t('travelTutorialEntitiesApp.hotelCompany.home.createOrEditLabel')"
        >
          Create or edit a HotelCompany
        </h2>
        <div>
          <div class="form-group" v-if="hotelCompany.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="hotelCompany.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntitiesApp.hotelCompany.name')" for="hotel-company-name"
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
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntitiesApp.hotelCompany.code')" for="hotel-company-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="hotel-company-code"
              data-cy="code"
              :class="{ valid: !$v.hotelCompany.code.$invalid, invalid: $v.hotelCompany.code.$invalid }"
              v-model="$v.hotelCompany.code.$model"
            />
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
