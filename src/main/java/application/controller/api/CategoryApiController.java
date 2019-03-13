package application.controller.api;

import application.data.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryApiController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductApiController productApiController;


}
