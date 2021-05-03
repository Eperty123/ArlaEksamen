import DAL.Parser.XLSXParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class XLSXParserTest {

    @DisplayName("Parsing test")
    @org.junit.jupiter.api.Test
    public void parseTest() {
        var xlsxParser = new XLSXParser();
        xlsxParser.loadFile("src/Resources/Excel_mockData.xlsx");
        var data = xlsxParser.getParsedData().toString();
        System.out.println(data);

        Assertions.assertNotNull(data);
    }

    @DisplayName("Specific column and value test")
    @org.junit.jupiter.api.Test
    public void getSpecificColumnValueTest() {
        var xlsxParser = new XLSXParser();
        xlsxParser.loadFile("src/Resources/Excel_mockData.xlsx");
        var data = xlsxParser.getRow(1);
        System.out.println(data);

        Assertions.assertNotNull(data);
    }
}