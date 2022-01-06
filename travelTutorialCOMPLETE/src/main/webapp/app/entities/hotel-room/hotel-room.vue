<template>
  <div>
    <h2 id="page-heading" data-cy="HotelRoomHeading">
      <span v-text="$t('travelTutorialCompleteApp.hotelRoom.home.title')" id="hotel-room-heading">Hotel Rooms</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCompleteApp.hotelRoom.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'HotelRoomCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-hotel-room"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('travelTutorialCompleteApp.hotelRoom.home.createLabel')"> Create a new Hotel Room </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hotelRooms && hotelRooms.length === 0">
      <span v-text="$t('travelTutorialCompleteApp.hotelRoom.home.notFound')">No hotelRooms found</span>
    </div>
    <div class="table-responsive" v-if="hotelRooms && hotelRooms.length > 0">
      <table class="table table-striped" aria-describedby="hotelRooms">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.roomID')">Room ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.sleeps')">Sleeps</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.boodked')">Boodked</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.duration')">Duration</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.price')">Price</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelRoom.hotelCo')">Hotel Co</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hotelRoom in hotelRooms" :key="hotelRoom.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HotelRoomView', params: { hotelRoomId: hotelRoom.id } }">{{ hotelRoom.id }}</router-link>
            </td>
            <td>{{ hotelRoom.roomID }}</td>
            <td>{{ hotelRoom.sleeps }}</td>
            <td>{{ hotelRoom.boodked }}</td>
            <td>{{ hotelRoom.duration }}</td>
            <td>{{ hotelRoom.price }}</td>
            <td>
              <div v-if="hotelRoom.hotelCo">
                <router-link :to="{ name: 'HotelCompanyView', params: { hotelCompanyId: hotelRoom.hotelCo.id } }">{{
                  hotelRoom.hotelCo.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HotelRoomView', params: { hotelRoomId: hotelRoom.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HotelRoomEdit', params: { hotelRoomId: hotelRoom.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(hotelRoom)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="travelTutorialCompleteApp.hotelRoom.delete.question"
          data-cy="hotelRoomDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-hotelRoom-heading" v-text="$t('travelTutorialCompleteApp.hotelRoom.delete.question', { id: removeId })">
          Are you sure you want to delete this Hotel Room?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-hotelRoom"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeHotelRoom()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./hotel-room.component.ts"></script>
