import request from '@/utils/request'

// 用户登录
export const userLogin = (user) => {
    return request.post('/user/login', user);
}

// 用户验证码
export const userCaptcha = () => {
    return request({
        url: '/captcha?rand=' + Math.random() * (10 - 0),
        method: 'GET',
        responseType: 'blob'
    })
}