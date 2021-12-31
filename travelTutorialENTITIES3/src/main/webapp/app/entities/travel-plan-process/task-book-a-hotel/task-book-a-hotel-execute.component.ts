import { Component, Vue, Inject } from 'vue-property-decorator';

import HotelCompanyService from '@/entities/hotel-company/hotel-company.service';
import { IHotelCompany } from '@/shared/model/hotel-company.model';

import TaskBookAHotelService from './task-book-a-hotel.service';
import { TaskBookAHotelContext } from './task-book-a-hotel.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        hotelBookingNumber: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskBookAHotelExecuteComponent extends Vue {
  private taskBookAHotelService: TaskBookAHotelService = new TaskBookAHotelService();
  private taskContext: TaskBookAHotelContext = {};

  @Inject('hotelCompanyService') private hotelCompanyService: () => HotelCompanyService;

  public hotelCompanies: IHotelCompany[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
      vm.initRelationships();
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskBookAHotelService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskBookAHotelService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {
    this.hotelCompanyService()
      .retrieve()
      .then(res => {
        this.hotelCompanies = res.data;
      });
  }
}
