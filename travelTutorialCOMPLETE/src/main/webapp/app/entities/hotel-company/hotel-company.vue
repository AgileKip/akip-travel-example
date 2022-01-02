<template>
  <div>
    <h2 id="page-heading" data-cy="HotelCompanyHeading">
      <span v-text="$t('travelTutorialCompleteApp.hotelCompany.home.title')" id="hotel-company-heading">Hotel Companies</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialCompleteApp.hotelCompany.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'HotelCompanyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-hotel-company"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('travelTutorialCompleteApp.hotelCompany.home.createLabel')"> Create a new Hotel Company </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hotelCompanies && hotelCompanies.length === 0">
      <span v-text="$t('travelTutorialCompleteApp.hotelCompany.home.notFound')">No hotelCompanies found</span>
    </div>
    <div class="table-responsive" v-if="hotelCompanies && hotelCompanies.length > 0">
      <table class="table table-striped" aria-describedby="hotelCompanies">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelCompany.name')">Name</span></th>
            <th scope="row"><span v-text="$t('travelTutorialCompleteApp.hotelCompany.place')">Place</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hotelCompany in hotelCompanies" :key="hotelCompany.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HotelCompanyView', params: { hotelCompanyId: hotelCompany.id } }">{{
                hotelCompany.id
              }}</router-link>
            </td>
            <td>{{ hotelCompany.name }}</td>
            <td>{{ hotelCompany.place }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HotelCompanyView', params: { hotelCompanyId: hotelCompany.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HotelCompanyEdit', params: { hotelCompanyId: hotelCompany.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(hotelCompany)"
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
          id="travelTutorialCompleteApp.hotelCompany.delete.question"
          data-cy="hotelCompanyDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-hotelCompany-heading" v-text="$t('travelTutorialCompleteApp.hotelCompany.delete.question', { id: removeId })">
          Are you sure you want to delete this Hotel Company?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-hotelCompany"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeHotelCompany()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./hotel-company.component.ts"></script>
