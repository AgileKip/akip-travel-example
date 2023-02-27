<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="decisionDefinition">
        <h2 class="jh-entity-heading" data-cy="decisionDefinitionDetailsHeading">
          <span v-text="$t('decisionDefinition.detail.title')">DecisionDefinition</span>
        </h2>
        <akip-show-decision-definition :decisionDefinition="decisionDefinition"> </akip-show-decision-definition>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="decisionDefinition.id"
          :to="{ name: 'DecisionDefinitionEdit', params: { decisionDefinitionId: decisionDefinition.id } }"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./decision-definition-details.component.ts"></script>
