package budget.utility;

import budget.core.Calculation;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for file operations.
 */
public final class FileUtility {
    /**
     * Load status.
     */
    private static boolean load;

    /**
     * Current directory path.
     */
    private static final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * File path for serialization.
     */
    private static final String FILE_PATH = CURRENT_DIR
            + "/../utility/src/main/resources/budget/utility/savedBudget.json";

    /**
     * Private constructor to hide the implicit public one.
     */
    private FileUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Writes the Calculation object to a file.
     *
     * @param calcMap The map of calculation objects to save
     * @throws IOException If an input or output exception occurred
     */
    public static void writeToFile(final Map<String, Calculation> calcMap) throws IOException {
        File file = new File(FILE_PATH);
        Map<String, Calculation> existingDataMap;

        // Read the existing data from the file, if it exists
        if (file.exists()) {
            existingDataMap = Json.getMapper().readValue(file, Json.getMapper()
                    .getTypeFactory().constructMapType(HashMap.class, String.class, Calculation.class));
        } else {
            existingDataMap = new HashMap<>();
        }

        // Merge the new data into the existing data
        existingDataMap.putAll(calcMap);

        // Write the merged data back to the file
        Json.getMapper().writerWithDefaultPrettyPrinter().writeValue(file, existingDataMap);

    }

    /**
     * Sets the load status.
     *
     * @param loadStatus The new load status
     */
    public static void setLoad(final boolean loadStatus) {
        load = loadStatus;
    }

    /**
     * Reads the Calculation object from a file.
     *
     * @param calcMap The map of calculations to update
     * @throws IOException If an input or output exception occurred
     */
    public static void readFile(final Map<String, Calculation> calcMap) throws IOException {
        File file = new File(FILE_PATH);
        Map<String, Calculation> mapFromFile =
                Json.getMapper().readValue(file, new TypeReference
                        <Map<String, Calculation>>(
                                ) {
                });
        calcMap.clear();

        for (Map.Entry<String, Calculation> entry : mapFromFile.entrySet()) {
            String name = entry.getKey();
            Calculation calcObject = entry.getValue();
            calcMap.put(name, calcObject);
        }
    }

    /**
     * Gets the load status.
     *
     * @return The load status
     */
    public static boolean getLoad() {
        return load;
    }
}
