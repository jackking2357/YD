import request from '@/utils/request'

/**
 * @description 机器人(列表)
 * @param {Object} data 携带的数据
 */
export function getRobotList (data = {}) {
  return request({
    url: '/yudian-back//robot/getRobotList',
    method: 'post',
    data
  })
}

/**
 * @description 机器人(明细)
 * @param {Object} businessResultPlanId 机器人id
 */
export function getRobotDetail (robotId) {
  return request({
    url: `/yudian-back//robot/getRobotDetail/${robotId}`,
    method: 'get'
  })
}

/**
 * @description 机器人(添加)
 * @param {Object} data 携带的数据
 */
export function robotAdd (data = {}) {
  return request({
    url: '/yudian-back//robot/robotAdd',
    method: 'post',
    data
  })
}

/**
 * @description 机器人(编辑)
 * @param {Object} data 携带的数据
 */
export function robotEdit (data = {}) {
  return request({
    url: '/yudian-back//robot/robotEdit',
    method: 'put',
    data
  })
}

/**
 * @description 机器人(删除)
 * @param {Object} robotIds 机器人id
 */
export function robotRemove (robotIds) {
  return request({
    url: `/yudian-back//robot/robotRemove/${robotIds}`,
    method: 'delete'
  })
}

/**
 * @description 机器人(excel导出)
 * @param {Object} data 携带的数据
 */
export function robotExport( data = {}) {
  return request({
    url: `/yudian-back//robot/robotExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
