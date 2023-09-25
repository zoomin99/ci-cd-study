package com.inha.capstonedesign.image.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ballot_image")
public class BallotImage extends Image {

    BallotImage(Image image) {
        super(image);
    }
}
