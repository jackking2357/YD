import request from '@/utils/request'

/**
 * @description 机器人加速器(列表)
 * @param {Object} data 携带的数据
 */
export function getRobotAcceleratorList (data = {}) {
  return request({
    url: '/yudian-back//robot-accelerator/getRobotAcceleratorList',
    method: 'post',
    data
  })
}

/**
 * @description 机器人加速器(明细)
 * @param {Object} businessResultPlanId 机器人加速器id
 */
export function getRobotAcceleratorDetail (robotAcceleratorId) {
  return request({
    url: `/yudian-back//robot-accelerator/getRobotAcceleratorDetail/${robotAcceleratorId}`,
    method: 'get'
  })
}

/**
 * @description 机器人加速器(添加)
 * @param {Object} data 携带的数据
 */
export function robotAcceleratorAdd (data = {}) {
  return request({
    url: '/yudian-back//robot-accelerator/robotAcceleratorAdd',
    method: 'post',
    data
  })
}

/**
 * @description 机器人加速器(编辑)
 * @param {Object} data 携带的数据
 */
export function robotAcceleratorEdit (data = {}) {
  return request({
    url: '/yudian-back//robot-accelerator/robotAcceleratorEdit',
    method: 'put',
    data
  })
}

/**
 * @description 机器人加速器(删除)
 * @param {Object} robotAcceleratorIds 机器人加速器id
 */
export function robotAcceleratorRemove (robotAcceleratorIds) {
  return request({
    url: `/yudian-back//robot-accelerator/robotAcceleratorRemove/${robotAcceleratorIds}`,
    method: 'delete'
  })
}

/**
 * @description 机器人加速器(excel导出)
 * @param {Object} data 携带的数据
 */
export function robotAcceleratorExport( data = {}) {
  return request({
    url: `/yudian-back//robot-accelerator/robotAcceleratorExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
