<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="handleCancel">
        <h2 class="jh-entity-heading" data-cy="handleCancelDetailsHeading">
          <span v-text="$t('travelTutorialCallApp.handleCancel.detail.title')">HandleCancel</span> {{ handleCancel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCallApp.handleCancel.reason')">Reason</span>
          </dt>
          <dd>
            <span>{{ handleCancel.reason }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./handle-cancel-details.component.ts"></script>
