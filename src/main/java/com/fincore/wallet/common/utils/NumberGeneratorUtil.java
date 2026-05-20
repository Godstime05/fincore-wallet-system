package com.fincore.wallet.common.utils;

public class NumberGeneratorUtil {

    private NumberGeneratorUtil(){

    }

    public static String generateCustomerId (long count){
        return String.format("CUST%04d", count);
    }

    public static String generateWalletId(long count) {

        return String.format(
                "WALT%04d",
                count
        );
    }

    public static String generateCustomerNumber(long count){
        long base = 2000000000L;
        //          2000000000L
        return String.valueOf(base + count);
    }

    public static String generateWalletNumber(long count){
        long base = 4000000000L;
        //          4000000000L
        return String.valueOf(base + count);
    }

}
