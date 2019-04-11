package application.viewmodel.productindex;

import application.model.ProductDetailModel;

import java.util.ArrayList;

public class ProductSearchVM {
    private String keyword;
    private ArrayList<ProductDetailModel> productDetailModels;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArrayList<ProductDetailModel> getProductDetailModels() {
        return productDetailModels;
    }

    public void setProductDetailModels(ArrayList<ProductDetailModel> productDetailModels) {
        this.productDetailModels = productDetailModels;
    }
}
