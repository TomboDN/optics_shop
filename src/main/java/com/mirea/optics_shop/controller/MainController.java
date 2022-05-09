package com.mirea.optics_shop.controller;

import com.mirea.optics_shop.dto.*;
import com.mirea.optics_shop.model.Product;
import com.mirea.optics_shop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;
    private final SessionService sessionService;
    private final OrderService orderService;
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;
    private final EmailService emailService;

    @RequestMapping("/productList/{category}")
    public String listProduct(@PathVariable String category, Model model) {
        List<ProductDto> allProductsByCategory = productService.findAllProductsByCategoryConvertProductDto(category);
        model.addAttribute("Products", allProductsByCategory);
        return category;
    }

    @RequestMapping({ "/buyProduct" })
    public String buyProduct(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "id", defaultValue = "") Long id) {

        Product product = null;
        if (id != null) {
            product = productService.findProductById(id);
        }
        if (product != null) {

            CartDto cartDto = sessionService.getCartInSession(request);

            ProductDto productDto = new ProductDto(product);

            cartDto.addProduct(productDto, 1);
            sessionService.saveCartInSession(request, cartDto);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProduct(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "id", defaultValue = "") Long id) {
        Product product = null;
        if (id != null) {
            product = productService.findProductById(id);
        }
        if (product != null) {

            CartDto cartDto = sessionService.getCartInSession(request);

            ProductDto productDto = new ProductDto(product);

            cartDto.removeProduct(productDto);

            sessionService.saveCartInSession(request, cartDto);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartDecreaseProduct" })
    public String decreaseProduct(HttpServletRequest request, Model model, //
                                @RequestParam(value = "id", defaultValue = "") Long id) {
        Product product = null;
        if (id != null) {
            product = productService.findProductById(id);
        }
        if (product != null) {

            CartDto cartDto = sessionService.getCartInSession(request);

            ProductDto productDto = new ProductDto(product);

            cartDto.updateProduct(id, -1);

            sessionService.saveCartInSession(request, cartDto);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartIncreaseProduct" })
    public String increaseProduct(HttpServletRequest request, Model model, //
                                  @RequestParam(value = "id", defaultValue = "") Long id) {
        Product product = null;
        if (id != null) {
            product = productService.findProductById(id);
        }
        if (product != null) {

            CartDto cartDto = sessionService.getCartInSession(request);

            ProductDto productDto = new ProductDto(product);

            cartDto.updateProduct(id, 1);

            sessionService.saveCartInSession(request, cartDto);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartDto myCart = sessionService.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCartCustomer" })
    public String shoppingCartCustomerUpdate(HttpServletRequest request) {

        CartDto cartDto = sessionService.getCartInSession(request);

        if (cartDto.isEmpty()) {

            return "redirect:/shoppingCart";
        }
        cartDto.setUserDto(customUserDetailService.getUserDtoByUsername(request.getUserPrincipal().getName()));

        sessionService.saveCartInSession(request, cartDto);

        return "shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCartFinalize" })
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartDto cartDto = sessionService.getCartInSession(request);

        if (cartDto.isEmpty()) {

            return "redirect:/shoppingCart";
        } else if (cartDto.getUserDto() == null) {

            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderService.saveOrder(cartDto);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/shoppingCart";
        }

        sessionService.removeCartInSession(request);

        sessionService.storeLastOrderedCartInSession(request, cartDto);

        CartDto lastOrderedCart = sessionService.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);

        emailService.sendSimpleMailMessage(cartDto.getUserDto().getEmail(), "Заказ в Optic City",
                cartDto.getUserDto().getFirstName() + ", спасибо за заказ №" + cartDto.getOrderNumber() + " в Optic City!");

        return "shoppingCartFinalize";
    }

    @RequestMapping("/orderList")
    public String orderList(HttpServletRequest request, Model model) {
        Long userId = userService.getUserByEmail(request.getUserPrincipal().getName()).getId();
        List<OrderDto> orderDtoList = orderService.findAllByUserIdOrderByCreationDateDescConvertOrderDto(userId);
        model.addAttribute("orders", orderDtoList);
        return "orderList";
    }

    @RequestMapping("/order")
    public String orderView(@RequestParam("orderId") Long orderId, Model model) {
        if (orderId == null){
            return "redirect:/orderList";
        }
        OrderDto orderDto = orderService.findOrderByIdConvertOrderDto(orderId);
        List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailByOrderIdConvertOrderDetailDto(orderId);
        orderDto.setDetails(orderDetailDtoList);

        model.addAttribute("orderDto", orderDto);
        return "order";
    }
}
