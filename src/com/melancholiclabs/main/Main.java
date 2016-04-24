package com.melancholiclabs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.melancoholic.ext_pages.Basics;
import com.melancoholic.ext_pages.Chemistry;
import com.melancoholic.ext_pages.Dose;
import com.melancoholic.ext_pages.Effects;
import com.melancoholic.ext_pages.Health;
import com.melancoholic.ext_pages.Images;
import com.melancoholic.ext_pages.Law;
import com.melancoholic.ext_pages.ResearchChemical;
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

    /**
     * Standard main method.
     * 
     * @param args
     *            no command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Checks if the latest hashcode matches erowid.org
        System.out.println("Determining if records match erowid.org...");
        if (!checkUpdate()) {
            System.exit(0);
        }
        // Load all indexes with full information
        System.out.println("Loading all indexes with full information...");
        Manager.loadAllIndexFull();

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
            // Load the Substance tables
            loadChemTable();
            loadPlantTable();
            loadHerbTable();
            loadPharmTable();
            loadSmartTable();
            loadAnimalTable();
            // Load the ExternalPage tables
            loadBasicsTable();
            loadEffectsTable();
            loadImagesTable();
            loadHealthTable();
            loadLawTable();
            loadDoseTable();
            loadChemistryTable();
            loadResearchChemicalTable();
            // Prints out a completion statement
            float run = System.currentTimeMillis() - start;
            System.out.println("Completed in " + run / 1000 + " seconds!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the hashcodes of the Indexes with basic detail against the erowid site.
     * 
     * @return true if the hashcode are different
     */
    private static boolean checkUpdate() {
        // TODO Implement hashcodes and check them against site scrapes
        return false;
    }

    /**
     * Creates a table for the chemIndex and loads the values into it.
     */
    private static void loadChemTable() {
        try {
            // Create chemIndex
            System.out.println("Creating chemIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS chemIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "commonNames VARCHAR(150),"
                    + "effectsClassification VARCHAR(100)," + "chemicalName VARCHAR(350),"
                    + "description VARCHAR(1000)," + "imageURL VARCHAR(150),"
                    + "basicsURL VARCHAR(150)," + "effectsURL VARCHAR(150),"
                    + "imagesURL VARCHAR(150)," + "healthURL VARCHAR(150)," + "lawURL VARCHAR(150),"
                    + "doseURL VARCHAR(150)," + "chemistryURL VARCHAR(150),"
                    + "researchChemicalsURL VARCHAR(150))");

            // Fill the chemIndex with values
            System.out.println("Loading the chemIndex table...");
            for (Chemical chem : Manager.chemIndex) {
                myStatement.executeUpdate("INSERT INTO chemIndex VALUES ('" + chem.getId() + "', '"
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
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "botanicalClassification VARCHAR(200),"
                    + "commonNames VARCHAR(150)," + "effectsClassification VARCHAR(50),"
                    + "description VARCHAR(550)," + "imageURL VARCHAR(100),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the plantIndex with values
            System.out.println("Loading the plantIndex table...");
            for (Plant plant : Manager.plantIndex) {
                myStatement.executeUpdate("INSERT INTO plantIndex VALUES ('" + plant.getId()
                        + "', '" + doubleQ(plant.getName()) + "', '" + doubleQ(plant.getUrl())
                        + "', '" + doubleQ(plant.getCategory()) + "', '"
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
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "botanicalClassification VARCHAR(200),"
                    + "commonNames VARCHAR(275)," + "uses VARCHAR(500),"
                    + "description VARCHAR(600)," + "imageURL VARCHAR(150),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the herbIndex with values
            System.out.println("Loading the herbIndex table...");
            for (Herb herb : Manager.herbIndex) {
                myStatement.executeUpdate("INSERT INTO herbIndex VALUES ('" + herb.getId() + "', '"
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
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "commonNames VARCHAR(150),"
                    + "effectsClassification VARCHAR(100)," + "chemicalName VARCHAR(150),"
                    + "description VARCHAR(500)," + "imageURL VARCHAR(150),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the pharmIndex with values
            System.out.println("Loading the pharmIndex table...");
            for (Pharm pharm : Manager.pharmIndex) {
                myStatement.executeUpdate("INSERT INTO pharmIndex VALUES ('" + pharm.getId()
                        + "', '" + doubleQ(pharm.getName()) + "', '" + doubleQ(pharm.getUrl())
                        + "', '" + doubleQ(pharm.getCategory()) + "', '"
                        + doubleQ(pharm.getCommonNames()) + "', '"
                        + doubleQ(pharm.getEffectsClassification()) + "', '"
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
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "commonNames VARCHAR(50),"
                    + "effectsClassification VARCHAR(75)," + "chemicalName VARCHAR(75),"
                    + "description VARCHAR(750)," + "imageURL VARCHAR(141),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the smartIndex with values
            System.out.println("Loading the smartIndex table...");
            for (Smart smart : Manager.smartIndex) {
                myStatement.executeUpdate("INSERT INTO smartIndex VALUES ('" + smart.getId()
                        + "', '" + doubleQ(smart.getName()) + "', '" + doubleQ(smart.getUrl())
                        + "', '" + doubleQ(smart.getCategory()) + "', '"
                        + doubleQ(smart.getCommonNames()) + "', '"
                        + doubleQ(smart.getEffectsClassification()) + "', '"
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
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "category VARCHAR(20)," + "botanicalClassification VARCHAR(150),"
                    + "commonNames VARCHAR(50)," + "effectsClassification VARCHAR(50),"
                    + "description VARCHAR(550)," + "imageURL VARCHAR(150),"
                    + "basicsURL VARCHAR(100)," + "effectsURL VARCHAR(100),"
                    + "imagesURL VARCHAR(100)," + "healthURL VARCHAR(100)," + "lawURL VARCHAR(100),"
                    + "doseURL VARCHAR(100)," + "chemistryURL VARCHAR(100))");

            // Fill the animalIndex with values
            System.out.println("Loading the animalIndex table...");
            for (Animal animal : Manager.animalIndex) {
                myStatement.executeUpdate("INSERT INTO animalIndex VALUES ('" + animal.getId()
                        + "', '" + doubleQ(animal.getName()) + "', '" + doubleQ(animal.getUrl())
                        + "', '" + doubleQ(animal.getCategory()) + "', '"
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
     * Creates a table for all of the Basic pages.
     */
    private static void loadBasicsTable() {
        try {
            // Create basicsIndex
            System.out.println("Creating basicsIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS basicsIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "description VARCHAR(2100)," + "descriptionSections VARCHAR(4250),"
                    + "effects VARCHAR(1500)," + "effectsSections VARCHAR(2400),"
                    + "problems VARCHAR(2400)," + "problemsSections VARCHAR(3050),"
                    + "cautionDisclaimer VARCHAR(325))");

            // Fill the basicsIndex with values
            System.out.println("Loading the basicsIndex table...");
            for (Basics page : Manager.basicsIndex) {
                myStatement.executeUpdate("INSERT INTO basicsIndex VALUES ('" + page.getId()
                        + "', '" + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl())
                        + "', '" + doubleQ(page.getDescription()) + "', '"
                        + doubleQ(page.getDescriptionSections()) + "', '"
                        + doubleQ(page.getEffects()) + "', '" + doubleQ(page.getEffectsSections())
                        + "', '" + doubleQ(page.getProblems()) + "', '"
                        + doubleQ(page.getProblemsSections()) + "', '"
                        + doubleQ(page.getCautionDisclaimer()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Effects pages.
     */
    private static void loadEffectsTable() {
        try {
            // Create effectsIndex
            System.out.println("Creating effectsIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS effectsIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "duration VARCHAR(275)," + "positiveEffects VARCHAR(1325),"
                    + "neutralEffects VARCHAR(725)," + "negativeEffects VARCHAR(1650),"
                    + "description VARCHAR(9200)," + "experienceReports VARCHAR(6425),"
                    + "cautionDisclaimer VARCHAR(400))");

            // Fill the effectsIndex with values
            System.out.println("Loading the effectsIndex table...");
            for (Effects page : Manager.effectsIndex) {
                myStatement.executeUpdate("INSERT INTO effectsIndex VALUES ('" + page.getId()
                        + "', '" + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl())
                        + "', '" + doubleQ(page.getDuration()) + "', '"
                        + doubleQ(page.getPositiveEffects()) + "', '"
                        + doubleQ(page.getNeutralEffects()) + "', '"
                        + doubleQ(page.getNegativeEffects()) + "', '"
                        + doubleQ(page.getDescription()) + "', '"
                        + doubleQ(page.getExperienceReports()) + "', '"
                        + doubleQ(page.getCautionDisclaimer()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Images pages.
     */
    private static void loadImagesTable() {
        try {
            // Create imagesIndex
            System.out.println("Creating imagesIndes table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS imagesIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "imageEntryList VARCHAR(20000))");

            // Fill the imagesIndex with values
            System.out.println("Loading the imagesIndex table...");
            for (Images page : Manager.imagesIndex) {
                myStatement.executeUpdate("INSERT INTO imagesIndex VALUES ('" + page.getId()
                        + "', '" + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl())
                        + "', '" + doubleQ(page.getImageEntryList()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Health pages.
     */
    private static void loadHealthTable() {
        try {
            // Create healthIndex
            System.out.println("Creating healthIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS healthIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "notes VARCHAR(2900)," + "deaths VARCHAR(900)," + "warnings VARCHAR(4150),"
                    + "cautions VARCHAR(3650)," + "benefits VARCHAR(1625))");

            // Fill the healthIndex with values
            System.out.println("Loading the healthIndex table...");
            for (Health page : Manager.healthIndex) {
                myStatement.executeUpdate("INSERT INTO healthIndex VALUES ('" + page.getId()
                        + "', '" + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl())
                        + "', '" + doubleQ(page.getNotes()) + "', '" + doubleQ(page.getDeaths())
                        + "', '" + doubleQ(page.getWarnings()) + "', '"
                        + doubleQ(page.getCautions()) + "', '" + doubleQ(page.getBenefits())
                        + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Law pages.
     */
    private static void loadLawTable() {
        try {
            // Create lawIndex
            System.out.println("Creating lawIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS lawIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "legalTable VARCHAR(175)," + "federalLawText VARCHAR(3350),"
                    + "stateLaw TEXT," + "internationalLaw TEXT,"
                    + "cautionDisclaimer VARCHAR(450))");

            // Fill the lawIndex with values
            System.out.println("Loading the lawIndex table...");
            for (Law page : Manager.lawIndex) {
                myStatement.executeUpdate("INSERT INTO lawIndex VALUES ('" + page.getId() + "', '"
                        + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl()) + "', '"
                        + doubleQ(page.getLegalTable()) + "', '" + doubleQ(page.getFederalLawText())
                        + "', '" + doubleQ(page.getStateLaw()) + "', '"
                        + doubleQ(page.getInternationalLaw()) + "', '"
                        + doubleQ(page.getCautionDisclaimer()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Dose pages.
     */
    private static void loadDoseTable() {
        try {
            // Create doseIndex
            System.out.println("Creating doseIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS doseIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "doseTable VARCHAR(750)," + "doseText VARCHAR(4775)," + "notes VARCHAR(2275),"
                    + "cautionDisclaimer VARCHAR(425))");

            // Fill the doseIndex with values
            System.out.println("Loading the doseIndex table...");
            for (Dose page : Manager.doseIndex) {
                myStatement.executeUpdate("INSERT INTO doseIndex VALUES ('" + page.getId() + "', '"
                        + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl()) + "', '"
                        + doubleQ(page.getDoseTable()) + "', '" + doubleQ(page.getDoseText())
                        + "', '" + doubleQ(page.getNotes()) + "', '"
                        + doubleQ(page.getCautionDisclaimer()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the Chemistry pages.
     */
    private static void loadChemistryTable() {
        try {
            // Create chemistryIndex
            System.out.println("Creating chemistryIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS chemistryIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "chemTable VARCHAR(950)," + "moleculeURL VARCHAR(100))");

            // Fill the chemistryIndex with values
            System.out.println("Loading the chemistryIndex table...");
            for (Chemistry page : Manager.chemistryIndex) {
                myStatement.executeUpdate("INSERT INTO chemistryIndex VALUES ('" + page.getId()
                        + "', '" + doubleQ(page.getName()) + "', '" + doubleQ(page.getUrl())
                        + "', '" + doubleQ(page.getChemTable()) + "', '"
                        + doubleQ(page.getMoleculeURL()) + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for all of the ResearchChemical pages.
     */
    private static void loadResearchChemicalTable() {
        try {
            // Create researchChemicalIndex
            System.out.println("Creating researchChemicalIndex table...");
            myStatement.executeUpdate("CREATE TABLE IF NOT EXISTS researchChemicalIndex ("
                    + "id SMALLINT UNSIGNED," + "name VARCHAR(50), " + "url VARCHAR(125), "
                    + "summaryText VARCHAR(1575)," + "imageURL VARCHAR(75))");

            // Fill the researchChemicalIndex with values
            System.out.println("Loading the researchChemicalIndex table...");
            for (ResearchChemical page : Manager.researchChemicalIndex) {
                myStatement.executeUpdate("INSERT INTO researchChemicalIndex VALUES ('"
                        + page.getId() + "', '" + doubleQ(page.getName()) + "', '"
                        + doubleQ(page.getUrl()) + "', '" + doubleQ(page.getSummaryText()) + "', '"
                        + doubleQ(page.getImageUrl()) + "')");
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
