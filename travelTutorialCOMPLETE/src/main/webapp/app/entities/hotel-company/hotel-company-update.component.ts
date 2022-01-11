import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import { IHotelCompany, HotelCompany } from '@/shared/model/hotel-company.model';
import HotelCompanyService from './hotel-company.service';

const validations: any = {
  hotelCompany: {
    name: {
      required,
      minLength: minLength(2),
      maxLength: maxLength(20),
    },
    place: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class HotelCompanyUpdate extends Vue {
  @Inject('hotelCompanyService') private hotelCompanyService: () => HotelCompanyService;
  public hotelCompany: IHotelCompany = new HotelCompany();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hotelCompanyId) {
        vm.retrieveHotelCompany(to.params.hotelCompanyId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.hotelCompany.id) {
      this.hotelCompanyService()
        .update(this.hotelCompany)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.hotelCompany.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.hotelCompanyService()
        .create(this.hotelCompany)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.hotelCompany.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveHotelCompany(hotelCompanyId): void {
    this.hotelCompanyService()
      .find(hotelCompanyId)
      .then(res => {
        this.hotelCompany = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
