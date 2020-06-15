package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.exception.NotFoundException;
import com.tothenew.ecommerce.rabbitMQConfig.AMQPProducer;
import com.tothenew.ecommerce.rabbitMQConfig.Notification;
import com.tothenew.ecommerce.rabbitMQConfig.RabbitMQListener;
import com.tothenew.ecommerce.rabbitMQConfig.RabbitMQProperties;
import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.enums.Status;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AMQPProducer amqpProducer;
    @Autowired
    RabbitMQListener rabbitMQListener;
    @Autowired
    RabbitMQProperties rabbitMQProperties;
    @Autowired
    SendMail sendMail;
    @Autowired
    MessageSource messageSource;


    public void placeOrder(Long productVariationId, int quantity, String paymentMethod, Long addressId) {
        String email=currentUserService.getUser();
        Customer customer=customerRepository.findByEmail(email);
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        ProductVariation productVariation = productVariationOptional.get();
        /*if (productVariation.getActive()==false) {
            throw new NullPointerException("this item is not available"); }*/
        int quantity_Available = productVariation.getQuantityAvailable();
        int quantityAvailable = quantity_Available - quantity;
        if (quantityAvailable < 0) {
            throw new NullPointerException("only " + quantity_Available + " are in stock please select in this range");
        }
        productVariation.setQuantityAvailable(quantityAvailable);
        productVariationRepository.save(productVariation);

        Optional<Address> addressOptional = addressRepository.findById(addressId);
        Address address = null;
        if (addressOptional.isPresent()) {
            address = addressOptional.get();
        }
        else {
            throw new NullPointerException("add an address to place order");
        }
        Orders orders = new Orders();
        orders.setAmountPaid((productVariationRepository.getPrice(productVariationId))*quantity);
        orders.setCustomer(customer);
        orders.setPaymentMethod(paymentMethod);
        orders.setOrderAddress(addressToOrderAddress(address));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice((productVariationRepository.getPrice(productVariationId))*quantity);
        orderProduct.setOrders(orders);
        orderProduct.setProductVariationMetadata(productVariation.getInfoJson());
        orderProduct.setProductVariation(productVariation);

        OrderStatus orderStatus= new OrderStatus();
        orderStatus.setFromStatus(Status.ORDER_PLACED);
        orderStatus.setOrderProduct(orderProduct);
        Optional<Product> productOptional=productRepository.findById(productVariationRepository.getProductId(productVariationId));
        Product product=productOptional.get();

        System.out.println(("*****sending message*****"));
        Notification msg=new Notification("to confirm order", product);
        amqpProducer.sendMessage(msg);
        System.out.println("====message send====");

        ordersRepository.save(orders);
        productVariationRepository.save(productVariation);
        orderProductRepository.save(orderProduct);
        addressRepository.save(address);
        orderStatusRepository.save(orderStatus);
    }

    OrderAddress addressToOrderAddress(Address address){
        OrderAddress orderAddress=new OrderAddress();
        orderAddress.setAddressLine(address.getAddressLine());
        orderAddress.setCity(address.getCity());
        orderAddress.setCountry(address.getCountry());
        orderAddress.setLabel(address.getLabel());
        orderAddress.setState(address.getState());
        orderAddress.setZipCode(address.getZipCode());
        return orderAddress;
    }

    public void toSellerMessage(){
        Long[] l = {};
        Optional<Product> product= (Optional<Product>) rabbitMQListener.getProductObject();
        ProductVariation productVariation=productRepository.findProductVariation(product.get().getId());
        if(productVariation!=null){
            Long id=orderProductRepository.getOrderId(productVariation.getId());
            Long CustomerId=ordersRepository.findCustomer(id);
            String email=customerRepository.findEmail(CustomerId);
            if(product!=null){
                String text="Your order of product has been confirmed " +product.get().getName();
                sendMail.sendEmail(email, "Order Confirmation Mail", text);
            }
            else
                sendMail.sendEmail(email, "About your Order", "This order is not available right now");
        }
        else {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l, LocaleContextHolder.getLocale()));
        }
    }
}
