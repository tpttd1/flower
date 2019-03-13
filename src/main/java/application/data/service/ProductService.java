package application.data.service;

import application.data.model.Category;
import application.data.model.Product;
import application.data.repository.ProductRepository;
import application.model.ProductDetailModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public ArrayList<Product> listAll() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

    public Product findOne(int id) {
        return productRepository.findOne(id);
    }

    public boolean updateProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        try {
            productRepository.delete(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public Collection<Product> search(String search) {
        try {
             return productRepository.findAllByName(search);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Product> listByCat(int id) {
        try {
//            return productRepository.listByCat(category);
            return productRepository.find(id);
        } catch (Exception e) {
            return null;
        }
    }

}
