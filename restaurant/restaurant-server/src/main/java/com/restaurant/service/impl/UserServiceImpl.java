package com.restaurant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.MessageConstant;
import com.restaurant.dto.UserLoginDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.LoginFailedException;
import com.restaurant.mapper.UserMapper;
import com.restaurant.properties.WeChatProperties;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;
import com.restaurant.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Resource
    private UserMapper usermapper;

    @Resource
    private WeChatProperties weChatProperties;

    @Resource
    private OrderService orderService;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openid = getOpenID(userLoginDTO.getCode());
        //检测openI
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //检验是否为新用户
        User user = usermapper.getByOpenid(openid);
        if (user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            usermapper.insert(user);
        }

        return user;
    }

    @Override
    public void changeUserMessage() {
        List<User> users = usermapper.listUser();

        for (User user : users) {
            Long id = user.getId();

            // 获取购买数量
            Long count = orderService.countUserOrder(id);
            user.setNumber(count.toString());

            // 获取最多点的菜系名称
            String mostOrderedCategory = usermapper.findMostOrderedCategory(id);
            user.setPreferences(mostOrderedCategory);
            user.setCreateTime(usermapper.findTime(id));
            // 更新用户信息
            usermapper.updateById(user);
        }
    }

    /**
     * 调用微信接口服务，拿到openId
     * @param code
     * @return
     */
    private String getOpenID(String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        String json =  HttpClientUtil.doGet(WX_LOGIN,map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        return openid;
    }
}
