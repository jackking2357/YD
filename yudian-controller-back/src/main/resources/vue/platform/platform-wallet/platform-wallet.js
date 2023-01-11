import request from '@/utils/request'

/**
 * @description 平台收款钱包(列表)
 * @param {Object} data 携带的数据
 */
export function getPlatformWalletList (data = {}) {
  return request({
    url: '/yudian-back//platform-wallet/getPlatformWalletList',
    method: 'post',
    data
  })
}

/**
 * @description 平台收款钱包(明细)
 * @param {Object} businessResultPlanId 平台收款钱包id
 */
export function getPlatformWalletDetail (platformWalletId) {
  return request({
    url: `/yudian-back//platform-wallet/getPlatformWalletDetail/${platformWalletId}`,
    method: 'get'
  })
}

/**
 * @description 平台收款钱包(添加)
 * @param {Object} data 携带的数据
 */
export function platformWalletAdd (data = {}) {
  return request({
    url: '/yudian-back//platform-wallet/platformWalletAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台收款钱包(编辑)
 * @param {Object} data 携带的数据
 */
export function platformWalletEdit (data = {}) {
  return request({
    url: '/yudian-back//platform-wallet/platformWalletEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台收款钱包(删除)
 * @param {Object} platformWalletIds 平台收款钱包id
 */
export function platformWalletRemove (platformWalletIds) {
  return request({
    url: `/yudian-back//platform-wallet/platformWalletRemove/${platformWalletIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台收款钱包(excel导出)
 * @param {Object} data 携带的数据
 */
export function platformWalletExport( data = {}) {
  return request({
    url: `/yudian-back//platform-wallet/platformWalletExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
