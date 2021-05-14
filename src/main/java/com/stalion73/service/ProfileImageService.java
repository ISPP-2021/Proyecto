package com.stalion73.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.stalion73.model.image.ProfileImage;
import com.stalion73.repository.ProfileImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileImageService {

    ProfileImageRepository profileImageRepository;

    @Autowired
    public ProfileImageService(ProfileImageRepository profileImageRepository){
        this.profileImageRepository = profileImageRepository;
    }

    @Transactional(readOnly = true)
    public Optional<ProfileImage> findByName(String name){
        return this.profileImageRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<ProfileImage> findByUsername(String username){
        return this.profileImageRepository.findByUsername(username);
    }

    @Transactional
    public void save(ProfileImage profileImage) {
        this.profileImageRepository.save(profileImage);
    }


    public  byte[] compress(byte[] data) throws IOException {  
        Deflater deflater = new Deflater();  
        deflater.setInput(data);  
     
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);   
     
        deflater.finish();  
        byte[] buffer = new byte[1024];   
        while (!deflater.finished()) {  
         int count = deflater.deflate(buffer);
         outputStream.write(buffer, 0, count);   
        }  
        outputStream.close();  
        byte[] output = outputStream.toByteArray();  
       
        return output;  
       }  
     
       public  byte[] decompress(byte[] data) throws IOException, DataFormatException {  
        Inflater inflater = new Inflater();   
        inflater.setInput(data);  
     
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
        byte[] buffer = new byte[1024];  
        while (!inflater.finished()) {  
         int count = inflater.inflate(buffer);  
         outputStream.write(buffer, 0, count);  
        }  
        outputStream.close();  
        byte[] output = outputStream.toByteArray();  
      
        return output;  
       }



}
