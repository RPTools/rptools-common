/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package net.rptools.lib.util;


import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class used for creating "random" numbers.
 */
public class RandomUtil {

    /** The singleton instance. */
    private static final RandomUtil INSTANCE = new RandomUtil();

    /** Random object used to generate random values. */
    // There is no need to seed this as after 1.4.2 seeding is guaranteed to be secure
    // (and probably better than we could manage ourselves).
    private Random random = new SecureRandom();



    private RandomUtil() {
        // Stop instantiation.
    }


    /**
     * Returns an instance of RandomUtil.
     *
     * @return an instance of RandomUtil.
     */
    public static RandomUtil getInstance() {
        return INSTANCE;
    }

    /**
     * Returns a random number between 1 and {@code sides} as if a die of that many sides
     * had been rolled.
     *
     * @param sides The number of sides for the die.
     *
     * @return the random number.
     */
    public int rollDie(int sides) {
        return random.nextInt(sides) + 1;
    }


    /**
     * Returns an array of random numbers between 1 and {@code sides} as if dice of that many sides
     * had been rolled.
     *
     * @param number The number dice to roll.
     *
     * @param sides The number of sides for the dice.
     *
     * @return the array of random numbers.
     */
    public int[] rollDice(int number, int sides) {
        int[] vals = new int[number];
        for (int i = 0; i < number; i++) {
            vals[i] = rollDie(sides);
        }
        return vals;
    }

    /**
     * Returns a random integer between 0 inclusive and {@code max} exclusive.
     *
     * @param lessThan The number the random number should be less than.
     *
     * @return the random integer.
     */
    public int randomInt(int lessThan) {
        return random.nextInt(lessThan);
    }

    /**
     * Returns an array of random integers between 0 inclusive and {@code lessThan} exclusive.
     *
     * @param number The number of random integers to return.
     * @param lessThan The number the random number should be less than.
     * @return
     */
    public int[] randomInts(int number, int lessThan) {
        int vals[] = new int[number];
        for (int i = 0; i < number; i++) {
            vals[i] = randomInt(lessThan);
        }
        return vals;
    }

    /**
     * Returns a random integer between {@code min} inclusive and {@code lessThan} exclusive.
     *
     * @param min The minimum number for the random number.
     * @param lessThan The number the random number should be less than.
     *
     * @return the random integer.
     */
    public int randomInt(int min, int lessThan) {
        return min + random.nextInt(lessThan - min);
    }

    /**
     * Returns an array of random integers between {@code min} inclusive and {@code lessThan} exclusive.
     *
     * @param number The number or random integers to produce.
     * @param min The minimum number for the random number.
     * @param lessThan The number the random number should beless than.
     *
     * @return an array of random integers.
     */
    public int[] randomInts(int number, int min, int lessThan) {
        int vals[] = new int[number];
        for (int i = 0; i < number; i++) {
            vals[i] = randomInt(min, lessThan);
        }

        return vals;
    }



}
