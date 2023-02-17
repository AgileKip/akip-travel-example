<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="loginProcessosApp.tenant.home.createOrEditLabel"
          data-cy="TenantCreateUpdateHeading"
          v-text="$t('loginProcessosApp.tenant.home.createOrEditLabel')"
        >
          Create or edit a Tenant
        </h2>
        <div>
          <div class="form-group" v-if="tenant.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tenant.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('loginProcessosApp.tenant.identifier')" for="tenant-identifier">Identifier</label>
            <input
              type="text"
              class="form-control"
              name="identifier"
              id="tenant-identifier"
              data-cy="identifier"
              :class="{ valid: !$v.tenant.identifier.$invalid, invalid: $v.tenant.identifier.$invalid }"
              v-model="$v.tenant.identifier.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('loginProcessosApp.tenant.name')" for="tenant-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="tenant-name"
              data-cy="name"
              :class="{ valid: !$v.tenant.name.$invalid, invalid: $v.tenant.name.$invalid }"
              v-model="$v.tenant.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('loginProcessosApp.tenant.description')" for="tenant-description"
              >Description</label
            >
            <textarea
              class="form-control"
              name="description"
              id="tenant-description"
              data-cy="description"
              :class="{ valid: !$v.tenant.description.$invalid, invalid: $v.tenant.description.$invalid }"
              v-model="$v.tenant.description.$model"
            ></textarea>
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
            :disabled="$v.tenant.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tenant-update.component.ts"></script>
