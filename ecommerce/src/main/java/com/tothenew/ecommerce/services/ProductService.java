package com.tothenew.ecommerce.services;

import com.tothenew.ecommerce.dto.ProductDto;
import com.tothenew.ecommerce.dto.ProductVariationDto;
import com.tothenew.ecommerce.entity.Category;
import com.tothenew.ecommerce.entity.Product;
import com.tothenew.ecommerce.entity.ProductVariation;
import com.tothenew.ecommerce.entity.Seller;
import com.tothenew.ecommerce.mailing.SendMail;
import com.tothenew.ecommerce.repository.CategoryRepository;
import com.tothenew.ecommerce.repository.ProductRepository;
import com.tothenew.ecommerce.repository.SellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            return "validation failed";
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
        String message;
        Optional<Category> savedCategory=categoryRepository.findById(productDto.getCategoryId());
        if(!savedCategory.isPresent()){
            message="category doesn't exist";
            return message;
        }
        Category category=savedCategory.get();
        if(!(category.getSubCategories()==null || category.getSubCategories().isEmpty())){
            message="not a leaf category";
            return message;
        }
        Product product=productRepository.findByName(productDto.getName());
        if(product.getCategory().getId().equals(productDto.getCategoryId())){
            if(product.getSeller().getEmail().equals(email)){
                message="this product already exist";
                return message;
            }
        }
        message="success";
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

    public Object getProduct(Long id, String email) {
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
            message="product not found";
            return message;
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
}
