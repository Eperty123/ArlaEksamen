package BE;

public class CSVRowData {
    String xValue;
    String yValue;

    public CSVRowData(String xValue, String yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public String getyValue() {
        return yValue;
    }

    public void setyValue(String yValue) {
        this.yValue = yValue;
    }
}
