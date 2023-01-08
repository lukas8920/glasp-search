package org.kehrbusch.procedure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class LevenshteinImpl {
    private static final Logger logger = Logger.getLogger(LevenshteinImpl.class.getName());

    private String x;
    private int currentXPosition = 0;
    private int currentYPosition = 0;
    private int string1Length;
    private boolean isLastChar = false;

    private final String y;
    private final int string2Length;
    private final List<List<Integer>> memories;

    public LevenshteinImpl(String searchString){
        this.x = "";
        this.y = searchString;
        this.string1Length = 0;
        this.string2Length = y.length();
        this.memories = new ArrayList<>();
    }

    public LevenshteinImpl(LevenshteinImpl levenshtein){
        this.x = levenshtein.x;
        this.y = levenshtein.y;
        this.string1Length = levenshtein.string1Length;
        this.string2Length = levenshtein.string2Length;
        this.currentXPosition = levenshtein.currentXPosition;
        this.currentYPosition = levenshtein.currentYPosition;
        this.memories = this.deepCopy(levenshtein.memories);
    }

    public void addChar(String c, boolean isLastChar){
        this.x = x + c;
        this.string1Length += 1;
        this.isLastChar = isLastChar;
    }

    public Result calculate(Function<Integer, Boolean> cancelCondition){
        int jUpperLimit = (string1Length > string2Length || isLastChar) ? string2Length : string1Length;
        //maybe replace with string1length again
        for(int i = 0; i <= string1Length; i++){
            int jStartValue = i <= currentXPosition ? currentYPosition : 0;
            int testValue = 0;

            for(int j = jStartValue; j <= jUpperLimit; j++){
                if (i == 0){
                    memories.add(new ArrayList<>());
                    memories.get(0).add(j);
                }
                else if (j == 0){
                    memories.add(new ArrayList<>());
                    memories.get(i).add(i);
                } else {
                    memories.get(i).add(min(
                        memories.get(i - 1).get(j - 1) + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                        memories.get(i-1).get(j) + 1,
                        memories.get(i).get(j - 1) + 1
                    ));
                    //System.out.println((memories.get(i).size() - 1));
                    //System.out.println(memories.get(i).get(memories.get(i).size() - 1));
                }

                testValue = memories.get(i).get(j);
                //System.out.println("(" + i + "/" + j + ")" + " " + testValue);
            }

            if (i == string1Length && cancelCondition.apply(testValue)){
                return new Result(x, null, true, true);
            }
        }
        currentXPosition += 1;
        currentYPosition = jUpperLimit + 1;

        boolean hasReachedMaxValidLength = (jUpperLimit >= string2Length || isLastChar);
        int distance = memories.get(string1Length).get(jUpperLimit);
        return new Result(x, distance, false, hasReachedMaxValidLength);
    }

    private int costOfSubstitution(char a, char b){
        return a == b ? 0 : 1;
    }

    int min(int... numbers){
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    List<List<Integer>> deepCopy(List<List<Integer>> rootList){
        List<List<Integer>> list = new ArrayList<>();
        for (List<Integer> integers : rootList) {
            list.add(new ArrayList<>(integers));
        }
        return list;
    }

    public class Result {
        private final String finalString;
        private final Integer distance;
        private final boolean isCancelled;
        private final boolean hasReachedMaxValidLength;
        private boolean hasBeenAddedToResults = false;

        Result(String finalString, Integer distance, boolean isCancelled, boolean hasReachedMaxValidLength) {
            this.finalString = finalString;
            this.distance = distance;
            this.isCancelled = isCancelled;
            this.hasReachedMaxValidLength = hasReachedMaxValidLength;
        }

        public void setHasBeenAddedToResults(){
            this.hasBeenAddedToResults = true;
        }

        public boolean hasBeenAddedToResults(){
            return this.hasBeenAddedToResults;
        }

        public boolean hasReachedValidatableLength(){return this.hasReachedMaxValidLength;}

        public Integer getDistance(){
            return this.distance;
        }

        public boolean isCancelled(){
            return this.isCancelled;
        }

        public String getFinalString() {
            return finalString;
        }
    }
}
