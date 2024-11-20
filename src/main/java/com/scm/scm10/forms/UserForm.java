package com.scm.scm10.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
  @NotBlank(message = "Username is required")
  @Size(min=3,message = "minimum 3 character is required")
  private String name;
  @Email(message = "invalid email address")
  private String email;
  @NotBlank(message = "Password is required")
  @Size(min=6,message = "minimum 6 character is required")
  private String password;
  @NotBlank(message = "About is required")
  private String about;
  @Size(min = 8,max = 12,message = "Invalid phone number")
  private String phoneNumber;
}
