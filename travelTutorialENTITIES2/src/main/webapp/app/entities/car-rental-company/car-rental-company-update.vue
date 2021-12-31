<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialEntities2App.carRentalCompany.home.createOrEditLabel"
          data-cy="CarRentalCompanyCreateUpdateHeading"
          v-text="$t('travelTutorialEntities2App.carRentalCompany.home.createOrEditLabel')"
        >
          Create or edit a CarRentalCompany
        </h2>
        <div>
          <div class="form-group" v-if="carRentalCompany.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="carRentalCompany.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntities2App.carRentalCompany.name')" for="car-rental-company-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="car-rental-company-name"
              data-cy="name"
              :class="{ valid: !$v.carRentalCompany.name.$invalid, invalid: $v.carRentalCompany.name.$invalid }"
              v-model="$v.carRentalCompany.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntities2App.carRentalCompany.code')" for="car-rental-company-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="car-rental-company-code"
              data-cy="code"
              :class="{ valid: !$v.carRentalCompany.code.$invalid, invalid: $v.carRentalCompany.code.$invalid }"
              v-model="$v.carRentalCompany.code.$model"
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
            :disabled="$v.carRentalCompany.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./car-rental-company-update.component.ts"></script>
