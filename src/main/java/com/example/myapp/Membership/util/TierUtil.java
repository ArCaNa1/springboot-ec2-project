package com.example.myapp.Membership.util;

public class TierUtil {

    private static final String[] TIER_NAMES = {
            "UNRANKED", // 0
            "BRONZE V", "BRONZE IV", "BRONZE III", "BRONZE II", "BRONZE I",         // 1~5
            "SILVER V", "SILVER IV", "SILVER III", "SILVER II", "SILVER I",         // 6~10
            "GOLD V", "GOLD IV", "GOLD III", "GOLD II", "GOLD I",                   // 11~15
            "PLATINUM V", "PLATINUM IV", "PLATINUM III", "PLATINUM II", "PLATINUM I", // 16~20
            "DIAMOND V", "DIAMOND IV", "DIAMOND III", "DIAMOND II", "DIAMOND I",    // 21~25
            "RUBY V", "RUBY IV", "RUBY III", "RUBY II", "RUBY I",                    // 26~30
            "MASTER" // 31
    };

    public static String convertTier(int tier) {
        if (tier >= 0 && tier < TIER_NAMES.length) {
            return TIER_NAMES[tier];
        }
        return "UNKNOWN";
    }
}