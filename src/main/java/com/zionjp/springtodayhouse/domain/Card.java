package com.zionjp.springtodayhouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Card {
    Integer shape;
    Integer rank;

    public String getShapeStr() {
        switch (this.shape) {
            case 1:
                return "Spade";
            case 2:
                return "Clover";
            case 3:
                return "Heart";
            case 4:
                return "Diamond";
            default:
                return "";
        }
    }

    public String getRankStr() {
        switch (this.rank) {
            case 1 :
                return "ACE";
            case 11 :
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return Integer.toString(this.rank);
        }
    }
    public String toString() {
        return "Shape : " + getShapeStr() + "\t Rank : " + getRankStr();
    }
}
