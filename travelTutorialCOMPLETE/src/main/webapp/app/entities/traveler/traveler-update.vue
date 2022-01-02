<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.traveler.home.createOrEditLabel"
          data-cy="TravelerCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.traveler.home.createOrEditLabel')"
        >
          Create or edit a Traveler
        </h2>
        <div>
          <div class="form-group" v-if="traveler.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="traveler.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.traveler.name')" for="traveler-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="traveler-name"
              data-cy="name"
              :class="{ valid: !$v.traveler.name.$invalid, invalid: $v.traveler.name.$invalid }"
              v-model="$v.traveler.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.traveler.age')" for="traveler-age">Age</label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="traveler-age"
              data-cy="age"
              :class="{ valid: !$v.traveler.age.$invalid, invalid: $v.traveler.age.$invalid }"
              v-model.number="$v.traveler.age.$model"
              required
            />
            <div v-if="$v.traveler.age.$anyDirty && $v.traveler.age.$invalid">
              <small class="form-text text-danger" v-if="!$v.traveler.age.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.traveler.age.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.traveler.age.max" v-text="$t('entity.validation.max', { max: 200 })">
                This field cannot be longer than 200 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.traveler.age.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
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
            :disabled="$v.traveler.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./traveler-update.component.ts"></script>
