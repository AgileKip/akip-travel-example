import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHandleCancel } from '@/shared/model/handle-cancel.model';
import HandleCancelService from './handle-cancel.service';

@Component
export default class HandleCancelDetails extends Vue {
  @Inject('handleCancelService') private handleCancelService: () => HandleCancelService;
  public handleCancel: IHandleCancel = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.handleCancelId) {
        vm.retrieveHandleCancel(to.params.handleCancelId);
      }
    });
  }

  public retrieveHandleCancel(handleCancelId) {
    this.handleCancelService()
      .find(handleCancelId)
      .then(res => {
        this.handleCancel = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
