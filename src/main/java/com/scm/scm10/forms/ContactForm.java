package com.scm.scm10.forms;

import com.scm.scm10.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "email is required [example@gmail.com]")
    @Email(message = "invalid email address")
    private String email;
    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedinLink;

    @ValidFile(message = "Invalid file")
    private MultipartFile contactImage;

    private String picture;

}
