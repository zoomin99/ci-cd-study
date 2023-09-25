package com.inha.capstonedesign.voting.entity.analysis.age;

public enum AgeGroup {
    TEENS_OR_LESS("10대 이하"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES("60대"),
    SEVENTIES_OR_ABOVE("70대 이상");

    private String korean;

    public String getKorean() {
        return korean;
    }

    AgeGroup(String korean) {
        this.korean = korean;
    }
}
