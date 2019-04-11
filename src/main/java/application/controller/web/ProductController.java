package application.controller.web;

import application.data.model.Product;
import application.data.repository.ProductRepository;
import application.data.service.CategoryService;
import application.data.service.ProductService;
import application.model.*;
import application.viewmodel.productindex.ProductSearchVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

//    @RequestParam("search") String search,

    @GetMapping(path = "/searchs/{search}")
    public BaseApiResult baseApiResult(@PathVariable String search) {
        DataApiResult result = new DataApiResult();
        try {
            ArrayList<Product> products = productService.search(search);
            result.setData(products);
            result.setSuccess(true);
            result.setMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            result.setData(e.getMessage());
            result.setSuccess(false);
            result.setMessage("fail");
        }
        return result;
    }
    @GetMapping("/search/{search}")
    public BaseApiResult search(Model model, @PathVariable String search) {
        DataApiResult result = new DataApiResult();
        ModelMapper modelMapper = new ModelMapper();
        ProductSearchVM vm = new ProductSearchVM();
        vm.setKeyword(search);
        ArrayList<ProductDetailModel> list = new ArrayList<>();
        Collection<Product> products = productService.search(search);
        for (Product product : products) {
            ProductDetailModel pro = new ProductDetailModel();
            pro.setId(product.getId());
            pro.setName(product.getName());
            pro.setDescription(product.getDescription());
            pro.setImage(product.getImage());
            pro.setCreatedDate(product.getCreatedDate());
            pro.setPrice(product.getPrice());
            pro.setQuantity(product.getQuantity());

            product.setCategory(categoryService.findOne(product.getCategory().getId()));

            CategoryDataModel cat = modelMapper.map(product.getCategory(), CategoryDataModel.class);
            pro.setCategory(cat);

            list.add(pro);
        }
        result.setData(list);
        result.setMessage("success");
        result.setSuccess(true);
//        vm.setProductDetailModels(list);
//
//        model.addAttribute("vm", vm);
//        return "search";
        return result;
    }


    @PostMapping("/new")
    public String addNewProducts() {
        return "admin/addnew";
    }



}
