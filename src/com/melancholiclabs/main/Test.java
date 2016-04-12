package com.melancholiclabs.main;

import com.melancoholic.manager.Manager;
import com.melancoholic.substance.Animal;

/**
 * Test
 * 
 * @author Melancoholic
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Manager manager = new Manager();
        manager.loadAnimalIndexDetailed();
        Animal[] animal = manager.getAnimalIndex();
        int bot = 0;
        for (Animal chemical : animal) {
            if (chemical.getBotanicalClassification() != null) {
                if (chemical.getBotanicalClassification().length() > bot) {
                    bot = chemical.getBotanicalClassification().length();
                }
            }
        }
        int com = 0;
        for (Animal chemical : animal) {
            if (chemical.getCommonNames() != null) {
                if (chemical.getCommonNames().length() > com) {
                    com = chemical.getCommonNames().length();
                }
            }
        }
        int eff = 0;
        for (Animal chemical : animal) {
            if (chemical.getEffectsClassification() != null) {
                if (chemical.getEffectsClassification().length() > eff) {
                    eff = chemical.getEffectsClassification().length();
                }
            }
        }
        int des = 0;
        for (Animal chemical : animal) {
            if (chemical.getDescription() != null) {
                if (chemical.getDescription().length() > des) {
                    des = chemical.getDescription().length();
                }
            }
        }
        int iurl = 0;
        for (Animal chemical : animal) {
            if (chemical.getImageURL() != null) {
                if (chemical.getImageURL().length() > iurl) {
                    iurl = chemical.getImageURL().length();
                }
            }
        }
        int burl = 0;
        for (Animal chemical : animal) {
            if (chemical.getBasicsURL() != null) {
                if (chemical.getBasicsURL().length() > burl) {
                    burl = chemical.getBasicsURL().length();
                }
            }
        }
        System.out.println("Longest botanicalClass length: " + bot);
        System.out.println("Longest commonNames length: " + com);
        System.out.println("Longest effectsClass length: " + eff);
        System.out.println("Longest description length: " + des);
        System.out.println("Longest imageURL length: " + iurl);
        System.out.println("Longest basicsURL length: " + burl);
        float run = System.currentTimeMillis() - start;
        System.out.println("Completed in " + run / 1000 + " seconds!");
    }
}
