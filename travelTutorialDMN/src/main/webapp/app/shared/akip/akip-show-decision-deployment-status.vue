<template>
  <div>
    <span class="badge rounded-pill bg-success" v-if="status == 'ACTIVE'">Active</span>
    <span class="badge rounded-pill bg-secondary" v-else-if="status == 'INACTIVE'">Inactive</span>
    <span class="badge rounded-pill bg-danger" v-else-if="status == 'ERROR'">Error</span>
  </div>
</template>

<script lang="ts" src="./akip-show-decision-deployment-status.component.ts"></script>

<style scoped>
.badge {
  color: white;
  padding: 0.35em 0.65em;
  font-weight: bold;
  font-size: 90%;
}
</style>
