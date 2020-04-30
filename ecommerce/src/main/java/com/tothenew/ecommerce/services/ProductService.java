package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.dto.ProductVariationDto;
import com.tothenew.ecommerce.dto.ViewProductDto;
import com.tothenew.ecommerce.dto.ViewProductDtoforCustomer;
import com.tothenew.ecommerce.entity.*;
import com.tothenew.ecommerce.exception.NullException;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.*;
import javassist.NotFoundException;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ProductService {
    Long[] l = {};
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    SendMail sendMail;
    @Autowired
    CurrentUserService currentUserService;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    public Product toProduct(ProductDto productDto) {
        if (productDto == null)
            return null;
        return modelMapper.map(productDto, Product.class);
    }

    public ProductDto toProductDto(Product product) {
        if (product == null)
            return null;
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductVariation toProductVariation(ProductVariationDto productVariationDto) {
        if (productVariationDto == null)
            return null;
        return modelMapper.map(productVariationDto, ProductVariation.class);
    }

    public ProductVariationDto toProductVariationDto(ProductVariation variation) {
        if (variation == null)
            return null;
        return modelMapper.map(variation, ProductVariationDto.class);
    }


    public String addProduct(Product product, Long categoryId) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()&&categoryRepository.checkIfLeaf(categoryId)==0)
        {
            if (product.getName().equals(categoryRepository.findById(categoryId).get().getName())||product.getName().equals(product.getBrand()))
            {
                throw new NullException(messageSource.getMessage("message16.txt",l,LocaleContextHolder.getLocale()));
            }
            Product product1 = new Product();
            product1 = modelMapper.map(product,Product.class);
            String sellername = currentUserService.getUser();
            Seller seller = sellerRepository.findByEmail(sellername);
            Set<Product> productSet = new HashSet<>();
            product1.setBrand(product.getBrand());
            product1.setActive(false);
            product1.setCancellable(product.getCancellable());
            product1.setDescription(product.getDescription());
            product1.setName(product.getName());
            product1.setSeller(seller);
            product1.setCategory(categoryRepository.findById(categoryId).get());
            productSet.add(product1);
            productRepository.save(product1);
        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("message15.txt",l,LocaleContextHolder.getLocale()));
        }
        return product.getName()+" Successfully added";
    }

    private void sendProductCreationMail(String email, Product product) {
        String subject = "product created";
        String content = "product with details \n" +
                " name: " + product.getName() +
                "category: " + product.getCategory().getName() +
                "brand: " + product.getBrand() + " has been created";
        sendMail.sendEmail(email, subject, content);
    }


    public String activateProduct(Long id) {
        String message;
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product1 = product.get();
        if (product1.getActive() == true) {
            message = "product is already active";
        }
        product1.setActive(true);
        String email = product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, true);
        return "success";
    }

    public String deactivateProductById(Long id) {
        String message;
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product1 = product.get();
        if (product1.getActive() == false) {
            message = "product is already deActive";
        }
        product1.setActive(false);
        String email = product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, false);
        return "success";
    }

    private void sendActDeactivationMail(String email, Product product1, boolean b) {
        String subject;
        if (b)
            subject = "product activation mail";
        else
            subject = "product deactivation mail";
        String content = "product with details " +
                "name: " + product1.getName() +
                "brand: " + product1.getBrand() + " is activated";
        sendMail.sendEmail(email, subject, content);
    }

    public ProductDto getProduct(Long productId, String email) throws NotFoundException {
        Seller seller= sellerRepository.findByUsername(email);
        Optional<Product> product=  productRepository.findById(productId);
        Long[] l = {};
        if (product.isPresent())
        {
            if ((product.get().getSeller().getUsername()).equals(seller.getUsername()))
            {
                ProductDto viewProductDTO = new ProductDto();
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setActive(product.get().getActive());
                viewProductDTO.setCancellable(product.get().getCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setName(product.get().getName());
                Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
                viewProductDTO.setCategoryName(category.get().getName());
                List<String > fields = new ArrayList<>();
                List<String > values = new ArrayList<>();
                List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
                for (Long l1 : longList1) {
                    Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                    fields.add(categoryMetadataField.get().getName());
                    values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
                }
                viewProductDTO.setFieldName(fields);
                viewProductDTO.setValues(values);


                return viewProductDTO;
            } else {
                throw new NotFoundException(messageSource.getMessage("message9.txt",l,LocaleContextHolder.getLocale()));
            }

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public String deleteProductById(Long id, String email) {
        String message;
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            message = "product not found";
            return message;
        }
        Product product = productOptional.get();
        if (!product.getSeller().getEmail().equalsIgnoreCase(email)) {
            message = "product does not belong to this user";
            return message;
        }
        if (product.getDeleted()) {
            message = "product is already deleted";
            return message;
        }
        product.setDeleted(true);
        message = product.getName() + " is successfully deleted.";
        return message;
    }

    public void updateProductByProductId(Long id, String email, Product product) throws NotFoundException {
        Seller seller1= sellerRepository.findByEmail(email);
        Optional<Product> productOptional = productRepository.findById(id);
        Long[] l = {};
        if (productOptional.isPresent()) {
            Product product1 = productOptional.get();
            if ((product1.getSeller().getUsername()).equals(seller1.getUsername())) {
                if (product.getName().equals(product1.getBrand())||product.getName().equals(product1.getCategory().getName())||product.getName().equals(seller1.getFirstName())||product.getName().equals(product.getBrand())) {
                    throw new NullException(messageSource.getMessage("message10.txt",l,LocaleContextHolder.getLocale()));
                }
                if (product.getName()!=null) {
                    product1.setName(product.getName());
                }
                if (product.getBrand()!=null) {
                    product1.setBrand(product.getBrand());
                }
                if (product.getCancellable()!=null) {
                    product1.setCancellable(product.getCancellable());
                }
                if (product.getReturnable()!=null) {
                    product1.setReturnable(product.getReturnable());
                }
                if (product.getDescription()!=null) {
                    product1.setDescription(product.getDescription());
                }
                if (product.getActive()!=null) {
                    product1.setActive(product.getActive());
                }
                productRepository.save(product1);
            }
            else {
                throw new NullException(messageSource.getMessage("message11.txt",l,LocaleContextHolder.getLocale()));
            }
        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }



    public ViewProductDto viewSingleProductForSeller(Long productId) throws NotFoundException {
        String sellerEmail = currentUserService.getUser();
        Seller seller = sellerRepository.findByEmail(sellerEmail);
        Optional<Product> product = productRepository.findById(productId);
        Long[] l = {};
        if (product.isPresent()) {
            if ((product.get().getSeller().getEmail()).equals(seller.getEmail())) {
                ViewProductDto viewProductDTO = new ViewProductDto();
                viewProductDTO.setId(product.get().getId());
                viewProductDTO.setBrand(product.get().getBrand());
                viewProductDTO.setActive(product.get().getActive());
                viewProductDTO.setCancellable(product.get().getCancellable());
                viewProductDTO.setDescription(product.get().getDescription());
                viewProductDTO.setProductName(product.get().getName());
                Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
                viewProductDTO.setProductName(category.get().getName());

                return viewProductDTO;
            } else {
                throw new NotFoundException(messageSource.getMessage("Category not found", l, LocaleContextHolder.getLocale()));
            }

        } else {
            throw new NotFoundException(messageSource.getMessage("product not found", l, LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewProductDto> getProductDetails(Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        String email = currentUserService.getUser();
        Seller seller = sellerRepository.findByEmail(email);
        List<Long> longList = productRepository.getProductIdOfSeller(seller.getId(), paging);
        List<ViewProductDto> list = new ArrayList<>();
        for (Long l : longList) {
            list.add(viewSingleProductForSeller(productRepository.findById(l).get().getId()));
        }
        return list;
    }

    public List<ViewProductDtoforCustomer> getSimilarProducts(Long productId, Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        Long[] l1 = {};
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ViewProductDtoforCustomer> list = new ArrayList<>();

        if (product.isPresent())
        {
            List<Long> longList = productRepository.getIdOfSimilarProduct(product.get().getCategory().getId(),product.get().getBrand(),paging);
            System.out.println(longList);
            for (Long l : longList)
            {
                list.add(viewSingleProductForCustomer(productRepository.findById(l).get().getId()));
            }
            return list;

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l1,LocaleContextHolder.getLocale()));
        }
    }

    public ViewProductDtoforCustomer viewSingleProductForCustomer(Long productId) throws NotFoundException {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent()&&product.get().getActive()==true&&product.get().getVariations().isEmpty()==false) {
            ViewProductDtoforCustomer viewProductDTO = new ViewProductDtoforCustomer();
            viewProductDTO.setBrand(product.get().getBrand());
            viewProductDTO.setActive(product.get().getActive());
            viewProductDTO.setCancellable(product.get().getCancellable());
            viewProductDTO.setDescription(product.get().getDescription());
            viewProductDTO.setProductName(product.get().getName());
            Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
            viewProductDTO.setName(category.get().getName());
            List<String > fields = new ArrayList<>();
            List<String > values = new ArrayList<>();
            List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            for (Long l1 : longList1) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                fields.add(categoryMetadataField.get().getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
            }
            viewProductDTO.setFieldName(fields);
            viewProductDTO.setValues(values);
            List<String > list = new ArrayList<>();
            Set<ProductVariation> productVariations = product.get().getVariations();
            String firstPath = System.getProperty("user.dir");
            String fileBasePath = firstPath+"/src/main/resources/productVariation/";
            for (ProductVariation productVariation : productVariations) {
                File dir = new File(fileBasePath);
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        String value = productVariation.getId().toString()+"_0";
                        if (file1.getName().startsWith(value)) {
                            list.add("http://localhost:8080/viewProductVariationImage/"+file1.getName());
                        }
                    }
                }
            }
            viewProductDTO.setLinks(list);
            return viewProductDTO;
        }
        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }


    public List<ViewProductDtoforCustomer> getProductDetailsForCustomer(Long categoryId,Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<ViewProductDtoforCustomer> list = new ArrayList<>();
        if (category.isPresent()&&categoryRepository.checkIfLeaf(categoryId)==0) {
            List<Long> longList = productRepository.getIdsOfProducts(categoryId,paging);
            System.out.println(longList);
            for (Long l : longList) {
                list.add(viewSingleProductForCustomer(productRepository.findById(l).get().getId()));

            }
            return list;
        }
        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public ViewProductDtoforCustomer viewSingleProductForAdmin(Long productId) throws NotFoundException {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent()) {
            ViewProductDtoforCustomer viewProductDTO = new ViewProductDtoforCustomer();
            viewProductDTO.setBrand(product.get().getBrand());
            viewProductDTO.setActive(product.get().getActive());
            viewProductDTO.setCancellable(product.get().getCancellable());
            viewProductDTO.setDescription(product.get().getDescription());
            viewProductDTO.setProductName(product.get().getName());
            Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
            viewProductDTO.setName(category.get().getName());
            List<String > fields = new ArrayList<>();
            List<String > values = new ArrayList<>();
            List<Long> longList1 = categoryMetadataFieldValuesRepository.getMetadataId(category.get().getId());
            for (Long l1 : longList1) {
                Optional<CategoryMetadataField> categoryMetadataField = categoryMetadataFieldRepository.findById(l1);
                fields.add(categoryMetadataField.get().getName());
                values.add(categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(category.get().getId(), l1));
            }
            viewProductDTO.setFieldName(fields);
            viewProductDTO.setValues(values);
            List<String > list = new ArrayList<>();
            Set<ProductVariation> productVariations = product.get().getVariations();
            String firstPath = System.getProperty("user.dir");
            String fileBasePath = firstPath+"/src/main/resources/productVariation/";
            for (ProductVariation productVariation : productVariations) {
                File dir = new File(fileBasePath);
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        String value = productVariation.getId().toString()+"_0";
                        if (file1.getName().startsWith(value)) {
                            list.add("http://localhost:8080/viewProductVariationImage/"+file1.getName());
                        }
                    }
                }
            }
            viewProductDTO.setLinks(list);
            return viewProductDTO;
        }
        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewProductDtoforCustomer> getProductDetailsForAdmin(Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Long> longList = productRepository.getAllId(paging);
        System.out.println(longList);
        List<ViewProductDtoforCustomer> list = new ArrayList<>();
        for (Long l : longList) {
            System.out.println(productRepository.findById(l).get().getName());
            list.add(viewSingleProductForAdmin(productRepository.findById(l).get().getId()));
        }
        return list;
    }

}
