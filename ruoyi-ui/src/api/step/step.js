import request from '@/utils/request'

// 查询用例步骤列表
export function listStep(query) {
  return request({
    url: '/step/step/list',
    method: 'get',
    params: query
  })
}

// 查询用例步骤详细
export function getStep(id) {
  return request({
    url: '/step/step/' + id,
    method: 'get'
  })
}

// 新增用例步骤
export function addStep(data) {
  return request({
    url: '/step/step',
    method: 'post',
    data: data
  })
}

// 修改用例步骤
export function updateStep(data) {
  return request({
    url: '/step/step',
    method: 'put',
    data: data
  })
}

// 删除用例步骤
export function delStep(id) {
  return request({
    url: '/step/step/' + id,
    method: 'delete'
  })
}
