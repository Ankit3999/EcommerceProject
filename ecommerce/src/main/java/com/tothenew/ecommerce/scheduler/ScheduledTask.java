package com.tothenew.ecommerce.scheduler;

import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTask {
    @Autowired
    SendMail sendMail;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    Product product;
    @Autowired
    ProductRepository productRepository;


    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public void scheduleTaskWithFixedRate() { }

    public void scheduleTaskWithFixedDelay() {}

    public void scheduleTaskWithInitialDelay() {}

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleTaskWithCronExpression() {
        String email=currentUserService.getUser();
        String sub="Daily reminder of your products";
        product=productRepository.activeProducts();
        String msg=product.getName()+" "+product.getBrand()+" "+product.getDescription();
        sendMail.sendEmail(email, sub, msg);
       // logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

}
