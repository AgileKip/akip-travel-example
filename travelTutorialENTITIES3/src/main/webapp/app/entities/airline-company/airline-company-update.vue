<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialEntities3App.airlineCompany.home.createOrEditLabel"
          data-cy="AirlineCompanyCreateUpdateHeading"
          v-text="$t('travelTutorialEntities3App.airlineCompany.home.createOrEditLabel')"
        >
          Create or edit a AirlineCompany
        </h2>
        <div>
          <div class="form-group" v-if="airlineCompany.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="airlineCompany.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntities3App.airlineCompany.name')" for="airline-company-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="airline-company-name"
              data-cy="name"
              :class="{ valid: !$v.airlineCompany.name.$invalid, invalid: $v.airlineCompany.name.$invalid }"
              v-model="$v.airlineCompany.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialEntities3App.airlineCompany.code')" for="airline-company-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="airline-company-code"
              data-cy="code"
              :class="{ valid: !$v.airlineCompany.code.$invalid, invalid: $v.airlineCompany.code.$invalid }"
              v-model="$v.airlineCompany.code.$model"
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
            :disabled="$v.airlineCompany.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./airline-company-update.component.ts"></script>
