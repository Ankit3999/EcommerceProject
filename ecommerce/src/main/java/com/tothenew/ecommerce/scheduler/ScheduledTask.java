
package com.tothenew.ecommerce.scheduler;

import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.entity.ProductVariation;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.entity.User;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.ProductVariationRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import com.tothenew.ecommerce.repository.UserRepository;
import com.tothenew.ecommerce.services.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ScheduledTask {
    @Autowired
    SendMail sendMail;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    UserRepository userRepository;



    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public void scheduleTaskWithFixedRate() { }

    public void scheduleTaskWithFixedDelay() {}

    public void scheduleTaskWithInitialDelay() {}
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void passwordExpired() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            LocalDate currentDate = LocalDate.now();
            if (user.getCreatedDate() != null) {
                LocalDate updatedDate= user.getCreatedDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
               // Date updated = new Date(user.getUpdatePasswordDate().getYear(), user.getUpdatePasswordDate().getMonth(), user.getUpdatePasswordDate().getDate());
                if (currentDate.equals(updatedDate.plusMonths(6))) {
                    user.setPasswordExpire(true);
                    System.out.println(updatedDate);
                    String subject = "Reminder of password expiration";
                    String text = "Hi, \\n As per terms your password has been expired. Click here to reset! http://localhost:8080/password/forgot";
                    sendMail.sendEmail(user.getEmail(), subject, text);
                    userRepository.save(user);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleTaskWithCronExpression() {
        String email=currentUserService.getUser();
        Seller seller=sellerRepository.findByEmail(email);
        List<Object[]> productVariations = productVariationRepository.outOfStockProducts(seller.getId());
        if(!productVariations.isEmpty()){
            String sub="Daily reminder of your products";
            String msg=productVariations.stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(" and ", "{", "}"));;
            sendMail.sendEmail(email, sub, msg);
        }
       // logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }
}
