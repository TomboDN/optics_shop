package com.mirea.optics_shop.service;

import com.mirea.optics_shop.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
@RequiredArgsConstructor
public class SessionService {
    private final CustomUserDetailService customUserDetailService;
    public CartDto getCartInSession(HttpServletRequest request){
        CartDto cartDto = (CartDto) request.getSession().getAttribute("myCart");

        if (cartDto == null){
            cartDto = new CartDto();
            cartDto.setUserDto(customUserDetailService.getUserDtoByUsername(request.getUserPrincipal().getName()));
            request.getSession().setAttribute("myCart", cartDto);
        }
        return cartDto;
    }

    public void saveCartInSession(HttpServletRequest request, CartDto cartDto){
        request.getSession().setAttribute("myCart", cartDto);
    }

    public void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("myCart");
    }

    public void storeLastOrderedCartInSession(HttpServletRequest request, CartDto cartDto) {
        request.getSession().setAttribute("lastOrderedCart", cartDto);
    }

    public CartDto getLastOrderedCartInSession(HttpServletRequest request) {
        return (CartDto) request.getSession().getAttribute("lastOrderedCart");
    }
}
