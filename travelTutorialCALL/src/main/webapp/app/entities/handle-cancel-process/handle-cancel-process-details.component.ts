import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHandleCancelProcess } from '@/shared/model/handle-cancel-process.model';
import HandleCancelProcessService from './handle-cancel-process.service';

@Component
export default class HandleCancelProcessDetailsComponent extends Vue {
  @Inject('handleCancelProcessService') private handleCancelProcessService: () => HandleCancelProcessService;
  public handleCancelProcess: IHandleCancelProcess = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveHandleCancelProcess(to.params.processInstanceId);
      }
    });
  }

  public retrieveHandleCancelProcess(handleCancelProcessId) {
    this.isFetching = true;
    this.handleCancelProcessService()
      .find(handleCancelProcessId)
      .then(
        res => {
          this.handleCancelProcess = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
