package com.mirea.optics_shop.dto;

import com.mirea.optics_shop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {
    @NotEmpty(message = "Поле имени не может быть пустым")
    private String firstName;

    @NotEmpty(message = "Поле фамилии не может быть пустым")
    private String lastName;

    @NotEmpty(message = "Поле почты не может быть пустым")
    @Email(message = "Пожалуйста используйте валидную почту")
    private String email;

    @Pattern(regexp = "\\+7-[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}",
            message = "Формат поля телефона: +7-xxx-xxx-xx-xx")
    @NotEmpty(message = "Поле телефона не может быть пустым")
    private String phone;

    @NotEmpty(message = "Поле пароля не может быть пустым")
    private String password;

    public UserDto(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
