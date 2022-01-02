import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICar } from '@/shared/model/car.model';
import CarService from './car.service';

@Component
export default class CarDetails extends Vue {
  @Inject('carService') private carService: () => CarService;
  public car: ICar = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carId) {
        vm.retrieveCar(to.params.carId);
      }
    });
  }

  public retrieveCar(carId) {
    this.carService()
      .find(carId)
      .then(res => {
        this.car = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
