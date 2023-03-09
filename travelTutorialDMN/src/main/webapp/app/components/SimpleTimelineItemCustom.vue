<template>
  <b-row align-h="center">
    <b-col cols="1" sm="1" lg="1">
      <div class="timeline h-100">
        <h3 class="h-100 d-flex justify-content-center">
          <div class="timeline-icon badge badge-pill align-self-center" :class="'badge-' + item.status">
            <font-awesome-icon :icon="item.icon" />
          </div>
        </h3>
      </div>
    </b-col>
    <b-col>
      <b-card
        :header="item.title"
        :border-variant="item.status"
        :header-bg-variant="item.status"
        header-text-variant="white"
        class="mt-1 mb-1"
      >
        <b-card-text>
          {{ formattedDate }}
        </b-card-text>
      </b-card>
    </b-col>
  </b-row>
</template>
<script lang="ts" src="./simple-timeline-item.component.ts"></script>
