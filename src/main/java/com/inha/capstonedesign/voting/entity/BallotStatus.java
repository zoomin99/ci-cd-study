package com.inha.capstonedesign.voting.entity;

public enum BallotStatus {

    NOT_STARTED("시작전"),
    IN_PROGRESS("진행중"),
    CLOSED("마감");

    private String korean;

    public String getKorean() {
        return korean;
    }

    BallotStatus(String korean) {
        this.korean = korean;
    }
}
