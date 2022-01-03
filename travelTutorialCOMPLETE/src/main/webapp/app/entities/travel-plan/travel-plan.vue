<template>
  <div>
    <h2 id="page-heading" data-cy="TravelPlanHeading">
      <span v-text="$t('travelTutorialCompleteApp.travelPlan.home.title')" id="travel-plan-heading">Travel Plans</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCompleteApp.travelPlan.home.refreshListLabel')">Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && travelPlans && travelPlans.length === 0">
      <span v-text="$t('travelTutorialCompleteApp.travelPlan.home.notFound')">No travelPlans found</span>
    </div>
    <div class="table-responsive" v-if="travelPlans && travelPlans.length > 0">
      <table class="table table-striped" aria-describedby="travelPlans">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.name')">Name</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.startDate')">Start Date</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.endDate')">End Date</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.numPax')">Num Pax</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.price')">Price</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.payment')">Payment</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.hotelDuration')">Hotel Duration</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.carDuration')">Car Duration</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.status')">Status</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.proceedToCheckOut')">Proceed To Check Out</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.car')">Car</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.flight')">Flight</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.travelPlan.hotelRoom')">Hotel Room</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="travelPlan in travelPlans" :key="travelPlan.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TravelPlanView', params: { travelPlanId: travelPlan.id } }">{{ travelPlan.id }}</router-link>
            </td>
            <td>{{ travelPlan.name }}</td>
            <td>{{ travelPlan.startDate }}</td>
            <td>{{ travelPlan.endDate }}</td>
            <td>{{ travelPlan.numPax }}</td>
            <td>{{ travelPlan.price }}</td>
            <td>{{ travelPlan.payment }}</td>
            <td>{{ travelPlan.hotelDuration }}</td>
            <td>{{ travelPlan.carDuration }}</td>
            <td v-text="$t('travelTutorialCompleteApp.PlanStatus.' + travelPlan.status)">{{ travelPlan.status }}</td>
            <td>{{ travelPlan.proceedToCheckOut }}</td>
            <td>
              <div v-if="travelPlan.car">
                <router-link :to="{ name: 'CarView', params: { carId: travelPlan.car.id } }">{{ travelPlan.car.code }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="travelPlan.flight">
                <router-link :to="{ name: 'FlightView', params: { flightId: travelPlan.flight.id } }">{{
                  travelPlan.flight.code
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="travelPlan.hotelRoom">
                <router-link :to="{ name: 'HotelRoomView', params: { hotelRoomId: travelPlan.hotelRoom.id } }">{{
                  travelPlan.hotelRoom.code
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TravelPlanView', params: { travelPlanId: travelPlan.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="travelTutorialCompleteApp.travelPlan.delete.question"
          data-cy="travelPlanDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-travelPlan-heading" v-text="$t('travelTutorialCompleteApp.travelPlan.delete.question', { id: removeId })">
          Are you sure you want to delete this Travel Plan?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-travelPlan"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTravelPlan()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./travel-plan.component.ts"></script>
