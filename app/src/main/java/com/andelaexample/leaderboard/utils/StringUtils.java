package com.andelaexample.leaderboard.utils;

public class StringUtils {

    public static String getLearnerHoursAndCountryText(int hours, String country) {

        return hours + " learning hours, " + country;
    }

    public static String getSkillsIQAndCountryText(int skillIq, String country) {

        return skillIq + " skill IQ Score, " + country;
    }
}
