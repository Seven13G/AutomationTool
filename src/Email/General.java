package Email;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class General {

    public static String xmlURL = "C:\\SeleniumWebDriver_PR\\data.xml";
    private static String pictureURL = "..\\Attachment\\ImageAttachment.jpg";
    private static String wordUrl = "..\\Attachment\\WordAttachment.docx";
    private static String pdfUrl = "..\\Attachment\\PDF.pdf";

    public General() {

    }

    public static String getAbsolutePath(String type) {
        File xmlFile = null;
        switch (type) {
        case "Picture":
            xmlFile = new File(pictureURL);
            break;
        case "Word":
            xmlFile = new File(wordUrl);
            break;
        case "PDF":
            xmlFile = new File(pdfUrl);
            break;
        }
        return xmlFile.getAbsolutePath();
    }

    // get xml node value based on node name
    public static String getXML(String nodeName) {
        String value = "";
        File xmlFile = new File(xmlURL);
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            NodeList nList = doc.getChildNodes();
            Node nNode = nList.item(0);
            // System.out.println(nNode.getTextContent());
            Element eElement = (Element) nNode;
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                value = eElement.getElementsByTagName(nodeName).item(0).getTextContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    // write to xml based on its node name
    public static void setXML(String nodeName, String newValue) {
        File xmlFile = new File(xmlURL);
        System.out.println(xmlURL);
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            NodeList nList = doc.getChildNodes();
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                eElement.getElementsByTagName(nodeName).item(0).setTextContent(newValue);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // return a random int number (among the range of min and max number set in
    // the parameter)
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    // return the current day of week. sunday = 1; Saturday = 7
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    // sleep x seconds
    public static void sleep(double d) {
        try {
            Thread.sleep((long) (d * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // grabs the current date in MM/dd/yyyy format
    public static String getCurrentDate() {
        return getFormatDate(getXML("DateFormat"));
    }

    public static String getFormatDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static String getToday() {
//    	DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
//        Calendar cal = Calendar.getInstance();
//        String today = null;
//        try {
//            // cal.setTime(dateFormat.parse(getCurrentDate()))
//            cal.setTime(new Date());
//            cal.add(Calendar.DAY_OF_MONTH, 0);  
//            today = dateFormat.format(cal.getTime());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return today;
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // get the next date forward by x in MM/dd/yyyy format
    public static String getNextDate(int noOfDays) {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String nextDate = null;
        try {
            // cal.setTime(dateFormat.parse(getCurrentDate()))
            cal.setTime(new Date());
            cal.add(Calendar.DATE, noOfDays);
            nextDate = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nextDate;
    }

	public static CharSequence getNextBusinessDay() {
        DateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(getDayOfWeek()==Calendar.FRIDAY){

		        Calendar cal = Calendar.getInstance();
		        String nextDate = null;
		        try {
		            // cal.setTime(dateFormat.parse(getCurrentDate()))
		            cal.setTime(new Date());
		            cal.add(Calendar.DATE, 3);
		            nextDate = apiFormat.format(cal.getTime());
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
				return nextDate;
		} else{
				Calendar cal = Calendar.getInstance();
		        String nextDate = null;
		            // cal.setTime(dateFormat.parse(getCurrentDate()))
		            cal.setTime(new Date());
		            cal.add(Calendar.DATE, 1);
		            nextDate = apiFormat.format(cal.getTime());
		        return nextDate;
		}
	}
    public static String getFirstDateOfCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String firstDay = null;
        try {
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            firstDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return firstDay;
    }
    
    public static String getFirstDateOfNextMonth() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String firstDay = null;
        try {
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            firstDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return firstDay;
    }

    public static String getLastDateOfCurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String lasttDay = null;
        try {
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            lasttDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lasttDay;
    }

    public static String getLastDateOfCurrentYear() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String lasttDay = null;
        try {
            cal.add(Calendar.YEAR, -1);
            cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
            lasttDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lasttDay;
    }
    
    public static String getFirstDateOfCurrentYear() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String firstDay = null;
        try {
            cal.add(Calendar.YEAR, 0);
            cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
            firstDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return firstDay;
    }

    public static String getLastDateOfNextMonth() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();
        String lasttDay = null;
        try {
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            lasttDay = dateFormat.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lasttDay;
    }

    public static double getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        double days = cal.getActualMaximum(Calendar.DATE);
        return days;
    }

    // grabs the saturday in the week
    public static String getEndOfWeek() {
        DateFormat dateFormat = new SimpleDateFormat(getXML("DateFormat"));
        Calendar cal = Calendar.getInstance();

        int differnce = -cal.get(Calendar.DAY_OF_WEEK) + 7;
        cal.add(Calendar.DAY_OF_MONTH, differnce);
        return dateFormat.format(cal.getTime());
    }

    public static String tempFile(String fileName) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(fileName, ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(tempFile);
            writer.write("Some data");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getPath();
    }

    // timestamp - can be used for suffix
    public static String timeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String timeStampWithSpecialCharacters() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        return dateFormat.format(date) + getXML("SpecialChar");
    }

    // timestamp - can be used for suffix(Because of the timeStamp is long for
    // some fields(e.g. favorite > Speed Code), so add this method )
    public static String secondStamp() {
        DateFormat dateFormat = new SimpleDateFormat("ddHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

	public static CharSequence timeStampShort() {
        DateFormat dateFormat = new SimpleDateFormat("MMddYY");
        Date date = new Date();
        return dateFormat.format(date);
	}
}