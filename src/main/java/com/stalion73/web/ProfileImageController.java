package com.stalion73.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.Random;
import java.util.Set;

import javax.print.attribute.standard.Media;

import com.stalion73.model.image.ProfileImage;
import com.stalion73.model.Business;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;
import com.stalion73.service.BusinessService;
import com.stalion73.service.UserService;
import com.stalion73.service.ProfileImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;


@RestController()
@RequestMapping("/images/profile")
@CrossOrigin(origins = "*")
public class ProfileImageController {
   
    private ProfileImageService profileImageService;

    private UserService userService;
    
    @Autowired
    public ProfileImageController(ProfileImageService profileImageService, UserService userService){
        this.profileImageService = profileImageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{username}" , method = RequestMethod.GET)
    public ResponseEntity<?> getImageProfile(@PathVariable("username") String username) throws IOException, DataFormatException {
        HttpHeaders headers = new HttpHeaders();
        ProfileImage img = new ProfileImage();

        Optional<ProfileImage> image = this.profileImageService.findByUsername(username);
        headers.setContentType(MediaType.IMAGE_JPEG);
        img.setName(image.get().getName());
        img.setType(image.get().getType());
        img.setImg(this.profileImageService.decompress(image.get().getImg()));
        return new ResponseEntity<>(img.getImg(), headers, HttpStatus.OK);
    
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImageProfile(@RequestParam("image") MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // Testing purpose
        //headers.setContentType(MediaType.IMAGE_JPEG);
        Random randGen = new Random();
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<ProfileImage> retrImage = this.profileImageService.findByUsername(username);
        if(!retrImage.isPresent()){
            Integer uid = randGen.nextInt(1000000);
            ProfileImage image = new ProfileImage();
            byte[] img = this.profileImageService.compress(file.getBytes());
            image.setName(uid + "_" + file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setDecompress(file.getBytes().length);
            image.setCompress(img.length);
            image.setImg(img);
            User user = this.userService.findUser(username).get();
            image.setUser(user);
    
            this.profileImageService.save(image);
    
            //byte[] media = file.getBytes();
    
           return new ResponseEntity<>(image, headers, HttpStatus.OK);
        }

        ProfileImage image = retrImage.get();
        Integer uid = randGen.nextInt(1000000);
        byte[] img = this.profileImageService.compress(file.getBytes());
            image.setName(uid + "_" + file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setDecompress(file.getBytes().length);
            image.setCompress(img.length);
            image.setImg(img);
            User user = this.userService.findUser(username).get();
            image.setUser(user);
    
            this.profileImageService.save(image);
    
            //byte[] media = file.getBytes();
    
           return new ResponseEntity<>(image, headers, HttpStatus.OK);

    
    }

    
}
