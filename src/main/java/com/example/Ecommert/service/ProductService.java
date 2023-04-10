package com.example.Ecommert.service;

import com.example.Ecommert.entity.Product;
import com.example.Ecommert.error.ProductNotFoundException;
import org.bson.Document;

import java.util.List;

public interface ProductService {
   Product save(Product product);

   List<Product> getAll();

   Product getProductById(String id) throws ProductNotFoundException;

   List<Document> getProductByQueryType(String queryType, String queryName);
}
