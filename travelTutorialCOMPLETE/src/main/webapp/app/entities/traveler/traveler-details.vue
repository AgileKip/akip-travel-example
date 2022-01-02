<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="traveler">
        <h2 class="jh-entity-heading" data-cy="travelerDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.traveler.detail.title')">Traveler</span> {{ traveler.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.traveler.name')">Name</span>
          </dt>
          <dd>
            <span>{{ traveler.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelTutorialCompleteApp.traveler.age')">Age</span>
          </dt>
          <dd>
            <span>{{ traveler.age }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="traveler.id" :to="{ name: 'TravelerEdit', params: { travelerId: traveler.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./traveler-details.component.ts"></script>
