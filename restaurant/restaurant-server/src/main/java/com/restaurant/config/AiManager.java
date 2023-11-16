package com.restaurant.config;



import com.restaurant.exception.OrderBusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.common.ErrorCode;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.rmi.server.ExportException;

@Service
public class AiManager {
//    @Resource
//    private YuCongMingClient client;
    public String doChat(Long modelId,String message){
        String accessKey = "ao21rejd9rko7ucqsae9c65seijdpxqv";
        String secretKey = "4kunurf2h6o4ys4aeybrsd3y77q8vrku";
        YuCongMingClient client = new YuCongMingClient(accessKey, secretKey);

        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);

        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        if (response == null){
            throw new OrderBusinessException("AI 错误");
        }

        return response.getData().getContent();
    }
}
