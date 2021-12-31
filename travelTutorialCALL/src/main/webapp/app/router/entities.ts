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
const HandleCancel = () => import('@/entities/handle-cancel/handle-cancel.vue');
// prettier-ignore
const HandleCancelDetails = () => import('@/entities/handle-cancel/handle-cancel-details.vue');
// prettier-ignore
const HandleCancelProcessDetails = () => import('@/entities/handle-cancel-process/handle-cancel-process-details.vue');
// prettier-ignore
const HandleCancelProcessList = () => import('@/entities/handle-cancel-process/handle-cancel-process-list.vue');
// prettier-ignore
const HandleCancelProcess_HandleCancelTaskDetails = () => import('@/entities/handle-cancel-process/handle-cancel-task/handle-cancel-task-details.vue');
// prettier-ignore
const HandleCancelProcess_HandleCancelTaskExecute = () => import('@/entities/handle-cancel-process/handle-cancel-task/handle-cancel-task-execute.vue');
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
    path: '/process-definition/TravelPlanProcessCALL/init',
    name: 'TravelPlanStartFormInit',
    component: TravelPlanStartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/instance/:processInstanceId/view',
    name: 'TravelPlanProcessView',
    component: TravelPlanProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/instances',
    name: 'TravelPlanProcessList',
    component: TravelPlanProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskHotel/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskBookAHotelDetails',
    component: TravelPlanProcess_TaskBookAHotelDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskHotel/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskBookAHotelExecute',
    component: TravelPlanProcess_TaskBookAHotelExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskFlight/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskBuyFlightTicketsDetails',
    component: TravelPlanProcess_TaskBuyFlightTicketsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskFlight/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskBuyFlightTicketsExecute',
    component: TravelPlanProcess_TaskBuyFlightTicketsExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskCar/:taskInstanceId/view',
    name: 'TravelPlanProcess_TaskRentACarDetails',
    component: TravelPlanProcess_TaskRentACarDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/TravelPlanProcessCALL/task/TaskCar/:taskInstanceId/execute',
    name: 'TravelPlanProcess_TaskRentACarExecute',
    component: TravelPlanProcess_TaskRentACarExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/handle-cancel',
    name: 'HandleCancel',
    component: HandleCancel,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/handle-cancel/:handleCancelId/view',
    name: 'HandleCancelView',
    component: HandleCancelDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/HandleUserCancel/instance/:processInstanceId/view',
    name: 'HandleCancelProcessView',
    component: HandleCancelProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/HandleUserCancel/instances',
    name: 'HandleCancelProcessList',
    component: HandleCancelProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/HandleUserCancel/task/taskHandleCancel/:taskInstanceId/view',
    name: 'HandleCancelProcess_HandleCancelTaskDetails',
    component: HandleCancelProcess_HandleCancelTaskDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/HandleUserCancel/task/taskHandleCancel/:taskInstanceId/execute',
    name: 'HandleCancelProcess_HandleCancelTaskExecute',
    component: HandleCancelProcess_HandleCancelTaskExecute,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
