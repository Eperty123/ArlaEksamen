import DAL.Parser.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class CSVParserTest {

    @DisplayName("Parsing test")
    @org.junit.jupiter.api.Test
    public void parseTest() {
        var csvReader = new CSVParser();
        csvReader.loadFile("src/Resources/BarChart_mockData.csv");
        var data = csvReader.getParsedData().toString();
        System.out.println(data);

        Assertions.assertNotNull(data);
    }

    @DisplayName("Specific column and value test")
    @org.junit.jupiter.api.Test
    public void getSpecificColumnValueTest() {
        var csvReader = new CSVParser();
        csvReader.loadFile("src/Resources/BarChart_mockData.csv");
        var data = csvReader.getParsedData().getColumnData(0,"Time", "10");
        System.out.println(data);

        Assertions.assertNotNull(data);
    }

    @DisplayName("Save test")
    @org.junit.jupiter.api.Test
    public void saveTest() {
        var csvReader = new CSVParser();
        csvReader.loadFile("src/Resources/BarChart_mockData.csv");

        csvReader.saveFile("src/Resources/test.csv");
    }
}
