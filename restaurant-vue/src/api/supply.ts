import request from '@/utils/request'
/**
 *
 * 套餐管理
 *
 **/

// 查询列表数据
export const getSupplyPage = (params: any) => {
  return request({
    url: '/supply/conditionQuery',
    method: 'get',
    params,
  },)
}

// 删除数据接口
// export const deleteTable = (params: any) => {
//   return request({
//     url: '/supply/delete',
//     method: 'delete',
//     params,
//   })
// }

// 修改数据接口
export const editSupply = (params: any) => {
  return request({
    url: '/supply/update',
    method: 'post',
    data: { ...params }
  })
}

// 新增数据接口
export const addSupply = (params: any) => {
  return request({
    url: '/supply/add',
    method: 'post',
    data: { ...params }
  })
}

// 根据id查看餐桌信息
export const querySupplyById = (id: string | (string | null)[]) => {
  return request({
    url: `/supply/${id}`,
    method: 'get'
  })
}
// 删除id删除数据接口
export const deleteSupplyById= (ids: string) => {
  return request({
    url: `/supply/deleteById/${ids}`,
    method: 'post',
  })
}
