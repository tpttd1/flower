package application.controller.api;

import application.data.model.Product;
import application.data.service.CategoryService;
import application.data.service.ProductService;
import application.model.CategoryDataModel;
import application.model.ProductDetailModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;


@RestController("/")
public class SearchApiController {

    private static final Logger logger = LogManager.getLogger(SearchApiController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/search")
    public ArrayList<ProductDetailModel> search(@RequestParam String search) {
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<ProductDetailModel> list = new ArrayList<>() ;
        System.out.println(search);
        Collection<Product> products = productService.search("%" +search+ "%");
        for (Product product : products) {
            ProductDetailModel productDetailModel = new ProductDetailModel();
            productDetailModel.setId(product.getId());
            productDetailModel.setName(product.getName());
            productDetailModel.setImage(product.getImage());
            productDetailModel.setDescription(product.getDescription());
            productDetailModel.setCreatedDate(product.getCreatedDate());
            productDetailModel.setUpdatedDate(product.getUpdatedDate());
            productDetailModel.setQuantity(product.getQuantity());
            productDetailModel.setPrice(product.getPrice());

            product.setCategory(categoryService.findOne(product.getCategory().getId()));

            CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
            productDetailModel.setCategory(cat);

            list.add(productDetailModel);
        }

        return list;
    }
}
