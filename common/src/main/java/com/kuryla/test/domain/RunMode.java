package com.kuryla.test.domain;

public enum RunMode {
    ONE, TWO, THREE, FOUR, FIVE, SIX;

    public static RunMode getRunMode(int i) {
        int num = i % 10;
        RunMode mode;
        switch (i) {
            case 0:
                mode = RunMode.ONE;
                break;
            case 1:
                mode = RunMode.TWO;
                break;
            case 2:
                mode = RunMode.THREE;
                break;
            case 3:
                mode = RunMode.FOUR;
                break;
            case 4:
                mode = RunMode.FIVE;
                break;
            case 5:
                mode = RunMode.SIX;
                break;
            case 6:
                mode = RunMode.ONE;
                break;
            case 7:
                mode = RunMode.TWO;
                break;
            case 8:
                mode = RunMode.THREE;
                break;
            case 9:
                mode = RunMode.FOUR;
                break;
            default:
                mode = RunMode.ONE;
        }
        return mode;
    }
}
