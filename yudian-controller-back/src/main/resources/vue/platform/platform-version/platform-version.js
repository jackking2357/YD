import request from '@/utils/request'

/**
 * @description 平台版本(列表)
 * @param {Object} data 携带的数据
 */
export function getPlatformVersionList (data = {}) {
  return request({
    url: '/yudian-back//platform-version/getPlatformVersionList',
    method: 'post',
    data
  })
}

/**
 * @description 平台版本(明细)
 * @param {Object} businessResultPlanId 平台版本id
 */
export function getPlatformVersionDetail (platformVersionId) {
  return request({
    url: `/yudian-back//platform-version/getPlatformVersionDetail/${platformVersionId}`,
    method: 'get'
  })
}

/**
 * @description 平台版本(添加)
 * @param {Object} data 携带的数据
 */
export function platformVersionAdd (data = {}) {
  return request({
    url: '/yudian-back//platform-version/platformVersionAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台版本(编辑)
 * @param {Object} data 携带的数据
 */
export function platformVersionEdit (data = {}) {
  return request({
    url: '/yudian-back//platform-version/platformVersionEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台版本(删除)
 * @param {Object} platformVersionIds 平台版本id
 */
export function platformVersionRemove (platformVersionIds) {
  return request({
    url: `/yudian-back//platform-version/platformVersionRemove/${platformVersionIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台版本(excel导出)
 * @param {Object} data 携带的数据
 */
export function platformVersionExport( data = {}) {
  return request({
    url: `/yudian-back//platform-version/platformVersionExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
