package com.scm.scm10.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
     String uploadImage(MultipartFile contactForm, String fileName);

     String getUrlFromPublicId(String publicId);
}
