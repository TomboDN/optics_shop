package com.mirea.optics_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable{
    private int orderNumber;
    private UserDto userDto;
    private List<CartLineDto> cartLines = new ArrayList<>();

    private CartLineDto findLineById(Long id) {
        for (CartLineDto line : this.cartLines) {
            System.out.println(line.getProductDto().getId());
            System.out.println(id);
            if (line.getProductDto().getId().equals(id)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductDto productDto, int quantity) {
        CartLineDto line = this.findLineById(productDto.getId());

        if (line == null) {
            line = new CartLineDto();
            line.setQuantity(0);
            line.setProductDto(productDto);
            this.cartLines.add(line);
        }
        System.out.println(isEmpty());
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void updateProduct(Long id, int amount) {
        CartLineDto line = this.findLineById(id);
        if (line != null) {
            int newQuantity = line.getQuantity() + amount;

            if (newQuantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(newQuantity);
            }
        }
    }

    public void removeProduct(ProductDto productDto) {
        CartLineDto line = this.findLineById(productDto.getId());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineDto line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartLineDto line : this.cartLines) {
            total += line.getTotalPrice();
        }
        return total;
    }

    public void updateQuantity(CartDto cartDto) {
        if (cartDto != null) {
            List<CartLineDto> lines = cartDto.getCartLines();
            for (CartLineDto line : lines) {
                this.updateProduct(line.getProductDto().getId(), line.getQuantity());
            }
        }
    }
}
