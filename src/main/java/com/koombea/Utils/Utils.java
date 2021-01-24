package com.koombea.Utils;

import java.util.Arrays;
import java.util.Random;

public class Utils {

    private static final Random rnd = new Random();

    public static int[] GetArrayWithRandomNumbersNoRepeat(int size, int bound, boolean zeroIndex, boolean ordered)
    {

        int[] arrayInt = new int[size];
        for(int index = 0 ; index < size ; ){
            int newNumber = NextInt(bound);
            if(!zeroIndex) newNumber++;
            if(index == 0 || !ArrayContainsNumber(Arrays.copyOf(arrayInt, index), newNumber)){
                arrayInt[index] = newNumber;
                index++;
            }
        }
        if(ordered) Arrays.sort(arrayInt);
        return arrayInt;
    }

    public static boolean ArrayContainsNumber(int[] array, int value){
        Arrays.sort(array);
        int result = Arrays.binarySearch(array, value);
        return result >= 0;
    }

    public static int NextInt(int bound){
        return rnd.nextInt(bound);
    }
}
