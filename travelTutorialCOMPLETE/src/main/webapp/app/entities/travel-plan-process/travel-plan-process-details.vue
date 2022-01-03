<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="travelPlanProcess.processInstance">
        <h2 class="jh-entity-heading" data-cy="travelPlanProcessDetailsHeading">
          <span v-text="$t('travelTutorialCompleteApp.travelPlanProcess.detail.title')">TravelPlanProcess</span> {{ travelPlanProcess.id }}
        </h2>
        <akip-show-process-instance :processInstance="travelPlanProcess.processInstance">
          <template v-slot:title>
            <span v-text="$t('travelTutorialCompleteApp.travelPlanProcess.detail.title')">TravelPlanProcess</span> #{{
              travelPlanProcess.id
            }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="travelPlanProcess.travelPlan">
              <h5 class="card-header">
                <span class="title"> Travel Plan #{{ travelPlanProcess.travelPlan.id }} </span>
              </h5>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.name')">name</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="name"
                    id="travel-plan-name"
                    data-cy="name"
                    v-model="travelPlanProcess.travelPlan.name"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.startDate')">startDate</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="startDate"
                    id="travel-plan-startDate"
                    data-cy="startDate"
                    v-model="travelPlanProcess.travelPlan.startDate"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.endDate')">endDate</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="endDate"
                    id="travel-plan-endDate"
                    data-cy="endDate"
                    v-model="travelPlanProcess.travelPlan.endDate"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.numPax')">numPax</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="numPax"
                    id="travel-plan-numPax"
                    data-cy="numPax"
                    v-model="travelPlanProcess.travelPlan.numPax"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.price')">price</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="price"
                    id="travel-plan-price"
                    data-cy="price"
                    v-model="travelPlanProcess.travelPlan.price"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.payment')">payment</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="payment"
                    id="travel-plan-payment"
                    data-cy="payment"
                    v-model="travelPlanProcess.travelPlan.payment"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.hotelDuration')"
                    >hotelDuration</label
                  >
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="hotelDuration"
                    id="travel-plan-hotelDuration"
                    data-cy="hotelDuration"
                    v-model="travelPlanProcess.travelPlan.hotelDuration"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.carDuration')"
                    >carDuration</label
                  >
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="carDuration"
                    id="travel-plan-carDuration"
                    data-cy="carDuration"
                    v-model="travelPlanProcess.travelPlan.carDuration"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.status')">status</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="status"
                    id="travel-plan-status"
                    data-cy="status"
                    v-model="travelPlanProcess.travelPlan.status"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlanProcess.proceedToCheckOut')"
                    >proceedToCheckOut</label
                  >
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="proceedToCheckOut"
                    id="travel-plan-proceedToCheckOut"
                    data-cy="proceedToCheckOut"
                    v-model="travelPlanProcess.travelPlan.proceedToCheckOut"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanProcess.car')"
                    for="travel-plan-process-car"
                    >Car</label
                  >
                  <input
                    v-if="travelPlanProcess.travelPlan.car"
                    readonly
                    type="text"
                    class="form-control"
                    name="car"
                    id="travel-plan-car"
                    data-cy="car"
                    :value="travelPlanProcess.travelPlan.car.code"
                  />
                  <input v-else readonly type="text" class="form-control" name="car" id="travel-plan-car" data-cy="car" value="" />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanProcess.flight')"
                    for="travel-plan-process-flight"
                    >Flight</label
                  >
                  <input
                    v-if="travelPlanProcess.travelPlan.flight"
                    readonly
                    type="text"
                    class="form-control"
                    name="flight"
                    id="travel-plan-flight"
                    data-cy="flight"
                    :value="travelPlanProcess.travelPlan.flight.code"
                  />
                  <input v-else readonly type="text" class="form-control" name="flight" id="travel-plan-flight" data-cy="flight" value="" />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanProcess.hotelRoom')"
                    for="travel-plan-process-hotelRoom"
                    >Hotel Room</label
                  >
                  <input
                    v-if="travelPlanProcess.travelPlan.hotelRoom"
                    readonly
                    type="text"
                    class="form-control"
                    name="hotelRoom"
                    id="travel-plan-hotelRoom"
                    data-cy="hotelRoom"
                    :value="travelPlanProcess.travelPlan.hotelRoom.code"
                  />
                  <input
                    v-else
                    readonly
                    type="text"
                    class="form-control"
                    name="hotelRoom"
                    id="travel-plan-hotelRoom"
                    data-cy="hotelRoom"
                    value=""
                  />
                </div>
              </div>
            </div>
          </template>
        </akip-show-process-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./travel-plan-process-details.component.ts"></script>
