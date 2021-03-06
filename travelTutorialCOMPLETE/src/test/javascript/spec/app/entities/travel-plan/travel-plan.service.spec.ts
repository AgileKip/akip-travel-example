/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import TravelPlanService from '@/entities/travel-plan/travel-plan.service';
import { TravelPlan } from '@/shared/model/travel-plan.model';
import { PlanStatus } from '@/shared/model/enumerations/plan-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('TravelPlan Service', () => {
    let service: TravelPlanService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new TravelPlanService();
      currentDate = new Date();
      elemDefault = new TravelPlan(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0,
        'AAAAAAA',
        currentDate,
        0,
        currentDate,
        0,
        PlanStatus.UNKNOWN,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: dayjs(currentDate).format(DATE_FORMAT),
            endDate: dayjs(currentDate).format(DATE_FORMAT),
            hotelStartDate: dayjs(currentDate).format(DATE_FORMAT),
            carStartDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of TravelPlan', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            startDate: dayjs(currentDate).format(DATE_FORMAT),
            endDate: dayjs(currentDate).format(DATE_FORMAT),
            numPax: 1,
            price: 1,
            payment: 'BBBBBB',
            hotelStartDate: dayjs(currentDate).format(DATE_FORMAT),
            hotelDuration: 1,
            carStartDate: dayjs(currentDate).format(DATE_FORMAT),
            carDuration: 1,
            status: 'BBBBBB',
            proceedToCheckOut: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
            hotelStartDate: currentDate,
            carStartDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of TravelPlan', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
