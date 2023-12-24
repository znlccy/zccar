import request from '@/utils/request'

// 查询餐饮车列表数据
export const carList = (pageNo, pageSize) => {
    return request.post(`/car/list/${pageNo}/${pageSize}` )
}

// 查询该餐饮车附近一公里的餐饮车
export const carNear = (carId, distance, size) => {
    return request.post(`/car/neighborhood/${carId}/${distance}/${size}`)
}