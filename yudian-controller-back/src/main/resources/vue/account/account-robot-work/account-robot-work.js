import request from '@/utils/request'

/**
 * @description 平台用户机器人工作记录(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountRobotWorkList (data = {}) {
  return request({
    url: '/yudian-back//account-robot-work/getAccountRobotWorkList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人工作记录(明细)
 * @param {Object} businessResultPlanId 平台用户机器人工作记录id
 */
export function getAccountRobotWorkDetail (accountRobotWorkId) {
  return request({
    url: `/yudian-back//account-robot-work/getAccountRobotWorkDetail/${accountRobotWorkId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户机器人工作记录(添加)
 * @param {Object} data 携带的数据
 */
export function accountRobotWorkAdd (data = {}) {
  return request({
    url: '/yudian-back//account-robot-work/accountRobotWorkAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户机器人工作记录(编辑)
 * @param {Object} data 携带的数据
 */
export function accountRobotWorkEdit (data = {}) {
  return request({
    url: '/yudian-back//account-robot-work/accountRobotWorkEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户机器人工作记录(删除)
 * @param {Object} accountRobotWorkIds 平台用户机器人工作记录id
 */
export function accountRobotWorkRemove (accountRobotWorkIds) {
  return request({
    url: `/yudian-back//account-robot-work/accountRobotWorkRemove/${accountRobotWorkIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户机器人工作记录(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountRobotWorkExport( data = {}) {
  return request({
    url: `/yudian-back//account-robot-work/accountRobotWorkExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
