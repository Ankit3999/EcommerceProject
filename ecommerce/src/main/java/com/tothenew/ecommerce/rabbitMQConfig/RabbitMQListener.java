package com.tothenew.ecommerce.rabbitMQConfig;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RabbitMQListener {
    Object productObject;

    //@RabbitListener(queues="${rabbitmq.queueName}")
    public void listen(byte[] message) {
        String msg = new String(message);
        Notification not = new Gson().fromJson(msg, Notification.class);
        System.out.println("++++++++++++++++++++"+not+".................");
        Object object= not.getObject();
        System.out.println("++++++++++++++++++++"+object+".................");
        System.out.println("Received a new notification...");
        //System.out.println(not.toString());
    }

    public Object getProductObject() {
        return productObject;
    }
}