package com.tsystems.javaschool.tasks.subsequence;

import java.util.*;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // input lists should not be null:
        if (null == x || null == y) {
            throw new IllegalArgumentException("Sequences should not be null");
        }

        // if sequence X is empty then it's always possible:
        if (x.size() == 0) {
            return true;
        }

        // if sequence X is longer than Y, then it's impossible:
        if (x.size() > y.size()) {
            return false;
        }


        ListIterator iteratorX = x.listIterator();
        Object nextFromX = iteratorX.next();

        for (Object element : y) {
            if (element.equals(nextFromX) && iteratorX.hasNext()) {
                nextFromX = iteratorX.next();
            }
        }
        return !iteratorX.hasNext();
    }

}
