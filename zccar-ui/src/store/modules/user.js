import { setUserInfo, getUserInfo } from '@/utils/storage'

export default {
    namespaced: true,
    state() {
        return {
            userInfo: getUserInfo()
        }
    },
    mutations: {
        setUserInfo(state, obj) {
            state.userInfo = obj
            // 持久化用户信息
            setUserInfo(obj)
        }
    },
    actions: {}
}