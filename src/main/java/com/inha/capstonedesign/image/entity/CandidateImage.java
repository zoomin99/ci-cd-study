package com.inha.capstonedesign.image.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "candidate_image")
public class CandidateImage extends Image {

    public CandidateImage(Image image) {
        super(image);
    }
}
