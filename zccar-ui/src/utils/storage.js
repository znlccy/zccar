const USER_INFO_KEY = 'car_user_info'

// 获取个人信息
export const getUserInfo = () => {
    const result = localStorage.getItem(USER_INFO_KEY)
    return result ? JSON.parse(result) : {
        token: '',
        userId:''
    }
}

// 设置个人信息
export const setUserInfo = (info) => {
    localStorage.setItem(USER_INFO_KEY, JSON.stringify(info))
}

// 移出个人信息
export const removeUserInfo = () => {
    localStorage.removeItem(USER_INFO_KEY)
}