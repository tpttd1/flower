package application.controller.web;

import application.data.model.PaginableItemList;
import application.data.model.Product;
import application.data.service.CategoryService;
import application.data.service.ProductService;
import application.model.CategoryDataModel;
import application.model.ProductDetailModel;
import application.viewmodel.common.ProductVM;
import application.viewmodel.homelanding.BannerVM;
import application.viewmodel.homelanding.HomeLandingVM;
import application.viewmodel.homelanding.MenuItemVM;
import application.viewmodel.productindex.ProductSearchVM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;


@Controller
@RequestMapping(path = "/")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/")
    public String landing(Model model,
                          HttpServletResponse response,
                          @RequestHeader("User-Agent") String userAgent,
                          HttpServletRequest request,
                          final Principal principal) {

//
//        System.out.println("====================");
//        System.out.println(userAgent);
//        System.out.println("IP: " + request.getRemoteAddr());
//
//        Cookie cookies[] = request.getCookies();
//        UUID uuid = UUID.randomUUID();
//        String guid = uuid.toString();
//        boolean flag_guild = true;
//        String user_guild = "";
//
//        if (cookies != null) {
//            Arrays.stream(cookies)
//                    .forEach(c -> System.out.println(c.getName() + "=" + c.getValue()));
//
//            for(Cookie c : cookies){
//                if(c.getName().equals("User_Guild")){
//                    if(c.getValue() != null){
//                        flag_guild = false;
//                        user_guild = c.getValue();
//                    }
//                }
//            }
//        }

//        ArrayList<Order> orders = orderService.getListOrder();
//        for(Order o : orders){
//            ArrayList<OrderProduct> orderProducts = orderService.getListOrderProductByOrderId(o.getId());
//            if(orderProducts == null){
//                orderService.deleteOrder(o.getId());
//            }
//        }
//
//        if(principal == null && flag_guild ){
//            response.addCookie(new Cookie("User_Guild",guid));
//            System.out.print("User Unknown : ");
//            System.out.println(guid);
//            orderService.createOrderByUserguild(guid);
//            Order order = orderService.findOrderByUserguild(guid);
//            response.addCookie(new Cookie("OrderId",Integer.toString(order.getId())));
//            response.addCookie(new Cookie("User_Id", null));
//        }else if(principal == null && !flag_guild){
//            System.out.print("User Unknown is checked : ");
//            System.out.println(user_guild);
//            response.addCookie(new Cookie("User_Id", null));
//        }else if(principal != null && !flag_guild) {
//            String userId = userService.findUserByUsername(principal.getName()).getId();
//            String updateUserId = orderService.setUserGuild(user_guild,userId);
//            System.out.println("Update UserId into Order :"+updateUserId);
//            response.addCookie(new Cookie("User_Id", userId));
//        }
//        else if(principal != null && flag_guild){
//            String userid = userService.findIdByUsername(principal.getName());
//            response.addCookie(new Cookie("User_Id",userid));
//            Order existOrder = orderService.findOrderByUserIdAndStatusid(userid,unpaid);
//            if(existOrder != null){
//                response.addCookie(new Cookie("OrderId",Integer.toString(existOrder.getId())));
//                response.addCookie(new Cookie("User_Guild",existOrder.getUserguild()));
//            }else {
//                if(orderService.createOrderByUserIdAndUser_guild(userid,guid)){
//                    existOrder = orderService.findOrderByUserIdAndStatusid(userid,unpaid);
//                    response.addCookie(new Cookie("OrderId",Integer.toString(existOrder.getId())));
//                    response.addCookie(new Cookie("User_Guild",guid));
//                }
//            }
//        }
//



        HomeLandingVM vm = new HomeLandingVM();

//        this.setLayoutHeaderVM(vm);

        ModelMapper modelMapper = new ModelMapper();


        ArrayList<BannerVM> listBanners = new ArrayList<>();
        listBanners.add(new BannerVM("https://media.static-adayroi.com/sys_master/h75/hac/15516679143454.jpg", "Tươi"));
        listBanners.add(new BannerVM("http://www.creavini.it/wp-content/uploads/2017/05/uva.png", "Nho Mỹ"));
        listBanners.add(new BannerVM("https://edeka-tank.de/wp-content/uploads/2017/01/Fotolia_43618946_Tomaten_mood.jpg", "Cà Chua"));
        listBanners.add(new BannerVM("https://dalat.net.vn/images/uploads/gia-dau-tay-da-lat-2.jpg", "Dâu Tây"));

//        ArrayList<MenuItemVM> listVtMenuItems = new ArrayList<>();
//        for(CategoryInfor cat : getCategories()){
//            ArrayList<CategoryDetailModel> categoryDetailModels = new ArrayList<>();
//            listVtMenuItems.add(new MenuItemVM(cat.getParentid(),cat.getParentname(),"/"));
//            if(cat.getCategories() != null){
//                for(CategoryDetailModel categoryDetailModel : cat.getCategories()){
//                    categoryDetailModels.add(modelMapper.map(categoryDetailModel,CategoryDetailModel.class));
//                }
//                for(CategoryDetailModel categoryDetailModel : categoryDetailModels){
//                    listVtMenuItems.get(cat.getParentid() - DEFAULT_PARENT_ID).getChildren().add(new MenuItemVM(categoryDetailModel.getId(),categoryDetailModel.getName(),"/category/detail/"+categoryDetailModel.getId()));
//                }
//            }
//        }


        PaginableItemList<Product> paginableItemListHot = productService.getListProducts(8, 0);
        ArrayList<ProductVM> listHotProductVMs = new ArrayList<>();
        for(Product product : paginableItemListHot.getListData()) {
            ProductVM productVM = modelMapper.map(product, ProductVM.class);
            listHotProductVMs.add(productVM);
        }

        PaginableItemList<Product> paginableItemListTrend = productService.getListProducts(8, 1);
        ArrayList<ProductVM> listTrendProductVMs = new ArrayList<>();
        for(Product product : paginableItemListTrend.getListData()) {
            ProductVM productVM = modelMapper.map(product, ProductVM.class);
            listTrendProductVMs.add(productVM);
        }

        vm.setListBanners(listBanners);
//        vm.setListVtMenuItemsAside(listVtMenuItems);
        vm.setListHotProducts(listHotProductVMs);
        vm.setListTrendProducts(listTrendProductVMs);

        model.addAttribute("vm", vm);
        return "index";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin/dashboard";
    }


}
