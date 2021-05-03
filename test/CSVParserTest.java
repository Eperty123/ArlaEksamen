import DAL.Parser.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

public class CSVParserTest {

    @DisplayName("Parsing test")
    @org.junit.jupiter.api.Test
    public void parseTest() {
        var csvReader = new CSVParser();
        csvReader.loadFile("src/Resources/BarChart_mockData.csv");
        var data = csvReader.getParsedData();

        for (int i = 0; i < data.size(); i++) {
            var line = data.get(i);
            System.out.println(String.format("%s: %s", line[0], line[1]));
        }
        Assertions.assertNotNull(data);
    }

    @DisplayName("Specific column and value test")
    @org.junit.jupiter.api.Test
    public void getSpecificColumnValueTest() {
        var csvReader = new CSVParser();
        csvReader.loadFile("src/Resources/BarChart_mockData.csv");
        var data = csvReader.getRow(0)[0];
        System.out.println(data);

        Assertions.assertNotNull(data);
    }
}
