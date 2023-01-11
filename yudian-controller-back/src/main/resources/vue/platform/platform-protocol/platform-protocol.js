import request from '@/utils/request'

/**
 * @description 平台协议(列表)
 * @param {Object} data 携带的数据
 */
export function getPlatformProtocolList (data = {}) {
  return request({
    url: '/yudian-back//platform-protocol/getPlatformProtocolList',
    method: 'post',
    data
  })
}

/**
 * @description 平台协议(明细)
 * @param {Object} businessResultPlanId 平台协议id
 */
export function getPlatformProtocolDetail (platformProtocolId) {
  return request({
    url: `/yudian-back//platform-protocol/getPlatformProtocolDetail/${platformProtocolId}`,
    method: 'get'
  })
}

/**
 * @description 平台协议(添加)
 * @param {Object} data 携带的数据
 */
export function platformProtocolAdd (data = {}) {
  return request({
    url: '/yudian-back//platform-protocol/platformProtocolAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台协议(编辑)
 * @param {Object} data 携带的数据
 */
export function platformProtocolEdit (data = {}) {
  return request({
    url: '/yudian-back//platform-protocol/platformProtocolEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台协议(删除)
 * @param {Object} platformProtocolIds 平台协议id
 */
export function platformProtocolRemove (platformProtocolIds) {
  return request({
    url: `/yudian-back//platform-protocol/platformProtocolRemove/${platformProtocolIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台协议(excel导出)
 * @param {Object} data 携带的数据
 */
export function platformProtocolExport( data = {}) {
  return request({
    url: `/yudian-back//platform-protocol/platformProtocolExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
