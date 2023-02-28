<template>
  <div class="card" v-if="decisionDefinition">
    <h5 class="card-header" v-if="!hideTitle">
      <span class="title">
        <slot name="title"> #{{ decisionDefinition.id }} - {{ decisionDefinition.name }} </slot>
      </span>
    </h5>
    <div class="card-body">
      <div class="xcontainer">
        <div class="row px-3">
          <div class="col summary" v-if="!hideSidebarSummary">
            <div class="label" v-text="$t('decisionDefinition.dmnDecisionDefinitionId')">BPMN Process Definition Id</div>
            <div class="link" v-if="decisionDefinition.dmnDecisionDefinitionId">
              {{ decisionDefinition.dmnDecisionDefinitionId }}
            </div>
          </div>
        </div>
        <div class="row summary-footer">
          <span class="footer-entry" v-if="decisionDefinition.createTime">
            <font-awesome-icon icon="clock"></font-awesome-icon>
            Created at: {{ $d(Date.parse(decisionDefinition.createTime), 'long', 'pt-BR') }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./akip-show-decision-definition.component.ts"></script>

<style scoped>
.title {
  float: left;
  padding-right: 0.55em;
}

.description {
  border: 1px solid lightgray !important;
  border-radius: 6px;
  padding-top: 10px;
}

.summary-footer {
  padding-top: 0.45em;
  font-size: 95%;
  color: gray;
}

.footer-entry {
  padding-right: 0.95em;
}

.summary {
  color: #586069;
}

.summary hr {
  margin-top: 0.5em;
  margin-bottom: 0.5em;
}

.label {
  font-weight: bold;
  margin-bottom: 0.25em;
}

.link a {
  font-weight: normal;
  color: #3e8acc;
}
</style>
