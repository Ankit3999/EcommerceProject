package com.tothenew.ecommerce.rabbitMQConfig;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    Object productObject;

    //@RabbitListener(queues="${rabbitmq.queueName}")
    public void listen(byte[] message) {
        String msg = new String(message);
        Notification not = new Gson().fromJson(msg, Notification.class);
        Object object= not.getObject();
        System.out.println("Received a new notification...");
        productObject.equals(object);
        //System.out.println(not.toString());
    }

    public Object getProductObject() {
        return productObject;
    }
}