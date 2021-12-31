<template>
  <div>
    <h2 id="page-heading" data-cy="CarRentalCompanyHeading">
      <span v-text="$t('travelTutorialEntities2App.carRentalCompany.home.title')" id="car-rental-company-heading"
        >Car Rental Companies</span
      >
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('travelTutorialEntities2App.carRentalCompany.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CarRentalCompanyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-car-rental-company"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('travelTutorialEntities2App.carRentalCompany.home.createLabel')"> Create a new Car Rental Company </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && carRentalCompanies && carRentalCompanies.length === 0">
      <span v-text="$t('travelTutorialEntities2App.carRentalCompany.home.notFound')">No carRentalCompanies found</span>
    </div>
    <div class="table-responsive" v-if="carRentalCompanies && carRentalCompanies.length > 0">
      <table class="table table-striped" aria-describedby="carRentalCompanies">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('travelTutorialEntities2App.carRentalCompany.name')">Name</span></th>
            <th scope="row"><span v-text="$t('travelTutorialEntities2App.carRentalCompany.code')">Code</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="carRentalCompany in carRentalCompanies" :key="carRentalCompany.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CarRentalCompanyView', params: { carRentalCompanyId: carRentalCompany.id } }">{{
                carRentalCompany.id
              }}</router-link>
            </td>
            <td>{{ carRentalCompany.name }}</td>
            <td>{{ carRentalCompany.code }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CarRentalCompanyView', params: { carRentalCompanyId: carRentalCompany.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CarRentalCompanyEdit', params: { carRentalCompanyId: carRentalCompany.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(carRentalCompany)"
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
          id="travelTutorialEntities2App.carRentalCompany.delete.question"
          data-cy="carRentalCompanyDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-carRentalCompany-heading"
          v-text="$t('travelTutorialEntities2App.carRentalCompany.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Car Rental Company?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-carRentalCompany"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCarRentalCompany()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./car-rental-company.component.ts"></script>
