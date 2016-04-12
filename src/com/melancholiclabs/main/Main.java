package com.melancholiclabs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.melancoholic.manager.Manager;
import com.melancoholic.substance.Animal;
import com.melancoholic.substance.Chemical;
import com.melancoholic.substance.Herb;
import com.melancoholic.substance.Pharm;
import com.melancoholic.substance.Plant;
import com.melancoholic.substance.Smart;

/**
 * This class uses the ErowidJavaAPI library to scrape data from the erowid site and then create a
 * mySQL database containing the data.
 * 
 * @author Melancoholic
 */
public class Main {

    /** The URL to the database. */
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/?autoReconnect=true&useSSL=false";
    /** The user name of the connection. */
    private static final String USER = "";
    /** The password matched with the user. */
    private static final String PASS = "";

    /** The main connection to the database. */
    private static Connection myConnection;
    /** The Statement to serve as a global variable. */
    private static Statement myStatement;

    /** The substance array of chemicals. */
    private static Chemical[] chemIndex;
    /** The substance array of plants. */
    private static Plant[] plantIndex;
    /** The substance array of herbs. */
    private static Herb[] herbIndex;
    /** The substance array of pharmaceuticals. */
    private static Pharm[] pharmIndex;
    /** The substance array of smarts. */
    private static Smart[] smartIndex;
    /** The substance array of animals. */
    private static Animal[] animalIndex;

    /**
     * Standard main method.
     * 
     * @param args
     *            no command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // Initialize the manager
        Manager manager = new Manager();
        // Load all indexes with detailed information
        System.out.println("Loading all indexes with detailed information...");
        // manager.loadAllIndexDetailed();
        manager.loadAllIndexDetailed();
        // Set the indexes to their respective arrays
        System.out.println("Assigning indexes to arrays...");
        chemIndex = manager.getChemIndex();
        plantIndex = manager.getPlantIndex();
        herbIndex = manager.getHerbIndex();
        pharmIndex = manager.getPharmIndex();
        smartIndex = manager.getSmartIndex();
        animalIndex = manager.getAnimalIndex();

        try {
            // Get a connection to the database
            System.out.println("Connecting to database...");
            myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            // Create a statement
            myStatement = myConnection.createStatement();
            // Drops database if it exists
            System.out.println("Dropping existing databases...");
            myStatement.executeUpdate("DROP DATABASE IF EXISTS erowid");
            // Create a database
            System.out.println("Creating erowid db if it doesn't exist...");
            myStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS erowid");
            // Use the newly created database
            myStatement.executeUpdate("USE erowid");
            // Load the chemIndex table
            loadChemTable();
            loadPlantTable();
            loadHerbTable();
            loadPharmTable();
            loadSmartTable();
            loadAnimalTable();
            // Prints out a completion statement
            float run = System.currentTimeMillis() - start;
            System.out.println("Completed in " + run / 1000 + " seconds!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the chemIndex and loads the values into it.
     */
    private static void loadChemTable() {
        try {
            // Create chemIndex
            System.out.println("Creating chemIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS chemIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "commonNames VARCHAR(150)," + "effectsClassification VARCHAR(100),"
                    + "chemicalName VARCHAR(350)," + "description VARCHAR(1000),"
                    + "imageURL VARCHAR(150)," + "basicsURL VARCHAR(150),"
                    + "effectsURL VARCHAR(150)," + "imagesURL VARCHAR(150),"
                    + "healthURL VARCHAR(150)," + "lawURL VARCHAR(150)," + "doseURL VARCHAR(150),"
                    + "chemistryURL VARCHAR(150)," + "researchChemicalsURL VARCHAR(150))");

            // Fill the chemIndex with values
            System.out.println("Loading the chemIndex table...");
            for (Chemical chem : chemIndex) {
                myStatement.executeUpdate("INSERT INTO chemIndex VALUES ('"
                        + doubleQ(chem.getName()) + "', '" + doubleQ(chem.getUrl()) + "', '"
                        + doubleQ(chem.getCategory()) + "', '" + doubleQ(chem.getCommonNames())
                        + "', '" + doubleQ(chem.getEffectsClassification()) + "', '"
                        + doubleQ(chem.getChemicalName()) + "', '" + doubleQ(chem.getDescription())
                        + "', '" + doubleQ(chem.getImageURL()) + "', '"
                        + doubleQ(chem.getBasicsURL()) + "', '" + doubleQ(chem.getEffectsURL())
                        + "', '" + doubleQ(chem.getImagesURL()) + "', '"
                        + doubleQ(chem.getHealthURL()) + "', '" + doubleQ(chem.getLawURL()) + "', '"
                        + doubleQ(chem.getDoseURL()) + "', '" + doubleQ(chem.getChemistryURL())
                        + "', '" + doubleQ(chem.getResearchChemicalsURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the plantIndex and loads the values into it.
     */
    private static void loadPlantTable() {
        try {
            // Create plantIndex
            System.out.println("Creating plantIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS plantIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "botanicalClassification VARCHAR(200)," + "commonNames VARCHAR(150),"
                    + "effectsClassification VARCHAR(50)," + "description VARCHAR(550),"
                    + "imageURL VARCHAR(100)," + "basicsURL VARCHAR(100),"
                    + "effectsURL VARCHAR(100)," + "imagesURL VARCHAR(100),"
                    + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100)," + "doseURL VARCHAR(100),"
                    + "chemistryURL VARCHAR(100))");

            // Fill the plantIndex with values
            System.out.println("Loading the plantIndex table...");
            for (Plant plant : plantIndex) {
                myStatement.executeUpdate("INSERT INTO plantIndex VALUES ('"
                        + doubleQ(plant.getName()) + "', '" + doubleQ(plant.getUrl()) + "', '"
                        + doubleQ(plant.getCategory()) + "', '"
                        + doubleQ(plant.getBotanicalClassification()) + "', '"
                        + doubleQ(plant.getCommonNames()) + "', '"
                        + doubleQ(plant.getEffectsClassification()) + "', '"
                        + doubleQ(plant.getDescription()) + "', '" + doubleQ(plant.getImageURL())
                        + "', '" + doubleQ(plant.getBasicsURL()) + "', '"
                        + doubleQ(plant.getEffectsURL()) + "', '" + doubleQ(plant.getImagesURL())
                        + "', '" + doubleQ(plant.getHealthURL()) + "', '"
                        + doubleQ(plant.getLawURL()) + "', '" + doubleQ(plant.getDoseURL()) + "', '"
                        + doubleQ(plant.getChemistryURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the herbIndex and loads the values into it.
     */
    private static void loadHerbTable() {
        try {
            // Create herbIndex
            System.out.println("Creating herbIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS herbIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "botanicalClassification VARCHAR(200)," + "commonNames VARCHAR(275),"
                    + "uses VARCHAR(500)," + "description VARCHAR(600)," + "imageURL VARCHAR(150),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the herbIndex with values
            System.out.println("Loading the herbIndex table...");
            for (Herb herb : herbIndex) {
                myStatement.executeUpdate("INSERT INTO herbIndex VALUES ('"
                        + doubleQ(herb.getName()) + "', '" + doubleQ(herb.getUrl()) + "', '"
                        + doubleQ(herb.getCategory()) + "', '"
                        + doubleQ(herb.getBotanicalClassification()) + "', '"
                        + doubleQ(herb.getCommonNames()) + "', '" + doubleQ(herb.getUses()) + "', '"
                        + doubleQ(herb.getDescription()) + "', '" + doubleQ(herb.getImageURL())
                        + "', '" + doubleQ(herb.getBasicsURL()) + "', '"
                        + doubleQ(herb.getEffectsURL()) + "', '" + doubleQ(herb.getImagesURL())
                        + "', '" + doubleQ(herb.getHealthURL()) + "', '" + doubleQ(herb.getLawURL())
                        + "', '" + doubleQ(herb.getDoseURL()) + "', '"
                        + doubleQ(herb.getChemistryURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the pharmIndex and loads the values into it.
     */
    private static void loadPharmTable() {
        try {
            // Create pharmIndex
            System.out.println("Creating pharmIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS pharmIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "commonNames VARCHAR(150)," + "effectsClassification VARCHAR(100),"
                    + "chemicalName VARCHAR(150)," + "description VARCHAR(500),"
                    + "imageURL VARCHAR(150)," + "basicsURL VARCHAR(100),"
                    + "effectsURL VARCHAR(100)," + "imagesURL VARCHAR(100),"
                    + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100)," + "doseURL VARCHAR(100),"
                    + "chemistryURL VARCHAR(100))");

            // Fill the pharmIndex with values
            System.out.println("Loading the pharmIndex table...");
            for (Pharm pharm : pharmIndex) {
                myStatement.executeUpdate("INSERT INTO pharmIndex VALUES ('"
                        + doubleQ(pharm.getName()) + "', '" + doubleQ(pharm.getUrl()) + "', '"
                        + doubleQ(pharm.getCategory()) + "', '" + doubleQ(pharm.getCommonNames())
                        + "', '" + doubleQ(pharm.getEffectsClassification()) + "', '"
                        + doubleQ(pharm.getChemicalName()) + "', '"
                        + doubleQ(pharm.getDescription()) + "', '" + doubleQ(pharm.getImageURL())
                        + "', '" + doubleQ(pharm.getBasicsURL()) + "', '"
                        + doubleQ(pharm.getEffectsURL()) + "', '" + doubleQ(pharm.getImagesURL())
                        + "', '" + doubleQ(pharm.getHealthURL()) + "', '"
                        + doubleQ(pharm.getLawURL()) + "', '" + doubleQ(pharm.getDoseURL()) + "', '"
                        + doubleQ(pharm.getChemistryURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the smartIndex and loads the values into it.
     */
    private static void loadSmartTable() {
        try {
            // Create smartIndex
            System.out.println("Creating smartIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS smartIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "commonNames VARCHAR(50)," + "effectsClassification VARCHAR(75),"
                    + "chemicalName VARCHAR(75)," + "description VARCHAR(750),"
                    + "imageURL VARCHAR(141)," + "basicsURL VARCHAR(100),"
                    + "effectsURL VARCHAR(100)," + "imagesURL VARCHAR(100),"
                    + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100)," + "doseURL VARCHAR(100),"
                    + "chemistryURL VARCHAR(100))");

            // Fill the smartIndex with values
            System.out.println("Loading the smartIndex table...");
            for (Smart smart : smartIndex) {
                myStatement.executeUpdate("INSERT INTO smartIndex VALUES ('"
                        + doubleQ(smart.getName()) + "', '" + doubleQ(smart.getUrl()) + "', '"
                        + doubleQ(smart.getCategory()) + "', '" + doubleQ(smart.getCommonNames())
                        + "', '" + doubleQ(smart.getEffectsClassification()) + "', '"
                        + doubleQ(smart.getChemicalName()) + "', '"
                        + doubleQ(smart.getDescription()) + "', '" + doubleQ(smart.getImageURL())
                        + "', '" + doubleQ(smart.getBasicsURL()) + "', '"
                        + doubleQ(smart.getEffectsURL()) + "', '" + doubleQ(smart.getImagesURL())
                        + "', '" + doubleQ(smart.getHealthURL()) + "', '"
                        + doubleQ(smart.getLawURL()) + "', '" + doubleQ(smart.getDoseURL()) + "', '"
                        + doubleQ(smart.getChemistryURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the animalIndex and loads the values into it.
     */
    private static void loadAnimalTable() {
        try {
            // Create animalIndex
            System.out.println("Creating animalIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS animalIndex ("
                    + "name VARCHAR(50), " + "url VARCHAR(125), " + "category VARCHAR(20),"
                    + "botanicalClassification VARCHAR(150)," + "commonNames VARCHAR(50),"
                    + "effectsClassification VARCHAR(50)," + "description VARCHAR(550),"
                    + "imageURL VARCHAR(150)," + "basicsURL VARCHAR(100),"
                    + "effectsURL VARCHAR(100)," + "imagesURL VARCHAR(100),"
                    + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100)," + "doseURL VARCHAR(100),"
                    + "chemistryURL VARCHAR(100))");

            // Fill the animalIndex with values
            System.out.println("Loading the animalIndex table...");
            for (Animal animal : animalIndex) {
                myStatement.executeUpdate("INSERT INTO animalIndex VALUES ('"
                        + doubleQ(animal.getName()) + "', '" + doubleQ(animal.getUrl()) + "', '"
                        + doubleQ(animal.getCategory()) + "', '"
                        + doubleQ(animal.getBotanicalClassification()) + "', '"
                        + doubleQ(animal.getCommonNames()) + "', '"
                        + doubleQ(animal.getEffectsClassification()) + "', '"
                        + doubleQ(animal.getDescription()) + "', '" + doubleQ(animal.getImageURL())
                        + "', '" + doubleQ(animal.getBasicsURL()) + "', '"
                        + doubleQ(animal.getEffectsURL()) + "', '" + doubleQ(animal.getImagesURL())
                        + "', '" + doubleQ(animal.getHealthURL()) + "', '"
                        + doubleQ(animal.getLawURL()) + "', '" + doubleQ(animal.getDoseURL())
                        + "', '" + doubleQ(animal.getChemistryURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces all single quotes in a String with two single quotes.
     * 
     * @param s
     *            the string to manipulate
     * @return the original string with double single quotes
     */
    private static String doubleQ(String s) {
        if (s != null) {
            return s.replaceAll("'", "''");
        }
        return null;
    }
}
