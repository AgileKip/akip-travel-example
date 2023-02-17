<template>
  <div class="card">
    <div class="card-header page-header">
      <div class="d-flex justify-content-between">
        <h1 class="h3 text-black-50">Dashboard Config of the process #{{ processDefinition.id }} - {{ processDefinition.name }}</h1>
      </div>
    </div>
    <br />
    <div class="card-body">
      <div class="card mb-3" v-for="(group, index) in dashboardConfig.groups">
        <div class="card-header">
          <div class="d-flex">
            <div class="col-6">
              <h5>Group - {{ group.title }}</h5>
            </div>
            <div class="col-6">
              <button
                v-if="index > 0"
                type="button"
                v-on:click.prevent="alterIndexGroup(index, index - 1)"
                class="btn btn-sm btn-secondary"
              >
                <font-awesome-icon icon="arrow-up"></font-awesome-icon>&nbsp;Up Group
              </button>
              <button
                v-if="index < dashboardConfig.groups.length - 1"
                type="button"
                v-on:click.prevent="alterIndexGroup(index, index + 1)"
                class="btn btn-sm btn-secondary"
              >
                <font-awesome-icon icon="arrow-down"></font-awesome-icon>&nbsp;Down Group
              </button>
            </div>
          </div>
        </div>
        <div class="card-body mb-2">
          <div class="row">
            <div class="col-sm-6">
              <div class="form-group input-group-sm">
                <label class="form-control-label">Title</label>
                <div class="input-group input-group-sm">
                  <input type="text" class="form-control" v-model="group.title" />
                </div>
              </div>
            </div>
            <div class="col-sm-6">
              <div class="form-group input-group-sm">
                <label class="form-control-label">Group Builder</label>
                <div class="input-group input-group-sm">
                  <input type="text" class="form-control" v-model="group.groupBuilder" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12" v-if="showExpression(group)">
              <div class="form-group input-group-sm">
                <label class="form-control-label">Group Builder Expression</label>
                <div class="input-group input-group-sm">
                  <textarea rows="20" class="code form-control" v-model="group.expression"></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer">
          <button type="button" v-on:click.prevent="removeGroup(index)" class="btn btn-sm btn-default">
            <font-awesome-icon icon="trash"></font-awesome-icon>&nbsp;Remove Group
          </button>
        </div>
      </div>
      <div class="mb-2">
        <button type="button" v-on:click.prevent="addGroup()" class="btn btn-sm btn-info">
          <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;Add Group
        </button>
      </div>

      <hr />
      <div class="card mb-3">
        <div class="card-body" :key="key">
          <h5 class="card-title" v-on:click="collapse('cardCalendarProperties')">Calendar Properties</h5>
          <div class="form-group input-group-sm" v-if="collapseController.cardCalendarProperties">
            <div class="input-group input-group-sm">
              <textarea rows="20" class="code form-control" v-model="dashboardConfig.calendarProperties"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="card-footer page-footer">
      <div class="d-flex justify-content-start">
        <div class="pt-1 pb-2">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-sm btn-info" data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
          </button>
          <button
            type="button"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            class="btn btn-sm btn-primary"
            :disabled="$v.dashboardConfig.$invalid || isFetching"
            v-on:click="save()"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./process-definition-dashboard-config.component.ts"></script>

<style scoped>
.code {
  font-family: Courier;
  font-size: 80%;
}
</style>
