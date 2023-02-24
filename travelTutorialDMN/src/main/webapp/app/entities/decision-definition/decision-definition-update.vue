<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="decisionDefinition.home.createOrEditLabel"
          data-cy="DecisionDefinitionCreateUpdateHeading"
          v-text="$t('decisionDefinition.home.createOrEditLabel')"
        >
          Create or edit a DecisionDefinition
        </h2>
        <div>
          <div class="form-group" v-if="decisionDefinition.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="decisionDefinition.id" readonly />
          </div>
          <div class="form-group" v-if="decisionDefinition.id">
            <label class="form-control-label" v-text="$t('decisionDefinition.name')" for="decision-definition-name">Name</label>
            <input type="text" class="form-control" name="name" id="decision-definition-name" v-model="decisionDefinition.name" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('decisionDefinition.specificationFile')"
              for="decision-definition-specificationFile"
              >Specification File</label
            >
            <div>
              <div v-if="decisionDefinition.specificationFile" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(decisionDefinition.specificationFileContentType, decisionDefinition.specificationFile)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left"
                  >{{ decisionDefinition.specificationFileContentType }}, {{ byteSize(decisionDefinition.specificationFile) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    decisionDefinition.specificationFile = null;
                    decisionDefinition.specificationFileContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_specificationFile"
                id="file_specificationFile"
                data-cy="specificationFile"
                v-on:change="setFileData($event, decisionDefinition, 'specificationFile', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="specificationFile"
              id="decision-definition-specificationFile"
              data-cy="specificationFile"
              :class="{
                valid: !$v.decisionDefinition.specificationFile.$invalid,
                invalid: $v.decisionDefinition.specificationFile.$invalid,
              }"
              v-model="$v.decisonDefinition.specificationFile.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="specificationFileContentType"
              id="decision-definition-specificationFileContentType"
              v-model="decisionDefinition.specificationFileContentType"
            />
          </div>
          <div class="form-group">
            <div class="col-sm">
              <div class="custom-control custom-switch">
                <input id="requireATenant" type="checkbox" class="custom-control-input" v-model="decisionDefinition.requireATenant" />
                <label class="custom-control-label" v-text="$t('decisionDefinition.requireATenant')" for="requireATenant">
                  Require A Tenant
                </label>
              </div>
            </div>
          </div>
          <div class="form-group" v-if="decisionDefinition.requireATenant">
            <label v-text="$t('decisionDefinition.tenants')">Tenants</label>
            <multiselect
              id="tenants"
              v-model="decisionDefinition.tenants"
              :options="tenants"
              :multiple="true"
              group-label="Tenants"
              :group-select="true"
              select-label=""
              :close-on-select="false"
              :hide-selected="true"
              placeholder=""
              track-by="id"
              label="name"
            ></multiselect>
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
            :disabled="$v.decisionDefinition.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./decision-definition-update.component.ts"></script>
