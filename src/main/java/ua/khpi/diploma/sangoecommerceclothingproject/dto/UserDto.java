package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String nickName;
    private String firstName;
    private String lastName;
    private String password;
    private String matchingPassword;
    private String email;
    private Role role;
}