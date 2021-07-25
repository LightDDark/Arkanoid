package com.oryehezkel.gamelogic;
/**
 * simple class that is used for counting things.
 */
public class Counter {
    private int count = 0;
    private static final int DEFAULT_AMOUNT = 1;

    /**
     * add number to current count.
     * @param number to add.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * add DEFAULT_AMOUNT to current count.
     */
    public void increase() {
        increase(DEFAULT_AMOUNT);
    }

    /**
     * subtract number from current count.
     * @param number to subtract.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * subtract DEFAULT_AMOUNT to current count.
     */
    public void decrease() {
        decrease(DEFAULT_AMOUNT);
    }

    /**
     * get current count.
     * @return this count.
     */
    public int getValue() {
        return this.count;
    }

    /**
     * check to see if the counter is zero.
     * @return true if it's zero and otherwise false.
     */
    public boolean isZero() {
        if (count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + count;
    }
}