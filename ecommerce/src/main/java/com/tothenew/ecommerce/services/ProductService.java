package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.dto.ProductVariationDto;
import com.tothenew.ecommerce.dto.ViewProductDto;
import com.tothenew.ecommerce.entity.*;
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

import java.util.*;

@Service
public class ProductService {
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

    public Product toProduct(ProductDto productDto){
        if(productDto == null)
            return null;
        return modelMapper.map(productDto, Product.class);
    }

    public ProductDto toProductDto(Product product){
        if(product == null)
            return null;
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductVariation toProductVariation(ProductVariationDto productVariationDto){
        if(productVariationDto==null)
            return null;
        return modelMapper.map(productVariationDto, ProductVariation.class);
    }

    public ProductVariationDto toProductVariationDto(ProductVariation variation){
        if(variation==null)
            return null;
        return modelMapper.map(variation, ProductVariationDto.class);
    }


    public String addProduct(String email, ProductDto productDto){
        String message=validateProduct(email, productDto);
        if(!message.equalsIgnoreCase("success")){
            return message;
        }
        Category category=categoryRepository.findById(productDto.getCategoryId()).get();
        Product product=toProduct(productDto);
        Seller seller=sellerRepository.findByEmail(email);
        product.setCategory(category);
        product.setSeller(seller);
        productRepository.save(product);

        sendProductCreationMail(email, product);

        return "success";
    }

    private void sendProductCreationMail(String email, Product product) {
        String subject="product created";
        String content="product with details \n"+
                " name: "+product.getName()+
                "category: "+product.getCategory().getName()+
                "brand: "+product.getBrand()+" has been created";
        sendMail.sendEmail(email, subject, content);
    }

    private String validateProduct(String email, ProductDto productDto) {
        String message="success";
        Optional<Category> savedCategory=categoryRepository.findById(productDto.getCategoryId());
        if(!savedCategory.isPresent()){
            message="category doesn't exist";
        }
        Category category=savedCategory.get();
        if(!(category.getSubCategories()==null || category.getSubCategories().isEmpty())){
            message="not a leaf category";
        }
        Product product=productRepository.findByName(productDto.getName());
       // message="success";
        return message;
    }

    public String activateProduct(Long id) {
        String message;
        Optional<Product> product=productRepository.findById(id);
        if(!product.isPresent()){
            message="product not found";
            return message;
        }
        Product product1=product.get();
        if(product1.getActive()==true){
            message="product is already active";
        }
        product1.setActive(true);
        String email=product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, true);
        return "success";
    }

    public String deactivateProductById(Long id) {
        String message;
        Optional<Product> product=productRepository.findById(id);
        if(!product.isPresent()){
            message="product not found";
            return message;
        }
        Product product1=product.get();
        if(product1.getActive()==false){
            message="product is already deActive";
        }
        product1.setActive(false);
        String email=product1.getSeller().getEmail();
        sendActDeactivationMail(email, product1, false);
        return "success";
    }

    private void sendActDeactivationMail(String email, Product product1, boolean b) {
        String subject;
        if(b)
            subject="product activation mail";
        else
            subject="product deactivation mail";
        String content="product with details "+
                "name: "+product1.getName()+
                "brand: "+product1.getBrand()+" is activated";
        sendMail.sendEmail(email, subject, content);
    }

    public ProductDto getProduct(Long id, String email) {
        String message;
        Optional<Product> productOptional=productRepository.findById(id);
        if(!productOptional.isPresent()){
            message="product not found";
            //return message;
        }
        Product product=productOptional.get();
        if(!product.getSeller().getEmail().equalsIgnoreCase(email)){
            message="product does not belong to this user";
            //return message;
        }
        if(product.getDeleted()){
            message="product not found";
            //return message;
        }
        ProductDto productDto=toProductDto(product);
        return productDto;
    }

    public String deleteProductById(Long id, String email) {
        String message;
        Optional<Product> productOptional=productRepository.findById(id);
        if(!productOptional.isPresent()){
            message="product not found";
            return message;
        }
        Product product=productOptional.get();
        if(!product.getSeller().getEmail().equalsIgnoreCase(email)){
            message="product does not belong to this user";
            return message;
        }
        if(product.getDeleted()){
            message="product is already deleted";
            return message;
        }
        product.setDeleted(true);
        message=product.getName()+" is successfully deleted.";
        return message;
    }

    public String updateProductByProductId(Long pId, String email, ProductDto productDto) {
        String message;
        Optional<Product> optionalProduct=productRepository.findById(pId);
        if(!optionalProduct.isPresent()){
            message="product not found";
            return message;
        }
        Product product=optionalProduct.get();
        if(!product.getSeller().getEmail().equalsIgnoreCase(email)){
            message="product is not of this seller";
            return message;
        }
        if(product.getDeleted()){
            message="product does not exist";
            return message;
        }
        if(product.getName()!=null){
            Product product1=productRepository.findByName(productDto.getName());
            if(product1!=null || product1.getBrand().equals(product.getBrand())){
                message="this product already exist";
                return message;
            }
        }
        Product product2 = productRepository.findById(pId).get();
        saveProductDtoToProduct(product, productDto);
        productRepository.save(product);
        return "success";
    }

    public void saveProductDtoToProduct(Product product, ProductDto productDto) {

        if(productDto.getName() != null)
            product.setName(productDto.getName());

        if(productDto.getDescription() != null)
            product.setDescription(productDto.getDescription());

        if(productDto.getReturnable() != null)
            product.setReturnable(productDto.getReturnable());

        if(productDto.getCancellable() != null)
            product.setCancellable(productDto.getCancellable());

    }

    public ViewProductDto viewSingleProductForSeller(Long productId) throws NotFoundException {
        String sellerEmail= currentUserService.getUser();
        Seller seller= sellerRepository.findByEmail(sellerEmail);
        Optional<Product> product=  productRepository.findById(productId);
        Long[] l = {};
        if (product.isPresent())
        {
            if ((product.get().getSeller().getEmail()).equals(seller.getEmail()))
            {
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
                throw new NotFoundException(messageSource.getMessage("Category not found",l, LocaleContextHolder.getLocale()));
            }

        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("product not found",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewProductDto> getProductDetails(Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        String email = currentUserService.getUser();
        Seller seller = sellerRepository.findByEmail(email);
        List<Long> longList = productRepository.getProductIdOfSeller(seller.getId(),paging);
        List<ViewProductDto> list = new ArrayList<>();
        for (Long l : longList)
        {
            list.add(viewSingleProductForSeller(productRepository.findById(l).get().getId()));
        }
        return list;
    }

    public ViewProductDto viewSingleProductForAdmin(Long productId) throws NotFoundException {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent())
        {
            ViewProductDto viewProductDTO = new ViewProductDto();
            viewProductDTO.setBrand(product.get().getBrand());
            viewProductDTO.setActive(product.get().getActive());
            viewProductDTO.setCancellable(product.get().getCancellable());
            viewProductDTO.setDescription(product.get().getDescription());
            viewProductDTO.setProductName(product.get().getName());
            Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
            viewProductDTO.setName(category.get().getName());

            return viewProductDTO;
        }

        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }
    }

    public List<ViewProductDto> getProductDetailsForAdmin(Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<Long> longList = productRepository.getAllId(paging);
        System.out.println(longList);
        List<ViewProductDto> list = new ArrayList<>();
        for (Long l : longList)
        {
            System.out.println(productRepository.findById(l).get().getName());
            list.add(viewSingleProductForAdmin(productRepository.findById(l).get().getId()));

        }
        return list;
    }

    public ViewProductDto viewSingleProductForCustomer(Long productId) throws NotFoundException {
        Optional<Product> product=  productRepository.findById(productId);
        if (product.isPresent()&&product.get().getActive()==true&&product.get().getVariations().isEmpty()==false)
        {
            ViewProductDto viewProductDTO = new ViewProductDto();
            viewProductDTO.setBrand(product.get().getBrand());
            viewProductDTO.setActive(product.get().getActive());
            viewProductDTO.setCancellable(product.get().getCancellable());
            viewProductDTO.setDescription(product.get().getDescription());
            viewProductDTO.setProductName(product.get().getName());
            Optional<Category> category = categoryRepository.findById(productRepository.getCategoryId(productId));
            viewProductDTO.setName(category.get().getName());

            Set<ProductVariation> productVariations = product.get().getVariations();
            String firstPath = System.getProperty("user.dir");
            String fileBasePath = firstPath+"/src/main/resources/productVariation/";
            return viewProductDTO;
        }
        else {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("notfound.txt",l,LocaleContextHolder.getLocale()));
        }

    }

    public List<ViewProductDto> getProductDetailsForCustomer(Long categoryId, Integer pageNo, Integer pageSize, String sortBy) throws NotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<ViewProductDto> list = new ArrayList<>();
        if (category.isPresent() && category.get().getSubCategories()==null)
        {
            List<Long> longList = productRepository.getIdsOfProducts(categoryId, paging);
            System.out.println(longList);
            for (Long l : longList)
            {
                list.add(viewSingleProductForCustomer(productRepository.findById(l).get().getId()));

            }
            return list;
        }
        else
        {
            Long[] l = {};
            throw new NotFoundException(messageSource.getMessage("not found",l,LocaleContextHolder.getLocale()));
        }

    }

    public List<ViewProductDto> getSimilarProducts(Long productId, Integer offset, Integer size, String sortByField) throws NotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        Long[] l1 = {};
        Pageable paging = PageRequest.of(offset, size, Sort.by(Sort.Order.asc(sortByField)));
        List<ViewProductDto> list = new ArrayList<>();

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
            throw new NotFoundException(messageSource.getMessage("not found",l1,LocaleContextHolder.getLocale()));
    }


}
