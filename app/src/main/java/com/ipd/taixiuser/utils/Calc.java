package com.ipd.taixiuser.utils;

public class Calc {
    public static void main(String[] args) {
//        ((10000*0.97)+1000) / 1100 = 9.09;
        double currentPrice = 10.00;
        double UP = 10000;
        double DOWN = 100000;
        double UP_HAND = UP / currentPrice;
        double DOWN_HAND = DOWN / currentPrice;

        boolean isUP = UP > DOWN;
        double endPrice = (isUP ? UP : DOWN * 0.90 + (isUP ? DOWN : UP)) / (UP_HAND + DOWN_HAND);
        System.out.print("endPrice:" + endPrice);

        double upTotal = UP_HAND * endPrice;
        double downEarnings = (currentPrice - endPrice) * DOWN_HAND;
        double downTotal = DOWN_HAND * currentPrice + downEarnings;
        System.out.print("up:" + upTotal);
        System.out.print("down:" + downTotal);
        System.out.print("total:" + (upTotal+downTotal));

    }
}
