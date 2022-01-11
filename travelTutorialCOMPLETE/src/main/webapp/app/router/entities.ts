import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const TravelPlan = () => import('@/entities/travel-plan/travel-plan.vue');
// prettier-ignore
const TravelPlanDetails = () => import('@/entities/travel-plan/travel-plan-details.vue');
// prettier-ignore
const Airport = () => import('@/entities/airport/airport.vue');
// prettier-ignore
const AirportUpdate = () => import('@/entities/airport/airport-update.vue');
// prettier-ignore
const AirportDetails = () => import('@/entities/airport/airport-details.vue');
// prettier-ignore
const Flight = () => import('@/entities/flight/flight.vue');
// prettier-ignore
const FlightUpdate = () => import('@/entities/flight/flight-update.vue');
// prettier-ignore
const FlightDetails = () => import('@/entities/flight/flight-details.vue');
// prettier-ignore
const HotelRoom = () => import('@/entities/hotel-room/hotel-room.vue');
// prettier-ignore
const HotelRoomUpdate = () => import('@/entities/hotel-room/hotel-room-update.vue');
// prettier-ignore
const HotelRoomDetails = () => import('@/entities/hotel-room/hotel-room-details.vue');
// prettier-ignore
const Car = () => import('@/entities/car/car.vue');
// prettier-ignore
const CarUpdate = () => import('@/entities/car/car-update.vue');
// prettier-ignore
const CarDetails = () => import('@/entities/car/car-details.vue');
// prettier-ignore
const Traveler = () => import('@/entities/traveler/traveler.vue');
// prettier-ignore
const TravelerUpdate = () => import('@/entities/traveler/traveler-update.vue');
// prettier-ignore
const TravelerDetails = () => import('@/entities/traveler/traveler-details.vue');
// prettier-ignore
const CarRentalCompany = () => import('@/entities/car-rental-company/car-rental-company.vue');
// prettier-ignore
const CarRentalCompanyUpdate = () => import('@/entities/car-rental-company/car-rental-company-update.vue');
// prettier-ignore
const CarRentalCompanyDetails = () => import('@/entities/car-rental-company/car-rental-company-details.vue');
// prettier-ignore
const AirlineCompany = () => import('@/entities/airline-company/airline-company.vue');
// prettier-ignore
const AirlineCompanyUpdate = () => import('@/entities/airline-company/airline-company-update.vue');
// prettier-ignore
const AirlineCompanyDetails = () => import('@/entities/airline-company/airline-company-details.vue');
// prettier-ignore
const HotelCompany = () => import('@/entities/hotel-company/hotel-company.vue');
// prettier-ignore
const HotelCompanyUpdate = () => import('@/entities/hotel-company/hotel-company-update.vue');
// prettier-ignore
const HotelCompanyDetails = () => import('@/entities/hotel-company/hotel-company-details.vue');
// prettier-ignore
const TravelPlanStartFormInit = () => import('@/entities/travel-plan-process/travel-plan-start-form-init.vue');
// prettier-ignore
const TravelPlanProcessDetails = () => import('@/entities/travel-plan-process/travel-plan-process-details.vue');
// prettier-ignore
const TravelPlanProcessList = () => import('@/entities/travel-plan-process/travel-plan-process-list.vue');
// prettier-ignore
const TravelPlanProcess_TaskPaymentDetailsDetails = () => import('@/entities/travel-plan-process/task-payment-details/task-payment-details-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskPaymentDetailsExecute = () => import('@/entities/travel-plan-process/task-payment-details/task-payment-details-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseCarDetails = () => import('@/entities/travel-plan-process/task-choose-car/task-choose-car-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseCarExecute = () => import('@/entities/travel-plan-process/task-choose-car/task-choose-car-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseHotelRoomDetails = () => import('@/entities/travel-plan-process/task-choose-hotel-room/task-choose-hotel-room-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseHotelRoomExecute = () => import('@/entities/travel-plan-process/task-choose-hotel-room/task-choose-hotel-room-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskProceedCheckoutDetails = () => import('@/entities/travel-plan-process/task-proceed-checkout/task-proceed-checkout-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskProceedCheckoutExecute = () => import('@/entities/travel-plan-process/task-proceed-checkout/task-proceed-checkout-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseFlightDetails = () => import('@/entities/travel-plan-process/task-choose-flight/task-choose-flight-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskChooseFlightExecute = () => import('@/entities/travel-plan-process/task-choose-flight/task-choose-flight-execute.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/travel-plan',
    name: 'TravelPlan',
    component: TravelPlan,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/travel-plan/:travelPlanId/view',
    name: 'TravelPlanView',
    component: TravelPlanDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airport',
    name: 'Airport',
    component: Airport,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airport/new',
    name: 'AirportCreate',
    component: AirportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airport/:airportId/edit',
    name: 'AirportEdit',
    component: AirportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airport/:airportId/view',
    name: 'AirportView',
    component: AirportDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/flight',
    name: 'Flight',
    component: Flight,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/flight/new',
    name: 'FlightCreate',
    component: FlightUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/flight/:flightId/edit',
    name: 'FlightEdit',
    component: FlightUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/flight/:flightId/view',
    name: 'FlightView',
    component: FlightDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-room',
    name: 'HotelRoom',
    component: HotelRoom,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-room/new',
    name: 'HotelRoomCreate',
    component: HotelRoomUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-room/:hotelRoomId/edit',
    name: 'HotelRoomEdit',
    component: HotelRoomUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-room/:hotelRoomId/view',
    name: 'HotelRoomView',
    component: HotelRoomDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car',
    name: 'Car',
    component: Car,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car/new',
    name: 'CarCreate',
    component: CarUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car/:carId/edit',
    name: 'CarEdit',
    component: CarUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car/:carId/view',
    name: 'CarView',
    component: CarDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/traveler',
    name: 'Traveler',
    component: Traveler,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/traveler/new',
    name: 'TravelerCreate',
    component: TravelerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/traveler/:travelerId/edit',
    name: 'TravelerEdit',
    component: TravelerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/traveler/:travelerId/view',
    name: 'TravelerView',
    component: TravelerDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car-rental-company',
    name: 'CarRentalCompany',
    component: CarRentalCompany,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car-rental-company/new',
    name: 'CarRentalCompanyCreate',
    component: CarRentalCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car-rental-company/:carRentalCompanyId/edit',
    name: 'CarRentalCompanyEdit',
    component: CarRentalCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/car-rental-company/:carRentalCompanyId/view',
    name: 'CarRentalCompanyView',
    component: CarRentalCompanyDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airline-company',
    name: 'AirlineCompany',
    component: AirlineCompany,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airline-company/new',
    name: 'AirlineCompanyCreate',
    component: AirlineCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airline-company/:airlineCompanyId/edit',
    name: 'AirlineCompanyEdit',
    component: AirlineCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/airline-company/:airlineCompanyId/view',
    name: 'AirlineCompanyView',
    component: AirlineCompanyDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-company',
    name: 'HotelCompany',
    component: HotelCompany,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-company/new',
    name: 'HotelCompanyCreate',
    component: HotelCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-company/:hotelCompanyId/edit',
    name: 'HotelCompanyEdit',
    component: HotelCompanyUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/hotel-company/:hotelCompanyId/view',
    name: 'HotelCompanyView',
    component: HotelCompanyDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/init',
    name: 'TravelPlanStartFormInit',
    component: TravelPlanStartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/instance/:processInstanceId/view',
    name: 'TravelPlanProcessView',
    component: TravelPlanProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/instances',
    name: 'TravelPlanProcessList',
    component: TravelPlanProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskPaymentDetails/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskPaymentDetailsDetails',
    component: TravelPlanProcess_TaskPaymentDetailsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskPaymentDetails/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskPaymentDetailsExecute',
    component: TravelPlanProcess_TaskPaymentDetailsExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseCar/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskChooseCarDetails',
    component: TravelPlanProcess_TaskChooseCarDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseCar/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskChooseCarExecute',
    component: TravelPlanProcess_TaskChooseCarExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseHotel/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskChooseHotelRoomDetails',
    component: TravelPlanProcess_TaskChooseHotelRoomDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseHotel/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskChooseHotelRoomExecute',
    component: TravelPlanProcess_TaskChooseHotelRoomExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskProceedCheckout/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskProceedCheckoutDetails',
    component: TravelPlanProcess_TaskProceedCheckoutDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskProceedCheckout/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskProceedCheckoutExecute',
    component: TravelPlanProcess_TaskProceedCheckoutExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseFlight/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskChooseFlightDetails',
    component: TravelPlanProcess_TaskChooseFlightDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseFlight/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskChooseFlightExecute',
    component: TravelPlanProcess_TaskChooseFlightExecute,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
