package com.codewithzea.myprojecttracker.user.dto;




import com.codewithzea.myprojecttracker.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}

