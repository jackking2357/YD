import request from '@/utils/request'

/**
 * @description 平台用户机器人加速器(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountRobotAcceleratorList (data = {}) {
  return request({
    url: '/yudian-back//account-robot-accelerator/getAccountRobotAcceleratorList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人加速器(明细)
 * @param {Object} businessResultPlanId 平台用户机器人加速器id
 */
export function getAccountRobotAcceleratorDetail (accountRobotAcceleratorId) {
  return request({
    url: `/yudian-back//account-robot-accelerator/getAccountRobotAcceleratorDetail/${accountRobotAcceleratorId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户机器人加速器(添加)
 * @param {Object} data 携带的数据
 */
export function accountRobotAcceleratorAdd (data = {}) {
  return request({
    url: '/yudian-back//account-robot-accelerator/accountRobotAcceleratorAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人加速器(编辑)
 * @param {Object} data 携带的数据
 */
export function accountRobotAcceleratorEdit (data = {}) {
  return request({
    url: '/yudian-back//account-robot-accelerator/accountRobotAcceleratorEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户机器人加速器(删除)
 * @param {Object} accountRobotAcceleratorIds 平台用户机器人加速器id
 */
export function accountRobotAcceleratorRemove (accountRobotAcceleratorIds) {
  return request({
    url: `/yudian-back//account-robot-accelerator/accountRobotAcceleratorRemove/${accountRobotAcceleratorIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户机器人加速器(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountRobotAcceleratorExport( data = {}) {
  return request({
    url: `/yudian-back//account-robot-accelerator/accountRobotAcceleratorExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
