package com.scm.scm20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {

    String uploadImage(MultipartFile file,String fileName);
    String getUlrFromPublicId(String publicId   );

}
