package com.hangha.reviewservice.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class S3DummyService {

    public String uploadFile(MultipartFile file) {
        if(file==null||file.isEmpty()){
            return null;
        }

        return UUID.randomUUID()+"_"+file.getOriginalFilename();
    }
}
