package com.stalion73.web;

import java.util.Optional;
import java.util.zip.DataFormatException;

import javax.print.attribute.standard.Media;

import com.stalion73.model.image.Image;
import com.stalion73.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;


@RestController()
@RequestMapping("/images")
public class ImageController {
   
    private ImageService imageService;
    
    @Autowired
    public ImageController(ImageService imageService){
        this.imageService = imageService;
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
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImg(this.imageService.compress(file.getBytes()));

        this.imageService.save(image);

        //byte[] media = file.getBytes();

       return new ResponseEntity<>(image, headers, HttpStatus.OK);

    
    }
    
}
