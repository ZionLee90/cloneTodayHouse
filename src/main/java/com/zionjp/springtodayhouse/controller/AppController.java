package com.zionjp.springtodayhouse.controller;

import com.zionjp.springtodayhouse.domain.Card;
import com.zionjp.springtodayhouse.domain.Deck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
public class AppController {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/")
    public String getRoot() {
        logger.info(name);
        boolean isStraight = false;
        while(!isStraight) {
            Deck deck = new Deck();
            deck.setCardList(newDeck());
            List<Card> hands = deck.getHands(5);
            isStraight = handInfo(hands) < 3;
        }

        return "Hello Spring Next";
    }

    public List<Card> newDeck() {
        List<Card> list = new ArrayList<>();
        IntStream.range(1,5).mapToObj(shape ->IntStream.range(1,14).mapToObj(rank->list.add(new Card(shape,rank))).collect(Collectors.toList())).collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

    public int handInfo(List<Card> list) {
        for(Card card: list) {
            System.out.println(card.toString());
        }
        //리스트내에 연속적인 숫자가 있는가
        boolean isStraight = isConsecutive(list);
        Map<Integer, List<Card>> suitMap = list.stream().sorted(Comparator.comparing(Card::getRank)).collect(Collectors.groupingBy(Card::getShape));
        Map<Integer, List<Card>> rankMap = list.stream().sorted(Comparator.comparing(Card::getRank)).collect(Collectors.groupingBy(Card::getRank));
        boolean isFlush = suitMap.size() == 1;
        //리스트내 수트가 같은가
        //boolean isFlush = isFlushed(list);
        //리스트내에 같은 숫자가 겹치는가
        //boolean isFair = isFaired(list);
        boolean isFair = rankMap.size() < list.size();
        boolean isTriple = false;
        boolean isFourCards = false;
        boolean isTwoPair = false;
        boolean isOnePair = false;
        boolean isFullHouse = false;
        if(isFair) {
            int pairMax = 0;
            Integer max = rankMap.entrySet().stream().mapToInt(value -> value.getValue().size()).max().orElse(0);
            switch (rankMap.size()) {
                case 2:
                    //풀하우스,포카드
                    if(max==4) isFourCards = true;
                    else isFullHouse = true;
                    break;
                case 3:
                    if(max==3) isTriple = true;
                    else isTwoPair = true;
                    //투페어,트리플
                    break;
                case 4:
                    isOnePair = true;
                    //원페어
                    break;
            }
        }

        if(isStraight && isFlush) {
            System.out.println("is StraightFlush");
            return 2;
        }

        if(isFourCards) {
            System.out.println("is FourCards");
            return 3;
        }

        if(isFullHouse) {
            System.out.println("is FullHouse");
            return 4;
        }

        if(isFlush) {
            System.out.println("is Flush");
            return 5;
        }

        if(isStraight) {
            System.out.println("is Straight");
            return 6;
        }

        if(isTriple) {
            System.out.println("is Triple");
            return 7;
        }

        if(isTwoPair) {
            System.out.println("is Two Pair");
            return 8;
        }

        if(isOnePair) {
            System.out.println("is One Fair");
            return 9;
        }
        System.out.println("is High Card");
        //중첩횟수, 최대중첩수
        return 10;
    }

    public boolean isConsecutive(List<Card> list) {
        boolean isConsecutive = true;
        List<Card> array = list.stream().sorted((o1, o2) -> o1.getRank() > o2.getRank() ? 1 : o1.getRank() == o2.getRank() ? 0 : -1).collect(Collectors.toList());
        List<Integer> rankList = array.stream().map(card->card.getRank()).sorted().collect(Collectors.toList());
        for(int index = 0; index+1 < rankList.size(); index++) {
            if(rankList.get(index)+1 != rankList.get(index+1)) {
                isConsecutive = false;
                break;
            }
        }
        return isConsecutive;
    }

    public boolean isFlushed(List<Card> list) {
        return list.stream().map(card-> card.getShape()).collect(Collectors.toSet()).size() == 1 ? true : false;
    }

    public boolean isFaired(List<Card> list) {
        return list.stream().map(card -> card.getRank()).collect(Collectors.toSet()).size() == list.size();
    }

}
