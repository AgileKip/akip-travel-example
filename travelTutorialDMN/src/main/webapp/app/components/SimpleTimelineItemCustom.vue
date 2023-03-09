<template>
  <b-row>
    <b-col cols="3" sm="2" lg="1" class="align-self-center">
      <div class="text-muted">{{ formattedDate }}</div>
    </b-col>
    <b-col cols="1" sm="1" lg="1">
      <div class="timeline h-100">
        <h3 class="h-100 d-flex justify-content-center">
          <div class="timeline-icon align-self-center" :class="'badge-' + item.status">
            <font-awesome-icon :icon="item.icon" />
          </div>
        </h3>
      </div>
    </b-col>
    <b-col cols="2" sm="2" lg="2">
      <b-card :header="item.title" :header-bg-variant="item.status" header-text-variant="white" class="mt-1 mb-1"> </b-card>
    </b-col>
  </b-row>
</template>
<script lang="ts" src="./simple-timeline-item.component.ts"></script>
