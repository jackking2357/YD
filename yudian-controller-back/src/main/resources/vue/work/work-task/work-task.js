import request from '@/utils/request'

/**
 * @description 任务(列表)
 * @param {Object} data 携带的数据
 */
export function getWorkTaskList (data = {}) {
  return request({
    url: '/yudian-back//work-task/getWorkTaskList',
    method: 'post',
    data
  })
}

/**
 * @description 任务(明细)
 * @param {Object} businessResultPlanId 任务id
 */
export function getWorkTaskDetail (workTaskId) {
  return request({
    url: `/yudian-back//work-task/getWorkTaskDetail/${workTaskId}`,
    method: 'get'
  })
}

/**
 * @description 任务(添加)
 * @param {Object} data 携带的数据
 */
export function workTaskAdd (data = {}) {
  return request({
    url: '/yudian-back//work-task/workTaskAdd',
    method: 'post',
    data
  })
}

/**
 * @description 任务(编辑)
 * @param {Object} data 携带的数据
 */
export function workTaskEdit (data = {}) {
  return request({
    url: '/yudian-back//work-task/workTaskEdit',
    method: 'put',
    data
  })
}

/**
 * @description 任务(删除)
 * @param {Object} workTaskIds 任务id
 */
export function workTaskRemove (workTaskIds) {
  return request({
    url: `/yudian-back//work-task/workTaskRemove/${workTaskIds}`,
    method: 'delete'
  })
}

/**
 * @description 任务(excel导出)
 * @param {Object} data 携带的数据
 */
export function workTaskExport( data = {}) {
  return request({
    url: `/yudian-back//work-task/workTaskExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
