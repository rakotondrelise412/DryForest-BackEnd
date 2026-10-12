package org.ong.dryforest.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequestDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
}
