// @ts-ignore

import request from '@/utils/request'

export const addInventory = (params: any) => {
  return request({
    url: '/inventory/add',
    method: 'post',
    data: { ...params }
  })
}
export const editInventory = (params: any) => {
  return request({
    url: '/inventory',
    method: 'put',
    data: { ...params }
  })
}
export const deleteInventory = (ids: string) => {
  return request({
    url: '/inventory',
    method: 'delete',
    params: { ids }
  })
}
export const queryInventoryById = (id: string | (string | null)[]) => {
  return request({
    url: `/inventory/${id}`,
    method: 'get'
  })
}
  export const getInventoryPage = (params: any) => {
    return request({
      url: '/inventory/page',
      method: 'get',
      params
    })
  }
