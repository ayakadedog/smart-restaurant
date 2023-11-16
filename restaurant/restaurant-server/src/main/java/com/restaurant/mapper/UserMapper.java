package com.restaurant.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restaurant.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 判断是否为新用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    Integer countByMap(Map map);

    @Select("select * from user")
    List<User> listUser();

    String  findMostOrderedCategory(Long userId);

   @Select("select MAX(order_time) from orders where user_id = #{userId}")
    LocalDateTime findTime(Long userId);


}
