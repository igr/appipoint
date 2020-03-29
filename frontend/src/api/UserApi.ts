import http from '@/utils/http';

class UserApi {
  login = (data: { email: string, password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
    errorHandle: false,
  });

  async getUserInfo(param: {}) {
    return {
      data: {
        user: {
          roles: ['doctor'], name: 'Pera', avatar: '', introduction: '', email: '',
        },
      },
    };
  }

  async logout() {
    return '';
  }
}

export default new UserApi();
