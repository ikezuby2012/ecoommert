package com.example.Ecommert.controller;

import com.example.Ecommert.entity.Product;
import com.example.Ecommert.error.ProductNotFoundException;
import com.example.Ecommert.model.response.CustomResponse;
import com.example.Ecommert.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1") @Slf4j
public class ProductController {
   @Autowired
   private ProductService productService;

   @PostMapping("/product")
   public ResponseEntity<CustomResponse> createNewProduct(@RequestBody Product product) {
      Product product1 = productService.save(product);
      CustomResponse customResponse = new CustomResponse(CREATED, true, CREATED.toString(), 0, product1);
      return ResponseEntity.status(CREATED).body(customResponse);
   }

   @GetMapping("/product")
   public ResponseEntity<CustomResponse> getAllProducts() {
      List<Product> products = productService.getAll();
      return ResponseEntity.ok().body(new CustomResponse(OK, true, "done ", products.size(), products));
   }

   @GetMapping("/product/{id}")
   public ResponseEntity<CustomResponse> getProductById(@PathVariable String id) throws ProductNotFoundException {
      Product product = productService.getProductById(id);
      return ResponseEntity.ok().body(new CustomResponse(OK, true, "done ", product.toString().length(), product));
   }
   @GetMapping("/product/{queryType}/{queryName}")
   public ResponseEntity<CustomResponse> getProductByQueryType(@PathVariable String queryType, @PathVariable String queryName) throws ProductNotFoundException {
      String[] queryTypes = {"category", "name"};
      AtomicBoolean isMatchQueryType = new AtomicBoolean(false);
      Arrays.stream(queryTypes).forEach(el -> {
         if (el.equals(queryType)) {
            isMatchQueryType.set(true);
            log.info("match was found");
         }
      });
      if (!isMatchQueryType.get()) throw new ProductNotFoundException("query type not allowed");

      List<Document> products = productService.getProductByQueryType(queryType, queryName);

      return ResponseEntity.ok().body(new CustomResponse(OK, true, "done ", products.size(), products));
   }
}
