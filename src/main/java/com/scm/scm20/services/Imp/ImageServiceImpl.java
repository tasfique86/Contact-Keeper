package com.scm.scm20.services.Imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm20.helper.AppConstents;
import com.scm.scm20.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private  Cloudinary cloudinary;

// ----------this is work as Autowired-----or---manually object creat
//    public ImageServiceImpl(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//    }

    @Override
    public String uploadImage(MultipartFile contactImage,String fileName) {


        try{
            byte[] data=new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",fileName ));
            return this.getUlrFromPublicId(fileName);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUlrFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(new Transformation<>()
                        .width(AppConstents.CONTACT_IMAGE_WIDTH)
                        .height(AppConstents.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstents.CONTACT_IMAGE_CROP)
                )
                .generate(publicId);
    }
}
