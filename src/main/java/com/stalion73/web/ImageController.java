package com.stalion73.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.Random;
import java.util.Set;


import com.stalion73.model.image.Image;
import com.stalion73.model.Business;

import com.stalion73.service.BusinessService;

import com.stalion73.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;

import java.io.IOException;


@RestController()
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {
   
    private ImageService imageService;

    private BusinessService businessService;

    
    @Autowired
    public ImageController(ImageService imageService, BusinessService businessService){
        this.imageService = imageService;
        this.businessService = businessService;
    }

    @RequestMapping(value = "/{name}" , method = RequestMethod.GET)
    public ResponseEntity<?> getImage(@PathVariable("name") String name) throws IOException, DataFormatException {
        HttpHeaders headers = new HttpHeaders();
        Image img = new Image();

        Optional<Image> image = this.imageService.findByName(name);
        if(!image.isPresent()){
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
            .headers(headers)
            .body(Problem.create()
                    .withTitle("Not found")
                    .withDetail("The image name didn't match any record."));
        }
        headers.setContentType(MediaType.IMAGE_JPEG);
        img.setName(image.get().getName());
        img.setType(image.get().getType());
        img.setImg(this.imageService.decompress(image.get().getImg()));
        return new ResponseEntity<>(img.getImg(), headers, HttpStatus.OK);
    
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // Testing purpose
        //headers.setContentType(MediaType.IMAGE_JPEG);
        Random randGen = new Random();
        Integer uid = randGen.nextInt(1000000);
        Image image = new Image();
        byte[] img = this.imageService.compress(file.getBytes());
        image.setName(uid + "_" + file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setDecompress(file.getBytes().length);
        image.setCompress(img.length);
        image.setImg(img);

        this.imageService.save(image);

        //byte[] media = file.getBytes();

       return new ResponseEntity<>(image, headers, HttpStatus.OK);

    
    }

    @RequestMapping(value = "/business/{business_id}" , method = RequestMethod.GET)
    public ResponseEntity<?> getImages(@PathVariable("business_id") Integer business_id) throws IOException, DataFormatException {
        HttpHeaders headers = new HttpHeaders();

        Set<Image> comprImgs = new HashSet<>(this.imageService.findByBusiness(business_id));
        Set<Image> descompImgs = new HashSet<>();
        for(Image compress : comprImgs){
            Image img = compress;
            img.setImg(this.imageService.decompress(compress.getImg()));
            descompImgs.add(img);
        }
        return new ResponseEntity<>(descompImgs, headers, HttpStatus.OK);
    
    }
    
    @PostMapping("/upload/batch/{business_id}")
    public ResponseEntity<?> uploadImages(@PathVariable("business_id") Integer business_id,
        @RequestParam("files") MultipartFile ... files) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // Testing purpose
        //headers.setContentType(MediaType.IMAGE_JPEG);
        Random randGen = new Random();
        Set<Image> images = new HashSet<>();
        Business business = this.businessService.findById(business_id).get();

        for(MultipartFile file : Arrays.asList(files)){
            Integer uid = randGen.nextInt(1000000);
            Image image = new Image();
            byte[] img = this.imageService.compress(file.getBytes());
            image.setName(uid + "_" + file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setDecompress(file.getBytes().length);
            image.setCompress(img.length);
            image.setImg(img); 
            image.setBusiness(business);

            this.imageService.save(image);
            images.add(image);
        }
  


        //byte[] media = file.getBytes();

       return new ResponseEntity<>(images, headers, HttpStatus.OK);

    
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImageBusiness(@RequestParam("image") MultipartFile file,
    @PathVariable("id") Integer id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // Testing purpose
        //headers.setContentType(MediaType.IMAGE_JPEG);
        Random randGen = new Random();
        Integer uid = randGen.nextInt(1000000);
        Image image = new Image();
        byte[] img = this.imageService.compress(file.getBytes());
        image.setName(uid + "_" + file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setDecompress(file.getBytes().length);
        image.setCompress(img.length);
        image.setImg(img);
        Business business = this.businessService.findById(id).get();
        image.setBusiness(business);

        this.imageService.save(image);

        //byte[] media = file.getBytes();

       return new ResponseEntity<>(image, headers, HttpStatus.OK);

    
    }

    @RequestMapping(value = "/business/{business_id}" , method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImages(@PathVariable("business_id") Integer business_id) throws IOException, DataFormatException {
        HttpHeaders headers = new HttpHeaders();

        this.imageService.deleteAllByBusiness(business_id);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    
    }
    
}
