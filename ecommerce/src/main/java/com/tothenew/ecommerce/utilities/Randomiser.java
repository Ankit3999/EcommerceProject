package com.tothenew.ecommerce.utilities;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Randomiser {

    public String randomString(){

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public Long pincode(){
        long number = (long) Math.floor(Math.random() * 9_000_00L) + 1_000_00L;
        return number;
    }

    public Integer randomNumber(){

        Random random=new Random();
        int num = random.nextInt(900) + 100;
        return num;
    }

    public Long randomContact(){
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return number;
    }

    public Date randomDate(){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        int hundredYears = 100 * 365;
        LocalDate localDate= LocalDate.ofEpochDay(ThreadLocalRandom
                .current()
                .nextInt(-hundredYears, hundredYears));
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

    public String randomEmail(){
        String email=randomString()+"@tothenew.com";
        return email;
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public String randomAlphaNumeric() {
        StringBuilder builder = new StringBuilder();
        int i=10;
        while (i-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
