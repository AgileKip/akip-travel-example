import { Component, Vue } from 'vue-property-decorator';

@Component
export default class NotifyMixin extends Vue {
  public notifyFromResponse(response: any, title: string, variant: string) {
    const message = this.getAlertMessageFromResponse(response);
    this.$root.$bvToast.toast(message.toString(), {
      toaster: 'b-toaster-top-center',
      title: title,
      variant: variant,
      solid: true,
      autoHideDelay: 10000,
    });
  }

  public notifySuccessFromResponse(response: any) {
    this.notifyFromResponse(response, 'Success', 'success');
  }

  public notifyErrorFromResponse(response: any) {
    const message = this.getErrorMessageFromResponse(response);
    this.$root.$bvToast.toast(message.toString(), {
      toaster: 'b-toaster-top-center',
      title: 'Erro',
      variant: 'danger',
      solid: true,
      autoHideDelay: 10000,
    });
  }

  public notifyError(messageKey: string) {
    const message = this.$t(messageKey);
    this.$root.$bvToast.toast(message.toString(), {
      toaster: 'b-toaster-top-center',
      title: 'Erro',
      variant: 'danger',
      solid: true,
      autoHideDelay: 10000,
    });
  }

  public getErrorMessageFromResponse(error: any): any {
    const paramLength: number = error.response.headers['x-traveltutorialmkrapp-param-length'];
    if (!paramLength || paramLength == 0) {
      return this.$t(error.response.headers['x-traveltutorialmkrapp-error']);
    }

    if (paramLength == 1) {
      return this.$t(error.response.headers['x-traveltutorialmkrapp-error'], {
        param: decodeURIComponent(error.response.headers['x-traveltutorialmkrapp-param'].replace(/\+/g, ' ')),
      });
    }

    const params = {};
    for (let i = 0; i < paramLength; i++) {
      params['param' + i] = decodeURIComponent(error.response.headers['x-traveltutorialmkrapp-param' + i].replace(/\+/g, ' '));
    }
    return this.$t(error.response.headers['x-traveltutorialmkrapp-error'], params);
  }

  public getAlertMessageFromResponse(response: any): any {
    return this.$t(response.headers['x-traveltutorialmkrapp-alert'], {
      param: decodeURIComponent(response.headers['x-traveltutorialmkrapp-params'].replace(/\+/g, ' ')),
    });
  }
}
