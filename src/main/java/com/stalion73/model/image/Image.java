package com.stalion73.model.image;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.stalion73.model.AuditableEntity;

@Entity
@Table(name = "images")
public class Image extends AuditableEntity{
    
    private String name;
    private String type;
    @Lob
    private byte[] img;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }

    
}

