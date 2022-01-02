import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'airport',
        data: { pageTitle: 'travelTutorialCompleteApp.airport.home.title' },
        loadChildren: () => import('./airport/airport.module').then(m => m.AirportModule),
      },
      {
        path: 'airline-company',
        data: { pageTitle: 'travelTutorialCompleteApp.airlineCompany.home.title' },
        loadChildren: () => import('./airline-company/airline-company.module').then(m => m.AirlineCompanyModule),
      },
      {
        path: 'flight',
        data: { pageTitle: 'travelTutorialCompleteApp.flight.home.title' },
        loadChildren: () => import('./flight/flight.module').then(m => m.FlightModule),
      },
      {
        path: 'hotel-company',
        data: { pageTitle: 'travelTutorialCompleteApp.hotelCompany.home.title' },
        loadChildren: () => import('./hotel-company/hotel-company.module').then(m => m.HotelCompanyModule),
      },
      {
        path: 'hotel-room',
        data: { pageTitle: 'travelTutorialCompleteApp.hotelRoom.home.title' },
        loadChildren: () => import('./hotel-room/hotel-room.module').then(m => m.HotelRoomModule),
      },
      {
        path: 'car-rental-company',
        data: { pageTitle: 'travelTutorialCompleteApp.carRentalCompany.home.title' },
        loadChildren: () => import('./car-rental-company/car-rental-company.module').then(m => m.CarRentalCompanyModule),
      },
      {
        path: 'car',
        data: { pageTitle: 'travelTutorialCompleteApp.car.home.title' },
        loadChildren: () => import('./car/car.module').then(m => m.CarModule),
      },
      {
        path: 'traveler',
        data: { pageTitle: 'travelTutorialCompleteApp.traveler.home.title' },
        loadChildren: () => import('./traveler/traveler.module').then(m => m.TravelerModule),
      },
      {
        path: 'travel-plan',
        data: { pageTitle: 'travelTutorialCompleteApp.travelPlan.home.title' },
        loadChildren: () => import('./travel-plan/travel-plan.module').then(m => m.TravelPlanModule),
      },
      {
        path: 'task-select-hotel',
        data: { pageTitle: 'travelTutorialCompleteApp.taskSelectHotel.home.title' },
        loadChildren: () => import('./task-select-hotel/task-select-hotel.module').then(m => m.TaskSelectHotelModule),
      },
      {
        path: 'task-select-flight',
        data: { pageTitle: 'travelTutorialCompleteApp.taskSelectFlight.home.title' },
        loadChildren: () => import('./task-select-flight/task-select-flight.module').then(m => m.TaskSelectFlightModule),
      },
      {
        path: 'task-select-car',
        data: { pageTitle: 'travelTutorialCompleteApp.taskSelectCar.home.title' },
        loadChildren: () => import('./task-select-car/task-select-car.module').then(m => m.TaskSelectCarModule),
      },
      {
        path: 'travel-plan-start-form',
        data: { pageTitle: 'travelTutorialCompleteApp.travelPlanStartForm.home.title' },
        loadChildren: () => import('./travel-plan-start-form/travel-plan-start-form.module').then(m => m.TravelPlanStartFormModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
