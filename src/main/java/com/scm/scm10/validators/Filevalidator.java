package com.scm.scm10.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Filevalidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024*1024*2; //2MB

    //type

    //height

    //width

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file==null|| file.isEmpty()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("file cannot be empty").addConstraintViolation();
            return true;
        }
        if (file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("size should be less than 2MB").addConstraintViolation();
            return false;
        }
//        try {
//            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
//            if (bufferedImage.getHeight())
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return true;
    }
}
