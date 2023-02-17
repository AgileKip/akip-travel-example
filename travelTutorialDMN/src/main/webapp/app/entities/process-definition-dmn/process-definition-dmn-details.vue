<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="processDefinition">
        <h2 class="jh-entity-heading" data-cy="processDefinitionDetailsHeading">
          <span v-text="$t('loginProcessosApp.processDefinition.detail.title')">ProcessDefinition</span>
        </h2>
        <akip-show-process-definition :processDefinition="processDefinition"> </akip-show-process-definition>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="processDefinition.id"
          :to="{ name: 'ProcessDefinitionEdit', params: { processDefinitionId: processDefinition.id } }"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./process-definition-details.component.ts"></script>
