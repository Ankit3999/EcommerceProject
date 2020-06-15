package com.tothenew.ecommerce.batch;

import com.tothenew.ecommerce.entity.Orders;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.repository.OrdersRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Orders>{
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public void write(List<? extends Orders> orders) throws Exception {

            ordersRepository.saveAll(orders);
    }

}
