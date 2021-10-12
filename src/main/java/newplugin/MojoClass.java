package newplugin;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;


@Mojo(name = "version", defaultPhase = LifecyclePhase.INITIALIZE)
public class MojoClass extends AbstractMojo {
    @Parameter(property = "query.url", required = true)
    private String srcDir;
    static Object valObj;
    static String fruit;
    static List<String> list1 = new ArrayList<String>();
    static List<String> list2 = new ArrayList<String>();
    static List<String> list3 = new ArrayList<String>();
    static List<String> list4 = new ArrayList<String>();
    static HashMap<String, Integer> map = new HashMap<String, Integer>();
    static int count = 0;
    static String strOne = "";
    static Object strKey = "";
    static Object strValue = "";
    static String f = "";
    static String strScenarios = "";
    static String strStepsPassed = "";
    static String strStepsFailed = "";
    static int frequency;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Json, Reading Plugin");
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(srcDir)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;
            getLog().info(employeeList.toJSONString());
            for (Object json : employeeList) {
                printJSONOBject((JSONObject) json);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        countOne();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Sample.pdf"));
            document.open();
            document.add(new Paragraph(String.valueOf(strScenarios)));
            document.add(new Paragraph(String.valueOf(strStepsPassed)));
            document.add(new Paragraph(String.valueOf(strStepsFailed)));
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printJSONOBject(JSONObject jsonObject) throws ParseException {
        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;
            Object valObj = jsonObject.get(key);
            if (valObj instanceof JSONObject) {
                printJSONOBject((JSONObject) valObj);

            } else {
                String str = key + " : " + valObj;
                if (key.equalsIgnoreCase("elements")) {
                    {
                        JSONArray jsonReportOne = (JSONArray) valObj;
                        for (Object json : jsonReportOne) {
                            printJSONOBjectOne((JSONObject) json);
                        }

                    }
                }
            }
        }
    }

    private static void printJSONOBjectOne(JSONObject jsonObject) throws ParseException {
        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;
            Object valObj = jsonObject.get(key)
                    ;
            if (valObj instanceof JSONObject) {
                printJSONOBjectOne((JSONObject) valObj);
            } else {
                String str = key + " : " + valObj;
                for (int i = 1; i <= 1; i++) {
                    list1.add(str);
                }
                if (key.equalsIgnoreCase("steps")) {
                    JSONArray jsonReportTwo = (JSONArray) valObj;
                    for (Object json : jsonReportTwo) {
                        printJSONOBjectTwo((JSONObject) json);
                    }
                }
            }
        }
    }

    private static void printJSONOBjectTwo(JSONObject jsonObject) throws ParseException {
        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;
            Object valObj = jsonObject.get(key)
                    ;
            if (valObj instanceof JSONObject) {
                printJSONOBjectTwo((JSONObject) valObj);
            } else {
                String str = key + " : " + valObj;
                List<String> list = new ArrayList<String>();
                for (int i = 1; i <= 1; i++) {
                    list2.add(str);
                }
            }
        }
    }

    private void countOne() {
        Set<String> distinct = new HashSet<>(list2);
        for (String s : distinct) {
            frequency = Collections.frequency(list2, s);
            strOne = s + ":" + frequency;
            map.put(s, frequency);
        }

        for (Map.Entry<String, Integer> m : map.entrySet()) {
            strKey = m.getKey();
            strValue = m.getValue();
//            System.out.println(strKey);
//            System.out.println(strValue);
            if (strKey.equals("keyword : Given ")) {
                strScenarios = "Total no of scenarios >>>>>>>>>> " +strValue;
             //   System.out.println("Total no of scenarios >>>>>>>>>> " +strValue);
                getLog().info("Total no of scenarios >>>>>>>>>> " +strValue);
            }
            if (strKey.equals("status : passed")){
                strStepsPassed = "Total no. of steps passed >>>>>> " +strValue;
           //     System.out.println("Total no. of steps passed >>>>>> " +strValue);
                getLog().info("Total no. of steps passed >>>>>> " +strValue);
            }
            if (strKey.equals("status : failed")){
                strStepsFailed = "Total no. of steps failed >>>>>> " +strValue;
               // System.out.println("Total no. of steps failed >>>>>> " +strValue);
                getLog().info("Total no. of steps failed >>>>>> " +strValue);
            }
        }
    }
}


