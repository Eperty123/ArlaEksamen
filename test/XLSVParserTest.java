import DAL.Parser.XLSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class XLSVParserTest {

    @DisplayName("Parsing test")
    @org.junit.jupiter.api.Test
    public void parseTest() {
        var xlsvParser = new XLSVParser();
        xlsvParser.loadFile("src/Resources/Excel_mockData.xlsx");
        var data = xlsvParser.getParsedData().toString();
        System.out.println(data);

        Assertions.assertNotNull(data);
    }

    @DisplayName("Specific column and value test")
    @org.junit.jupiter.api.Test
    public void getSpecificColumnValueTest() {
        var xlsvParser = new XLSVParser();
        xlsvParser.loadFile("src/Resources/Excel_mockData.xlsx");
        var data = xlsvParser.getParsedData().getColumnData("Time", "10");
        System.out.println(data);

        Assertions.assertNotNull(data);
    }
}