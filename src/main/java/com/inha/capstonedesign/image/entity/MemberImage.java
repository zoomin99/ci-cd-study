package com.inha.capstonedesign.image.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_image")
public class MemberImage extends Image {

    public MemberImage(Image image) {
        super(image);
    }

    private MemberImage(String imageName, String imageOriginalName, String imagePath, String imageFormat, Long imageBytes) {
        super(imageName, imageOriginalName, imagePath, imageFormat, imageBytes);
    }

    public static MemberImage createDefaultMemberImage() {
        return new MemberImage("default-image.png", "default-image.png",
                "https://final-project-travel.s3.ap-northeast-2.amazonaws.com/default-image.png",
                "png", 18700L);
    }
}
