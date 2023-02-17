<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="deploy()">
        <h2
          id="loginProcessosApp.processDefinition.deploy.title"
          data-cy="ProcessDeploymentDeployHeading"
          v-text="$t('processDefinitionDmn.deploy.title')"
        >
          Deploy a Process
        </h2>
        <div class="form-group row py-5">
          <div class="col-sm">
            <label class="form-control-label" v-text="$t('processDefinitionDmn.deploy.specificationFile')"
              >Specification File</label
            >
            <div>
              <div v-if="processDeployment.specificationFile" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(processDeployment.specificationFileContentType, processDeployment.specificationFile)"
                  v-text="$t('entity.action.open')"
                  >open</a
                ><br />
                <span class="pull-left"
                  >{{ processDeployment.specificationFileContentType }}, {{ byteSize(processDeployment.specificationFile) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    processDeployment.specificationFile = null;
                    processDeployment.specificationFileContentType = null;
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
                v-on:change="setFileData($event, processDeployment, 'specificationFile', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="specificationFile"
              id="process-definition-specificationFile"
              data-cy="specificationFile"
              :class="{ valid: !$v.processDeployment.specificationFile.$invalid, invalid: $v.processDeployment.specificationFile.$invalid }"
              v-model="$v.processDeployment.specificationFile.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="specificationFileContentType"
              id="process-definition-specificationFileContentType"
              v-model="processDeployment.specificationFileContentType"
            />
          </div>

          <div class="col-sm">
            <label v-text="$t('processDefinitionDmn.deploy.tenant')">Tenant</label>
            <select class="form-control" name="tenant" v-model="processDeployment.tenant">
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
            :disabled="$v.processDeployment.$invalid || isDeploying"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('processDefinitionDmn.deploy.deployLabel')">Deploy</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./process-definition-dmn-deploy.component.ts"></script>
