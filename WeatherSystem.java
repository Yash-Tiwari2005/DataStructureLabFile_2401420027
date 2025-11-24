public class WeatherSystem {

    // ADT: WeatherRecord
    static class WeatherRecord {
        String date;
        String city;
        double temperature;

        WeatherRecord(String date, String city, double temperature) {
            this.date = date;
            this.city = city;
            this.temperature = temperature;
        }

        public String toString() {
            return date + " | " + city + " | " + temperature + "°C";
        }
    }

    // Data Storage Class
    static class WeatherDataStorage {
        String[] cities;
        int[] years;
        double[][] temperatureData;
        double SENTINEL = -9999; // value for missing data

        WeatherDataStorage(String[] cities, int[] years) {
            this.cities = cities;
            this.years = years;
            temperatureData = new double[years.length][cities.length];

            // initialize with sentinel
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < cities.length; j++) {
                    temperatureData[i][j] = SENTINEL;
                }
            }
        }

        // Insert data
        void insert(int year, String city, double temp) {
            int row = getYearIndex(year);
            int col = getCityIndex(city);
            if (row != -1 && col != -1) {
                temperatureData[row][col] = temp;
            }
        }

        // Delete data
        void delete(int year, String city) {
            int row = getYearIndex(year);
            int col = getCityIndex(city);
            if (row != -1 && col != -1) {
                temperatureData[row][col] = SENTINEL;
            }
        }

        // Retrieve data
        Double retrieve(int year, String city) {
            int row = getYearIndex(year);
            int col = getCityIndex(city);
            if (row != -1 && col != -1 && temperatureData[row][col] != SENTINEL) {
                return temperatureData[row][col];
            }
            return null; // no data
        }

        // Row-Major Access
        void rowMajorAccess() {
            System.out.println("Row-Major Access:");
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < cities.length; j++) {
                    System.out.print(temperatureData[i][j] + " ");
                }
                System.out.println();
            }
        }

        // Column-Major Access
        void columnMajorAccess() {
            System.out.println("Column-Major Access:");
            for (int j = 0; j < cities.length; j++) {
                for (int i = 0; i < years.length; i++) {
                    System.out.print(temperatureData[i][j] + " ");
                }
                System.out.println();
            }
        }

        // Show only non-missing values
        void handleSparseData() {
            System.out.println("Sparse Data (non-missing values):");
            for (int i = 0; i < years.length; i++) {
                for (int j = 0; j < cities.length; j++) {
                    if (temperatureData[i][j] != SENTINEL) {
                        System.out.println("Year " + years[i] + " | City: " + cities[j] + " | Temp: " + temperatureData[i][j]);
                    }
                }
            }
        }

        // Complexity Information
        void analyzeComplexity() {
            System.out.println("Insert: O(1), Delete: O(1), Retrieve: O(1)");
            System.out.println("Row/Column Traversal: O(N*M)");
            System.out.println("Space Complexity: O(N*M)");
        }

        // Helper methods
        int getYearIndex(int year) {
            for (int i = 0; i < years.length; i++) {
                if (years[i] == year) return i;
            }
            return -1;
        }

        int getCityIndex(String city) {
            for (int j = 0; j < cities.length; j++) {
                if (cities[j].equals(city)) return j;
            }
            return -1;
        }
    }

    // Main method to test
    public static void main(String[] args) {
        String[] cities = {"Delhi", "Mumbai", "Kolkata"};
        int[] years = {2023, 2024, 2025};

        WeatherDataStorage storage = new WeatherDataStorage(cities, years);

        // Insert some records
        storage.insert(2023, "Delhi", 32.5);
        storage.insert(2024, "Mumbai", 29.8);
        storage.insert(2025, "Kolkata", 30.1);

        // Retrieve a record
        System.out.println("Retrieve Delhi 2023: " + storage.retrieve(2023, "Delhi"));

        // Delete a record
        storage.delete(2024, "Mumbai");

        // Show different accesses
        storage.rowMajorAccess();
        storage.columnMajorAccess();
        storage.handleSparseData();
        storage.analyzeComplexity();
    }
}
