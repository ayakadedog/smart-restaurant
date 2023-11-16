# smart-restaurant
能自动分析数据并给出营业建议的餐厅管理系统
项目使用技术：vue + element-ui + Apache ECharts + springboot + mybatis-plus + redis + websocket + Spring Task + Apache POI + 接入文心一言
1. JWT 登录
2. 使用Redis 缓存减轻数据库压力，加快访问速度
3. 使用WebSocket 通信
4. 使用 SpringTask定时跟新营业额状态等信息
5. 微信登录及支付（直接返回成功）
6. 使用POI导出数据报表
7.依据导出报表数据分析餐厅营业情况
8.使用腾讯云数据库
9.使用阿里云存储图片
