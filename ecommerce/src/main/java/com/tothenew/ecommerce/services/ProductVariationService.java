package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.ProductVariationDto;
import com.tothenew.ecommerce.entity.CategoryMetadataField;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.entity.ProductVariation;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.exception.NotFoundException;
import com.tothenew.ecommerce.exception.NullException;
import com.tothenew.ecommerce.repository.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ProductVariationService {
    Long[] l = {};
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageSource messageSource;
    public void addNewProductVariation(ProductVariation productVariation, Long productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product product1 = product.get();
            if (product1.getActive()) {
                Map<String, String> stringMap = new HashMap<>();
                String seller = currentUserService.getUser();
                Seller seller1 = sellerRepository.findByEmail(seller);
                Map<String, Object> map = productVariation.getInfoAttributes();

                if ((product1.getSeller().getUsername()).equals(seller1.getUsername())) {
                    productVariation.setProduct(product1);
                    Long categoryId = productRepository.getCategoryId(product1.getId());
                    List<Long> metadataIds = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);

                    for (long l : metadataIds) {
                        String metadata = categoryMetadataFieldRepository.getNameOfMetadata(l);
                        String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l);
                        stringMap.put(metadata, metadataValues);
                    }
                    int count = 0;
                    for (String key : map.keySet()) {
                        if (stringMap.containsKey(key)) {
                            String str = stringMap.get(key);
                            Object obj = map.get(key);
                            String[] strings = str.split(",");
                            List<String> list = Arrays.asList(strings);
                            if (list.contains(obj)) {
                                count++;
                            }
                        }
                    }

                    if (count == map.size()) {
                        String info = objectMapper.writeValueAsString(productVariation.getInfoAttributes());
                        productVariation.setInfoJson(info);
                        productVariation.setActive(true);
                        productVariationRepository.save(productVariation);
                    } else {
                        throw new NotFoundException("Field values are not provided correctly");
                    }

                } else {
                    throw new NullException("you can't add any product variation to this product");
                }

            } else {
                throw new NullException("product is not active");
            }
        }
        else {
            throw new NotFoundException("The product name provided is wrong");

        }
    }

    public ProductVariationDto getSingleProductVariation(Long productVariationId) throws IOException {
        String username = currentUserService.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        if (productVariation.isPresent())
        {
            Product product = productVariation.get().getProduct();
            if (product.getSeller().getUsername().equals(seller.getUsername())&&product!=null)
            {
                ProductVariationDto productVariationDTO = new ProductVariationDto();
                productVariationDTO.setName(product.getName());
                productVariationDTO.setBrand(product.getBrand());
                productVariationDTO.setCancellable(product.getCancellable());
                productVariationDTO.setActive(product.getActive());
                productVariationDTO.setDescription(product.getDescription());
                productVariationDTO.setReturnable(product.getReturnable());
                Map<String ,Object> map = objectMapper.readValue(productVariation.get().getInfoJson(), HashMap.class);
                List<String > field = new ArrayList<>();
                List<String > values = new ArrayList<>();
                for (Map.Entry m : map.entrySet())
                {
                    field.add(m.getKey().toString());
                    values.add(m.getValue().toString());
                }
                productVariationDTO.setFields(field);
                productVariationDTO.setValues(values);
                productVariationDTO.setPrice(productVariation.get().getPrice());
                productVariationDTO.setCurrentActiveStatus(productVariation.get().getActive());
                productVariationDTO.setQuantityAvailable(productVariation.get().getQuantityAvailable());
                return productVariationDTO;

            }
            else
            {
                throw  new NotFoundException(messageSource.getMessage("message17.txt",l, LocaleContextHolder.getLocale()));
            }
        }
        else {
            throw new NullException(messageSource.getMessage("message18.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ProductVariationDto> getAllProductVariations(Long productId) throws IOException {
        String seller= currentUserService.getUser();
        Seller seller1= sellerRepository.findByUsername(seller);
        Optional<Product> productOptional= productRepository.findById(productId);
        List<ProductVariationDto> list = new ArrayList<>();
        if (productOptional.isPresent())
        { Product product = productOptional.get();
            if ((product.getSeller().getUsername()).equals(seller1.getUsername()))
            {
                Set<ProductVariation> productVariations = product.getVariations();
                for (ProductVariation productVariation : productVariations)
                {
                    list.add(getSingleProductVariation(productVariation.getId()));
                }
                return list;
            }
            else {
                throw  new NotFoundException(messageSource.getMessage("message17.txt",l, LocaleContextHolder.getLocale()));
            }
        }

        else {
            throw new NullException(messageSource.getMessage("message18.txt",l,LocaleContextHolder.getLocale()));
        }

    }

    public void editProductVariation(ProductVariation productVariation, Long productVariationId) throws IOException {
        String username = currentUserService.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        long id = productVariationRepository.getProductId(productVariationId);
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<ProductVariation> productVariation3= productVariationRepository.findById(productVariationId);
        if(productVariation3.isPresent()&&productOptional.isPresent())
        {
            Product product = productOptional.get();
            if (product.getActive()== true)
            {
                if ((product.getSeller().getUsername()).equals(seller.getUsername())) {
                    Map<String, String> stringMap = new HashMap<>();
                    Map<String, Object> map = productVariation.getInfoAttributes();
                    Long categoryId = productRepository.getCategoryId(product.getId());
                    List<Long> metadataIds = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);

                    for (long l : metadataIds) {

                        String metadata = categoryMetadataFieldRepository.getNameOfMetadata(l);
                        String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l);
                        stringMap.put(metadata, metadataValues);

                    }

                    int count=0;

                    for (String key : map.keySet()) {
                        if (stringMap.containsKey(key)) {
                            String str = stringMap.get(key);
                            Object obj = map.get(key);
                            String[] strings = str.split(",");
                            List<String> list = Arrays.asList(strings);
                            if (list.contains(obj)) {
                                count++;
                            }

                        }
                    }

                    if (count == map.size())
                    {
                        Optional<ProductVariation> productVariation1 = productVariationRepository.findById(productVariationId);
                        ProductVariation productVariation2 = productVariation1.get();
                        productVariation2.setActive(productVariation.getActive());
                        productVariation2.setQuantityAvailable(productVariation.getQuantityAvailable());
                        productVariation2.setPrice(productVariation.getPrice());
                        String info = objectMapper.writeValueAsString(productVariation.getInfoAttributes());
                        productVariation2.setInfoJson(info);
                        productVariationRepository.save(productVariation2);
                    }

                    else{
                        throw new NotFoundException(messageSource.getMessage("message21.txt",l,LocaleContextHolder.getLocale()));
                    }

                } else {
                    throw  new  NullException(messageSource.getMessage("message22.txt",l,LocaleContextHolder.getLocale()));
                }
            }
            else {
                throw new NullException(messageSource.getMessage("message20.txt",l,LocaleContextHolder.getLocale()));
            }
        }

        else
        {
            throw  new NullException(messageSource.getMessage("message18.txt",l,LocaleContextHolder.getLocale()));
        }
    }
}
