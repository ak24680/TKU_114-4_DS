interface ReportExporter {
    String export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        StringBuilder sb = new StringBuilder("CSV: ").append(title).append("\n");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]).append(i == values.length - 1 ? "" : ",");
            }
        }
        return sb.toString();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        StringBuilder sb = new StringBuilder("JSON: {\"title\":\"").append(title).append("\", \"values\":[");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]).append(i == values.length - 1 ? "" : ", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        StringBuilder sb = new StringBuilder("TEXT: ").append(title).append(" -> ");
        if (values != null && values.length > 0) {
            for (int val : values) {
                sb.append("[").append(val).append("] ");
            }
        } else {
            sb.append("[No Data]");
        }
        return sb.toString();
    }
}

public class ReportExporterFactory {
    public static ReportExporter createExporter(String format) {
        if ("csv".equalsIgnoreCase(format)) {
            return new CsvExporter();
        } else if ("json".equalsIgnoreCase(format)) {
            return new JsonExporter();
        }
        return new TextExporter();
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        System.out.println(exporter.export(title, values));
    }

    public static void main(String[] args) {
        int[] data = {10, 20, 30, 40};

        ReportExporter csv = createExporter("csv");
        ReportExporter json = createExporter("json");
        ReportExporter unknown = createExporter("xml");

        exportReport(csv, "月度銷售數據", data);
        exportReport(json, "年度統計數據", data);
        exportReport(unknown, "邊界測試數據", null);
    }
}