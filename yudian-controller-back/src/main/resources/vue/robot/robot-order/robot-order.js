import request from '@/utils/request'

/**
 * @description 机器人订单(列表)
 * @param {Object} data 携带的数据
 */
export function getRobotOrderList (data = {}) {
  return request({
    url: '/yudian-back//robot-order/getRobotOrderList',
    method: 'post',
    data
  })
}

/**
 * @description 机器人订单(明细)
 * @param {Object} businessResultPlanId 机器人订单id
 */
export function getRobotOrderDetail (robotOrderId) {
  return request({
    url: `/yudian-back//robot-order/getRobotOrderDetail/${robotOrderId}`,
    method: 'get'
  })
}

/**
 * @description 机器人订单(添加)
 * @param {Object} data 携带的数据
 */
export function robotOrderAdd (data = {}) {
  return request({
    url: '/yudian-back//robot-order/robotOrderAdd',
    method: 'post',
    data
  })
}

/**
 * @description 机器人订单(编辑)
 * @param {Object} data 携带的数据
 */
export function robotOrderEdit (data = {}) {
  return request({
    url: '/yudian-back//robot-order/robotOrderEdit',
    method: 'put',
    data
  })
}

/**
 * @description 机器人订单(删除)
 * @param {Object} robotOrderIds 机器人订单id
 */
export function robotOrderRemove (robotOrderIds) {
  return request({
    url: `/yudian-back//robot-order/robotOrderRemove/${robotOrderIds}`,
    method: 'delete'
  })
}

/**
 * @description 机器人订单(excel导出)
 * @param {Object} data 携带的数据
 */
export function robotOrderExport( data = {}) {
  return request({
    url: `/yudian-back//robot-order/robotOrderExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
