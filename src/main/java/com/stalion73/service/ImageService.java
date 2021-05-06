package com.stalion73.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.stalion73.model.image.Image;
import com.stalion73.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

    ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Image> findByName(String name){
        return this.imageRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Collection<Image> findByBusiness(String business_id){
        return this.imageRepository.findByBusiness(business_id);
    }

    @Transactional
    public void save(Image image) {
        this.imageRepository.save(image);
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
