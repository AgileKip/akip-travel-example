<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.flight.home.createOrEditLabel"
          data-cy="FlightCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.flight.home.createOrEditLabel')"
        >
          Create or edit a Flight
        </h2>
        <div>
          <div class="form-group" v-if="flight.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="flight.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.code')" for="flight-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="flight-code"
              data-cy="code"
              :class="{ valid: !$v.flight.code.$invalid, invalid: $v.flight.code.$invalid }"
              v-model="$v.flight.code.$model"
              required
            />
            <div v-if="$v.flight.code.$anyDirty && $v.flight.code.$invalid">
              <small class="form-text text-danger" v-if="!$v.flight.code.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.flight.code.minLength" v-text="$t('entity.validation.minlength', { min: 5 })">
                This field is required to be at least 5 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.flight.code.maxLength" v-text="$t('entity.validation.maxlength', { max: 5 })">
                This field cannot be longer than 5 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.departure')" for="flight-departure"
              >Departure</label
            >
            <div class="d-flex">
              <input
                id="flight-departure"
                data-cy="departure"
                type="datetime-local"
                class="form-control"
                name="departure"
                :class="{ valid: !$v.flight.departure.$invalid, invalid: $v.flight.departure.$invalid }"
                required
                :value="convertDateTimeFromServer($v.flight.departure.$model)"
                @change="updateZonedDateTimeField('departure', $event)"
              />
            </div>
            <div v-if="$v.flight.departure.$anyDirty && $v.flight.departure.$invalid">
              <small class="form-text text-danger" v-if="!$v.flight.departure.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.flight.departure.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.duration')" for="flight-duration"
              >Duration</label
            >
            <input
              type="number"
              class="form-control"
              name="duration"
              id="flight-duration"
              data-cy="duration"
              :class="{ valid: !$v.flight.duration.$invalid, invalid: $v.flight.duration.$invalid }"
              v-model.number="$v.flight.duration.$model"
              required
            />
            <div v-if="$v.flight.duration.$anyDirty && $v.flight.duration.$invalid">
              <small class="form-text text-danger" v-if="!$v.flight.duration.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.flight.duration.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.emptySeats')" for="flight-emptySeats"
              >Empty Seats</label
            >
            <input
              type="number"
              class="form-control"
              name="emptySeats"
              id="flight-emptySeats"
              data-cy="emptySeats"
              :class="{ valid: !$v.flight.emptySeats.$invalid, invalid: $v.flight.emptySeats.$invalid }"
              v-model.number="$v.flight.emptySeats.$model"
              required
            />
            <div v-if="$v.flight.emptySeats.$anyDirty && $v.flight.emptySeats.$invalid">
              <small class="form-text text-danger" v-if="!$v.flight.emptySeats.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.flight.emptySeats.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.price')" for="flight-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="flight-price"
              data-cy="price"
              :class="{ valid: !$v.flight.price.$invalid, invalid: $v.flight.price.$invalid }"
              v-model.number="$v.flight.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.from')" for="flight-from">From</label>
            <select class="form-control" id="flight-from" data-cy="from" name="from" v-model="flight.from">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="flight.from && airportOption.id === flight.from.id ? flight.from : airportOption"
                v-for="airportOption in airports"
                :key="airportOption.id"
              >
                {{ airportOption.code }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.to')" for="flight-to">To</label>
            <select class="form-control" id="flight-to" data-cy="to" name="to" v-model="flight.to">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="flight.to && airportOption.id === flight.to.id ? flight.to : airportOption"
                v-for="airportOption in airports"
                :key="airportOption.id"
              >
                {{ airportOption.code }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.flight.airline')" for="flight-airline">Airline</label>
            <select class="form-control" id="flight-airline" data-cy="airline" name="airline" v-model="flight.airline">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="flight.airline && airlineCompanyOption.id === flight.airline.id ? flight.airline : airlineCompanyOption"
                v-for="airlineCompanyOption in airlineCompanies"
                :key="airlineCompanyOption.id"
              >
                {{ airlineCompanyOption.code }}
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
            :disabled="$v.flight.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./flight-update.component.ts"></script>
