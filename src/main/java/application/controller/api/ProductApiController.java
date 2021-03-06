package application.controller.api;

import application.data.model.Product;
import application.data.repository.CategoryRepository;
import application.data.service.CategoryService;
import application.data.service.ProductService;
import application.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile() throws IOException {
        String filename = "/static/img/cart.png";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

    @GetMapping("/listAll")
    public BaseApiResult listAllProduct() {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<Product> list = productService.listAll();
        ArrayList<ProductDetailModel> listAll = new ArrayList<>();
        try {
            for (Product product : list) {
                ProductDetailModel pro = new ProductDetailModel();
                pro.setId(product.getId());
                pro.setName(product.getName());
                pro.setDescription(product.getDescription());
                pro.setImage(product.getImage());
                pro.setCreatedDate(product.getCreatedDate());
                pro.setUpdatedDate(product.getUpdatedDate());
                pro.setQuantity(product.getQuantity());
                pro.setPrice(product.getPrice());

                product.setCategory(categoryService.findOne(product.getCategory().getId()));

                CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
                pro.setCategory(cat);

                listAll.add(pro);
                result.setData(listAll);
                result.setMessage("get all products");
                result.setSuccess(true);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("get product fail: " + e.getMessage());
        }

        return result;
    }

    @PostMapping("/addNew")
    public BaseApiResult allNewProduct(@RequestBody ProductDataModel product) {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
        try {
            if(!"".equals(product.getName()) && !"".equals(product.getImage()) && !"".equals(product.getDescription())  ) {
                Product product1 = modelMapper.map(product, Product.class);
                product1.setCategory(categoryService.findOne(product.getCategoryId()));
                product1.setCreatedDate(new Date());
                productService.addNewProduct(product1);

                result.setMessage("save successfully");
                result.setSuccess(true);
                result.setData(product1.getId());
            } else {
                result.setMessage("fail");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setMessage("fail");
            result.setSuccess(false);
        }
        return result;
    }

    @GetMapping("/getOne/{id}")
    public ProductDetailModel getOne(@PathVariable int id) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDetailModel productDetailModel = new ProductDetailModel();

        Product product = productService.findOne(id);
        productDetailModel = modelMapper.map(product, ProductDetailModel.class);

        product.setCategory(categoryService.findOne(product.getCategory().getId()));
        CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
        productDetailModel.setCategory(cat);

        return productDetailModel;
    }

    @GetMapping("/detail/{id}")
    public BaseApiResult detail(@PathVariable int id) {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
        ProductDetailModel productDetailModel = new ProductDetailModel();

        try {
            Product product = productService.findOne(id);
            productDetailModel = modelMapper.map(product, ProductDetailModel.class);

            product.setCategory(categoryService.findOne(product.getCategory().getId()));
            CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
            productDetailModel.setCategory(cat);

            result.setMessage("success");
            result.setSuccess(true);
            result.setData(productDetailModel);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("fail");
        }

        return result;
    }

    @PostMapping("/update/{id}")
    public BaseApiResult updateProduct(@PathVariable int id, @RequestBody ProductDataModel product) {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
        Product existProduct = productService.findOne(id);

        try {
            if(!"".equals(product.getName()) && !"".equals(product.getDescription())
                    && !"".equals(product.getImage())) {
                if (existProduct == null) {
                    result.setSuccess(false);
                    result.setMessage("Invalid product");
                } else {
                    Product product1 = modelMapper.map(product, Product.class);
                    product1.setId(id);
                    product1.setCreatedDate(existProduct.getCreatedDate());
                    product1.setUpdatedDate(new Date());
                    productService.updateProduct(product1);
                    System.out.println(product1.getName());
                    result.setMessage("Successful");
                    result.setSuccess(true);
                }
            }
        } catch (Exception e) {
            result.setMessage("Error");
            result.setSuccess(false);
        }

        return result;
    }

    @DeleteMapping("/delete/{id}")
    public BaseApiResult deleteProduct(@PathVariable int id) {
        DataApiResult result = new DataApiResult();
        if (productService.deleteProduct(id)) {
            result.setData(id);
            result.setSuccess(true);
            result.setMessage("Delete successfully");
        } else {
            result.setData(id);
            result.setSuccess(false);
            result.setMessage("Delete fail");
        }
        return result;
    }

    @GetMapping("/listByCat/{id}")
    public BaseApiResult listAllProductByCat(@PathVariable int id) {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
//        Product pro = new Product();
//        pro.setCategory(categoryRepository.findOne(id));
        ArrayList<Product> products = productService.listByCat(id);

        ArrayList<ProductDetailModel> list = new ArrayList<>();

        try {
            for (Product product : products) {
                ProductDetailModel productDetailModel = new ProductDetailModel();
                productDetailModel.setId(product.getId());
                productDetailModel.setName(product.getName());
                productDetailModel.setDescription(product.getDescription());
                productDetailModel.setImage(product.getImage());
                productDetailModel.setUpdatedDate(product.getUpdatedDate());
                productDetailModel.setCreatedDate(product.getCreatedDate());
                productDetailModel.setQuantity(product.getQuantity());
                productDetailModel.setPrice(product.getPrice());

                product.setCategory(categoryService.findOne(product.getCategory().getId()));

                CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
                productDetailModel.setCategory(cat);

                list.add(productDetailModel);
            }
            result.setSuccess(true);
            result.setMessage("get product success");
            result.setData(list);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("get product fail");
        }
        return result;
    }
}
