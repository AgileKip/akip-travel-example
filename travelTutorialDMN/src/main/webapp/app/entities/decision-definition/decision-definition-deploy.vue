<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="deploy()">
        <h2 id="decisionDefinition.deploy.title" data-cy="DecisionDeploymentDeployHeading" v-text="$t('decisionDefinition.deploy.title')">
          Deploy a Decision
        </h2>
        <div class="form-group row py-5">
          <div class="col-sm">
            <label class="form-control-label" v-text="$t('decisionDefinition.deploy.specificationFile')">Specification File</label>
            <div>
              <div v-if="decisionDeployment.specificationFile" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(decisionDeployment.specificationFileContentType, decisionDeployment.specificationFile)"
                  v-text="$t('entity.action.open')"
                  >open</a
                >
                ><br />
                <span class="pull-left"
                  >{{ decisionDeployment.specificationFileContentType }}, {{ byteSize(decisionDeployment.specificationFile) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    decisionDeployment.specificationFile = null;
                    decisionDeployment.specificationFileContentType = null;
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
                v-on:change="setFileData($event, decisionDeployment, 'specificationFile', false)"
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
                valid: !$v.decisionDeployment.specificationFile.$invalid,
                invalid: $v.decisionDeployment.specificationFile.$invalid,
              }"
              v-model="$v.decisionDeployment.specificationFile.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="specificationFileContentType"
              id="decision-definition-specificationFileContentType"
              v-model="decisionDeployment.specificationFileContentType"
            />
          </div>

          <div class="col-sm">
            <label v-text="$t('decisionDefinition.deploy.tenant')">Tenant</label>
            <select class="form-control" name="tenant" v-model="decisionDeployment.tenant">
              <option :value="null"></option>
              <option v-for="tenant in tenants" :key="tenant.id" :value="tenant">
                {{ tenant.name }}
              </option>
            </select>
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
            :disabled="$v.decisionDeployment.$invalid || isDeploying"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('decisionDefinition.deploy.deployLabel')"
              >Deploy</span
            >
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./decision-definition-deploy.component.ts"></script>
