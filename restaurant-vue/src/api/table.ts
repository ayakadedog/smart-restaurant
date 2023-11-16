import request from '@/utils/request'
/**
 *
 * 套餐管理
 *
 **/

// 查询列表数据
export const getTablePage = (params: any) => {
  return request({
    url: '/table/conditionQuery',
    method: 'get',
    params,
  },)
}

// 删除数据接口
// export const deleteTable = (params: any) => {
//   return request({
//     url: '/table/delete',
//     method: 'delete',
//     params,
//   })
// }

// 修改数据接口
export const editTable = (params: any) => {
  return request({
    url: '/table/update',
    method: 'post',
    data: { ...params }
  })
}

// 新增数据接口
export const addTable = (params: any) => {
  return request({
    url: '/table/add',
    method: 'post',
    data: { ...params }
  })
}

// 根据id查看餐桌信息
export const queryTableById = (id: string | (string | null)[]) => {
  return request({
    url: `/table/${id}`,
    method: 'get'
  })
}
// 删除id删除数据接口
export const deleteTableById= (ids: string) => {
  return request({
    url: `/table/deleteById/${ids}`,
    method: 'post',
  })
}
