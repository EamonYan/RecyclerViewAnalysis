/**
  * Copyright 2019 bejson.com 
  */
package cn.npe1348.testrecyclerview.http.bean;

import androidx.annotation.NonNull;

/**
 * Auto-generated: 2019-07-18 14:35:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private String text;
    private String name;
    private String profile_image;
    private String created_at;
    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }


    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }


    public void setProfile_image(String profile_image) {
         this.profile_image = profile_image;
     }
     public String getProfile_image() {
         return profile_image;
     }

    public void setCreated_at(String created_at) {
         this.created_at = created_at;
     }
     public String getCreated_at() {
         return created_at;
     }

    @Override
    public String toString() {
        return "Data{" +
                "text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}