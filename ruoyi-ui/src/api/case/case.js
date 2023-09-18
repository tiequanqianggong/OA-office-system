import request from '@/utils/request'

// 查询用例列表
export function listCase(query) {
  return request({
    url: '/case/case/list',
    method: 'get',
    params: query
  })
}

// 查询用例详细
export function getCase(id) {
  return request({
    url: '/case/case/' + id,
    method: 'get'
  })
}

// 新增用例
export function addCase(data) {
  return request({
    url: '/case/case',
    method: 'post',
    data: data
  })
}

// 修改用例
export function updateCase(data) {
  return request({
    url: '/case/case',
    method: 'put',
    data: data
  })
}

// 删除用例
export function delCase(id) {
  return request({
    url: '/case/case/' + id,
    method: 'delete'
  })
}
