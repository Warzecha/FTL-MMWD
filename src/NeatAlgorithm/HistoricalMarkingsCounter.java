package NeatAlgorithm;

public class HistoricalMarkingsCounter {

    private static int currentMaxInnovationNumber = 0;
    private static int currentMaxNodeId = 0;

    public static int getNextInnovationNumber() {
        return currentMaxInnovationNumber++;
    }
    public static void resetInnovationNumber() {currentMaxInnovationNumber = 0; }
    public static int getNextNodeId() {
        return currentMaxNodeId++;
    }

    public static void resetNodeId() {currentMaxNodeId = 0; }

    public static void setCurrentMaxNodeId(int id) {currentMaxNodeId = id; }

    public static int peekCurrentMaxNodeId() {
        return currentMaxNodeId;
    }

}
