import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const TravelPlan = () => import('@/entities/travel-plan/travel-plan.vue');
// prettier-ignore
const TravelPlanDetails = () => import('@/entities/travel-plan/travel-plan-details.vue');
// prettier-ignore
const TravelPlanStartFormInit = () => import('@/entities/travel-plan-process/travel-plan-start-form-init.vue');
// prettier-ignore
const TravelPlanProcessDetails = () => import('@/entities/travel-plan-process/travel-plan-process-details.vue');
// prettier-ignore
const TravelPlanProcessList = () => import('@/entities/travel-plan-process/travel-plan-process-list.vue');
// prettier-ignore
const TravelPlanProcess_TaskBookAHotelDetails = () => import('@/entities/travel-plan-process/task-book-a-hotel/task-book-a-hotel-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskBookAHotelExecute = () => import('@/entities/travel-plan-process/task-book-a-hotel/task-book-a-hotel-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskBuyFlightTicketsDetails = () => import('@/entities/travel-plan-process/task-buy-flight-tickets/task-buy-flight-tickets-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskBuyFlightTicketsExecute = () => import('@/entities/travel-plan-process/task-buy-flight-tickets/task-buy-flight-tickets-execute.vue');
// prettier-ignore
const TravelPlanProcess_TaskRentACarDetails = () => import('@/entities/travel-plan-process/task-rent-a-car/task-rent-a-car-details.vue');
// prettier-ignore
const TravelPlanProcess_TaskRentACarExecute = () => import('@/entities/travel-plan-process/task-rent-a-car/task-rent-a-car-execute.vue');
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
    path: '/process-definition/TravelPlanProcessENTITIES2/init',
    name: 'TravelPlanStartFormInit',
    component: TravelPlanStartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/instance/:processInstanceId/view',
    name: 'TravelPlanProcessView',
    component: TravelPlanProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/instances',
    name: 'TravelPlanProcessList',
    component: TravelPlanProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskHotel/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskBookAHotelDetails',
    component: TravelPlanProcess_TaskBookAHotelDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskHotel/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskBookAHotelExecute',
    component: TravelPlanProcess_TaskBookAHotelExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskFlight/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskBuyFlightTicketsDetails',
    component: TravelPlanProcess_TaskBuyFlightTicketsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskFlight/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskBuyFlightTicketsExecute',
    component: TravelPlanProcess_TaskBuyFlightTicketsExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskCar/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskRentACarDetails',
    component: TravelPlanProcess_TaskRentACarDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessENTITIES2/task/TaskCar/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskRentACarExecute',
    component: TravelPlanProcess_TaskRentACarExecute,
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
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
