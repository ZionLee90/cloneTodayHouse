package com.zionjp.springtodayhouse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class Deck {
    private List<Card> cardList;
    private List<Card> usedList = new ArrayList<>();

    public List<Card> getHands(int maxHands) {
        List<Card> newList = new ArrayList<>(cardList.subList(0, maxHands));
        usedList.addAll(newList);
        newList.sort(Comparator.comparingInt(Card::getRank));
        return newList;
    }

    public int getLeftSize() {
        return cardList.size();
    }

    public int getTotalSize() {
        return cardList.size() + usedList.size();
    }
}
