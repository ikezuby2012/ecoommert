package com.example.Ecommert.implementation;

import com.example.Ecommert.entity.Product;
import com.example.Ecommert.error.ProductNotFoundException;
import com.example.Ecommert.repository.ProductRepository;
import com.example.Ecommert.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service @Slf4j
public class ProductServiceImpl implements ProductService {
   @Autowired
   private ProductRepository productRepository;

   @Autowired
   private MongoTemplate mongoTemplate;

   @Override
   public Product save(Product product) {
      return productRepository.save(product);
   }

   @Override
   public List<Product> getAll() {
      return productRepository.findAll();
   }

   @Override
   public Product getProductById(String id) throws ProductNotFoundException {
      Optional<Product> product = productRepository.findById(id);

      if (product.isEmpty()) {
         throw new ProductNotFoundException("product not found");
      }
      return product.get();
   }

   @Override
   public List<Document> getProductByQueryType(String queryType, String queryName) {
      log.info("queryType is " + queryType + ", queryName is " + queryName);
      Aggregation agg;
      if (queryType.equals("category")) {
         agg = newAggregation(
             match(Criteria.where("category").regex(queryName, "i")),
             Aggregation.sort(Sort.Direction.ASC, "createdAt")
         );
      } else {
         agg = newAggregation(
             match(Criteria.where("name").regex(queryName, "i")),
             Aggregation.sort(Sort.Direction.ASC, "createdAt")
         );
      }
      return mongoTemplate.aggregate(agg, Product.class, Document.class).getMappedResults();
//      MatchOperation matchOperation = match(Criteria.where("category").regex(queryType, "i"));
   }
}
