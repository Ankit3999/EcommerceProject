package com.tothenew.ecommerce.dao;

import com.tothenew.ecommerce.entity.Customer;
import com.tothenew.ecommerce.entity.ProductVariation;
import com.tothenew.ecommerce.entity.Seller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class SellerImageDao {
    String firstPath = System.getProperty("user.dir");

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


    public ResponseEntity<Object> uploadSingleImageForProductVariation(MultipartFile file, ProductVariation productVariation) throws IOException {

        File convertfile = new File(firstPath+"/src/main/resources/productVariation/images" + file.getOriginalFilename());
        convertfile.createNewFile();
        String fileBasePath = firstPath+"/src/main/resources/productVariation/";
        Path path = Paths.get(fileBasePath + convertfile.getName());
        FileOutputStream fout = new FileOutputStream(convertfile);
        System.out.println(convertfile.getAbsolutePath());
        fout.write(file.getBytes());
        fout.close();
        Optional<String> ext = getExtensionByStringHandling(convertfile.getName());
        int count = 0;
        File dir = new File(fileBasePath);

        if (ext.isPresent()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file1 : files) {
                    String value = productVariation.getId().toString();
                    if (file1.getName().startsWith(value)) {
                        count++;
                        System.out.println(count);
                    }
                }
                String value1 = productVariation.getId().toString();
                value1 = value1 + count;
                Files.move(path, path.resolveSibling(value1 + "." + ext.get()));
            }
        } else {
            throw new RuntimeException();
        }
        return new ResponseEntity<>("file added", HttpStatus.OK);
    }

    public ResponseEntity<Object> uploadSingleImage(MultipartFile file, Seller seller) throws IOException {
        File convertfile = new File(firstPath+"/src/main/resources/users/images" + file.getOriginalFilename());
        convertfile.createNewFile();
        String fileBasePath = firstPath+"/src/main/resources/users/";
        Path path = Paths.get(fileBasePath + convertfile.getName());
        FileOutputStream fout = new FileOutputStream(convertfile);
        fout.write(file.getBytes());
        fout.close();
        Optional<String> ext = getExtensionByStringHandling(convertfile.getName());
        File dir = new File(fileBasePath);
        if (ext.isPresent())
        {
            if (dir.isDirectory())
            {
                File[] files = dir.listFiles();
                for (File file1 : files) {
                    String value = seller.getId().toString();
                    if (file1.getName().startsWith(value)) {
                        Files.delete(Paths.get(file1.getPath()));
                    }
                }
                Files.move(path, path.resolveSibling(seller.getId() + "." + ext.get()));
            }


        } else {
            throw new RuntimeException();
        }
        return new ResponseEntity<>("file added", HttpStatus.OK);
    }
}
