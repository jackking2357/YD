import request from '@/utils/request'

/**
 * @description 平台用户机器人(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountRobotList (data = {}) {
  return request({
    url: '/yudian-back//account-robot/getAccountRobotList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人(明细)
 * @param {Object} businessResultPlanId 平台用户机器人id
 */
export function getAccountRobotDetail (accountRobotId) {
  return request({
    url: `/yudian-back//account-robot/getAccountRobotDetail/${accountRobotId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户机器人(添加)
 * @param {Object} data 携带的数据
 */
export function accountRobotAdd (data = {}) {
  return request({
    url: '/yudian-back//account-robot/accountRobotAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人(编辑)
 * @param {Object} data 携带的数据
 */
export function accountRobotEdit (data = {}) {
  return request({
    url: '/yudian-back//account-robot/accountRobotEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户机器人(删除)
 * @param {Object} accountRobotIds 平台用户机器人id
 */
export function accountRobotRemove (accountRobotIds) {
  return request({
    url: `/yudian-back//account-robot/accountRobotRemove/${accountRobotIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户机器人(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountRobotExport( data = {}) {
  return request({
    url: `/yudian-back//account-robot/accountRobotExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
