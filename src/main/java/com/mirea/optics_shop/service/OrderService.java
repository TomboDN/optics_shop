package com.mirea.optics_shop.service;

import com.mirea.optics_shop.dto.*;
import com.mirea.optics_shop.model.*;
import com.mirea.optics_shop.repository.OrderDetailRepository;
import com.mirea.optics_shop.repository.OrderRepository;
import com.mirea.optics_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    private int getMaxOrderNumber(){
        List<Order> all = orderRepository.findAll();
        if (all.isEmpty()){
            return 0;
        }
        int counter = 0;
        for (Order order : all){
            if (order.getOrderNumber() > counter){
                counter = order.getOrderNumber();
            }
        }
        return counter;
    }

    public void saveOrder(CartDto cartDto){
        Order order = new Order();
        int orderNumber = getMaxOrderNumber() + 1;
        order.setOrderNumber(orderNumber);
        order.setCreationDate(new Date());
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setUserId(userRepository.findByEmail(cartDto.getUserDto().getEmail()).getId());

        orderRepository.save(order);

        List<CartLineDto> lines = cartDto.getCartLines();

        for (CartLineDto line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setTotalPrice(line.getTotalPrice());
            detail.setPrice(line.getProductDto().getPrice());
            detail.setQuantity(line.getQuantity());

            Long id = line.getProductDto().getId();
            Product product = productService.findProductById(id);
            detail.setProduct(product);

            orderDetailRepository.save(detail);
        }
        cartDto.setOrderNumber(orderNumber);
    }

    public List<OrderDto> findAllOrderConvertOrderDto(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders){
            orderDtos.add(new OrderDto(order));
        }
        return orderDtos;
    }

    public Order findOrderById(Long id){
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public List<OrderDto> findAllByUserIdOrderByCreationDateDescConvertOrderDto(Long id){
        List<Order> userOrderList = orderRepository.findAllByUserIdOrderByCreationDateDesc(id);
        List<OrderDto> userOrderDtoList = new ArrayList<>();
        for (Order order : userOrderList){
            userOrderDtoList.add(new OrderDto(order));
        }
        return userOrderDtoList;
    }

    public OrderDto findOrderByIdConvertOrderDto(Long id){
        Order order = findOrderById(id);
        if (order == null){
            return null;
        } else return new OrderDto(order);
    }

    public List<OrderDetailDto> findOrderDetailByOrderIdConvertOrderDetailDto(Long id){
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(id);
        for (OrderDetail orderDetail : orderDetails){
            orderDetailDtoList.add(new OrderDetailDto(orderDetail));
        }
        return  orderDetailDtoList;
    }
}
