package com.inha.capstonedesign.member.entity;

public enum Region {

    SEOUL("서울"),
    GYEONGGI("경기도"),
    GANGWON("강원도"),
    CHUNGCHEONGBUK("충청북도"),
    CHUNGCHEONGNAM("충청남도"),
    JEOLLABUK("전라북도"),
    JEOLLANAM("전라남도"),
    GYEONGSANGBUK("경상북도"),
    GYEONGSANGNAM("경상남도");

    private String korean;

    public String getKorean() {
        return korean;
    }

    Region(String korean) {
        this.korean = korean;
    }
}
