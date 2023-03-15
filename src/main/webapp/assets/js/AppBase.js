class AppBase {
    static DOMAIN_SERVER = location.origin;

    static API_SERVER = this.DOMAIN_SERVER + '/api';

    static API_CUSTOMER = this.API_SERVER + '/customers';

    static API_DEPOSIT = this.API_CUSTOMER + '/deposit';

    static API_TRANSFER = this.API_SERVER + '/transfers';
}