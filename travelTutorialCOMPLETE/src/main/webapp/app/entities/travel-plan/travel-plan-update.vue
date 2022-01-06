<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.travelPlan.home.createOrEditLabel"
          data-cy="TravelPlanCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.travelPlan.home.createOrEditLabel')"
        >
          Create or edit a TravelPlan
        </h2>
        <div>
          <div class="form-group" v-if="travelPlan.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="travelPlan.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.name')" for="travel-plan-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="travel-plan-name"
              data-cy="name"
              :class="{ valid: !$v.travelPlan.name.$invalid, invalid: $v.travelPlan.name.$invalid }"
              v-model="$v.travelPlan.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.startDate')" for="travel-plan-startDate"
              >Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="travel-plan-startDate"
                  v-model="$v.travelPlan.startDate.$model"
                  name="startDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="travel-plan-startDate"
                data-cy="startDate"
                type="text"
                class="form-control"
                name="startDate"
                :class="{ valid: !$v.travelPlan.startDate.$invalid, invalid: $v.travelPlan.startDate.$invalid }"
                v-model="$v.travelPlan.startDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.endDate')" for="travel-plan-endDate"
              >End Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="travel-plan-endDate"
                  v-model="$v.travelPlan.endDate.$model"
                  name="endDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="travel-plan-endDate"
                data-cy="endDate"
                type="text"
                class="form-control"
                name="endDate"
                :class="{ valid: !$v.travelPlan.endDate.$invalid, invalid: $v.travelPlan.endDate.$invalid }"
                v-model="$v.travelPlan.endDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.numPax')" for="travel-plan-numPax"
              >Num Pax</label
            >
            <input
              type="number"
              class="form-control"
              name="numPax"
              id="travel-plan-numPax"
              data-cy="numPax"
              :class="{ valid: !$v.travelPlan.numPax.$invalid, invalid: $v.travelPlan.numPax.$invalid }"
              v-model.number="$v.travelPlan.numPax.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.price')" for="travel-plan-price"
              >Price</label
            >
            <input
              type="number"
              class="form-control"
              name="price"
              id="travel-plan-price"
              data-cy="price"
              :class="{ valid: !$v.travelPlan.price.$invalid, invalid: $v.travelPlan.price.$invalid }"
              v-model.number="$v.travelPlan.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.payment')" for="travel-plan-payment"
              >Payment</label
            >
            <input
              type="text"
              class="form-control"
              name="payment"
              id="travel-plan-payment"
              data-cy="payment"
              :class="{ valid: !$v.travelPlan.payment.$invalid, invalid: $v.travelPlan.payment.$invalid }"
              v-model="$v.travelPlan.payment.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('travelTutorialCompleteApp.travelPlan.hotelStartDate')"
              for="travel-plan-hotelStartDate"
              >Hotel Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="travel-plan-hotelStartDate"
                  v-model="$v.travelPlan.hotelStartDate.$model"
                  name="hotelStartDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="travel-plan-hotelStartDate"
                data-cy="hotelStartDate"
                type="text"
                class="form-control"
                name="hotelStartDate"
                :class="{ valid: !$v.travelPlan.hotelStartDate.$invalid, invalid: $v.travelPlan.hotelStartDate.$invalid }"
                v-model="$v.travelPlan.hotelStartDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('travelTutorialCompleteApp.travelPlan.hotelDuration')"
              for="travel-plan-hotelDuration"
              >Hotel Duration</label
            >
            <input
              type="number"
              class="form-control"
              name="hotelDuration"
              id="travel-plan-hotelDuration"
              data-cy="hotelDuration"
              :class="{ valid: !$v.travelPlan.hotelDuration.$invalid, invalid: $v.travelPlan.hotelDuration.$invalid }"
              v-model.number="$v.travelPlan.hotelDuration.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('travelTutorialCompleteApp.travelPlan.carStartDate')"
              for="travel-plan-carStartDate"
              >Car Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="travel-plan-carStartDate"
                  v-model="$v.travelPlan.carStartDate.$model"
                  name="carStartDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="travel-plan-carStartDate"
                data-cy="carStartDate"
                type="text"
                class="form-control"
                name="carStartDate"
                :class="{ valid: !$v.travelPlan.carStartDate.$invalid, invalid: $v.travelPlan.carStartDate.$invalid }"
                v-model="$v.travelPlan.carStartDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.carDuration')" for="travel-plan-carDuration"
              >Car Duration</label
            >
            <input
              type="number"
              class="form-control"
              name="carDuration"
              id="travel-plan-carDuration"
              data-cy="carDuration"
              :class="{ valid: !$v.travelPlan.carDuration.$invalid, invalid: $v.travelPlan.carDuration.$invalid }"
              v-model.number="$v.travelPlan.carDuration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.status')" for="travel-plan-status"
              >Status</label
            >
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.travelPlan.status.$invalid, invalid: $v.travelPlan.status.$invalid }"
              v-model="$v.travelPlan.status.$model"
              id="travel-plan-status"
              data-cy="status"
            >
              <option value="UNKNOWN" v-bind:label="$t('travelTutorialCompleteApp.PlanStatus.UNKNOWN')">UNKNOWN</option>
              <option value="CANCELED" v-bind:label="$t('travelTutorialCompleteApp.PlanStatus.CANCELED')">CANCELED</option>
              <option value="TIMEDOUT" v-bind:label="$t('travelTutorialCompleteApp.PlanStatus.TIMEDOUT')">TIMEDOUT</option>
              <option value="PAID" v-bind:label="$t('travelTutorialCompleteApp.PlanStatus.PAID')">PAID</option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('travelTutorialCompleteApp.travelPlan.proceedToCheckOut')"
              for="travel-plan-proceedToCheckOut"
              >Proceed To Check Out</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="proceedToCheckOut"
              id="travel-plan-proceedToCheckOut"
              data-cy="proceedToCheckOut"
              :class="{ valid: !$v.travelPlan.proceedToCheckOut.$invalid, invalid: $v.travelPlan.proceedToCheckOut.$invalid }"
              v-model="$v.travelPlan.proceedToCheckOut.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.car')" for="travel-plan-car">Car</label>
            <select class="form-control" id="travel-plan-car" data-cy="car" name="car" v-model="travelPlan.car">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="travelPlan.car && carOption.id === travelPlan.car.id ? travelPlan.car : carOption"
                v-for="carOption in cars"
                :key="carOption.id"
              >
                {{ carOption.license }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.flight')" for="travel-plan-flight"
              >Flight</label
            >
            <select class="form-control" id="travel-plan-flight" data-cy="flight" name="flight" v-model="travelPlan.flight">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="travelPlan.flight && flightOption.id === travelPlan.flight.id ? travelPlan.flight : flightOption"
                v-for="flightOption in flights"
                :key="flightOption.id"
              >
                {{ flightOption.code }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.travelPlan.hotelRoom')" for="travel-plan-hotelRoom"
              >Hotel Room</label
            >
            <select class="form-control" id="travel-plan-hotelRoom" data-cy="hotelRoom" name="hotelRoom" v-model="travelPlan.hotelRoom">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  travelPlan.hotelRoom && hotelRoomOption.id === travelPlan.hotelRoom.id ? travelPlan.hotelRoom : hotelRoomOption
                "
                v-for="hotelRoomOption in hotelRooms"
                :key="hotelRoomOption.id"
              >
                {{ hotelRoomOption.roomID }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('travelTutorialCompleteApp.travelPlan.travelers')" for="travel-plan-travelers">Travelers</label>
            <select
              class="form-control"
              id="travel-plan-travelers"
              data-cy="travelers"
              multiple
              name="travelers"
              v-if="travelPlan.travelers !== undefined"
              v-model="travelPlan.travelers"
            >
              <option
                v-bind:value="getSelected(travelPlan.travelers, travelerOption)"
                v-for="travelerOption in travelers"
                :key="travelerOption.id"
              >
                {{ travelerOption.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.travelPlan.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./travel-plan-update.component.ts"></script>
