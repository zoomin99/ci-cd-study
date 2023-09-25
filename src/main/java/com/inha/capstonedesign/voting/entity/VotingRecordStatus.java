package com.inha.capstonedesign.voting.entity;

public enum VotingRecordStatus {

    IN_PROGRESS("투표 진행중"),
    COMPLETED("투표 완료"),
    CANCELLED_ERROR("오류로 인한 투표 취소");

    private String korean;

    public String getKorean() {
        return korean;
    }

    VotingRecordStatus(String korean) {
        this.korean = korean;
    }
}
