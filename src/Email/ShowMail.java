package Email;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import Reminder.Reminder;

public class ShowMail {

	public static String file = "";
	private MimeMessage mimeMessage = null;
	private StringBuffer bodyText = new StringBuffer(); 
	private String dateFormat = "yy-MM-dd HH:mm"; // the data format

	/**
	 * init methord
	 */
	public ShowMail() {
	}

	public ShowMail(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	/**
	 *　the mail method for the email option 
	 */
	public static void setup() throws Exception {
		String host = "mail.autotask.com";
		
		//Here is the setting of email
 		String username = "";
		String password = "";
		
		String email_Content = "";

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		props.put("mail.imaps.ssl.trust", "*");
		Session session = Session.getInstance(props);

		Store store = session.getStore("imaps");
		store.connect(host, username, password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message message[] = folder.getMessages();
		Reminder.txaDisplay.append(" Email Amount:　" + message.length + "\n");
		ShowMail re = null;

		for (int i = message.length; i > 0 ; i--) {			
				re = new ShowMail((MimeMessage) message[i-1]);
				if(!re.isNew())
				{
					if(re.getFrom().contains(""))
					{
						if(re.getSubject().contains(""))
						{
							Reminder.txaDisplay.append(" Subject:　" + re.getSubject() + "\n");
							Reminder.txaDisplay.append("　Send time:　" + re.getSentDate() + "\n");
							Reminder.txaDisplay.append("　Readed?:　" + re.isNew() + "\n");
							Reminder.txaDisplay.append("　Sender:　" + re.getFrom() + "\n");
							re.setDateFormat("yyMMdd　HH:mm");
							re.getMailContent((Part) message[i-1]);
							email_Content = delHTMLTag(re.getBodyText()).replace("&nbsp;", "").replaceAll("(?m)^\\s*$"+System.lineSeparator(), "");
							break;
						}
					}
				}
		}
		if(email_Content.contains("https://ww2.autotask.net"))
			writeData("https://ww2.autotask.net","AUTOMATIONQAPR.COM");
		if(email_Content.contains("https://qaprod.autotask.com"))
			writeData("https://qaprod.autotask.com","AUTOMATIONQAPROD.COM");
		 Runtime rt = Runtime.getRuntime();
		 try{  
			 Reminder.txaDisplay.append(" Writing data file...\n");
			 General.sleep(3);
		     rt.exec("cmd.exe /c start " + file); 
		    } catch (IOException e) { 
		      e.printStackTrace(); 
		    } 
		
	}
	
	public static void writeData(String url,String db){	
		General.setXML("Url", url+"/");
		General.setXML("SuperUser", "administrator@" + db);
	}
	
	//remove the HTML codes
	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
        String regEx_html="<[^>]+>"; 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll("");  
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); 

        return htmlStr.trim(); 
    }
	
	/**
	 * 　*　get form　
	 */
	public String getFrom() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = address[0].getPersonal();

		if (personal == null) {
			personal = "";
		}

		String fromAddr = null;
		if (personal != null || from != null) {
			fromAddr = personal + "<" + from + ">";
		} 
		return fromAddr;
	}


	/**
	 * 　*　get subject
	 */
	public String getSubject() throws MessagingException {
		String subject = "";
		try {
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null) {
				subject = "";
			}
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		return subject;
	}

	/**
	 * 　*　get the send date　
	 */
	public String getSentDate() throws Exception {
		Date sentDate = mimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String strSentDate = format.format(sentDate);
		return strSentDate;
	}

	/**
	 * 　*　get the email content 
	 */
	public String getBodyText() {
		return bodyText.toString();
	}

	/**
	 * 　　*　parse the email content by the following options　
	 */

	public void getMailContent(Part part) throws Exception {

		String contentType = part.getContentType();

		int nameIndex = contentType.indexOf("name");

		boolean conName = false;

		if (nameIndex != -1) {
			conName = true;
		}

		if (part.isMimeType("text/plain") && conName == false) {

		} else if (part.isMimeType("text/html") && conName == false) {
			bodyText.append((String) part.getContent());
		} 
		else if (part.isMimeType("multipart/*")) {
			// multipart/*
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				//System.out.println(i);
				getMailContent(multipart.getBodyPart(i));
			}
		} 
		else if (part.isMimeType("message/rfc822")) {
			// message/rfc822
			getMailContent((Part) part.getContent());
		} else {

		}
	}

	/**
	 *　get the Message-ID
	 */
	public String getMessageId() throws MessagingException {
		String messageID = mimeMessage.getMessageID();
		return messageID;
	}

	/**
	 * judge the email is new or not
	 */
	public boolean isNew() throws MessagingException {
		boolean isNew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isNew = true;
			}
		}
		return isNew;
	}

	/**
	 * 　*　the data format
	 */
	public void setDateFormat(String format) throws Exception {
		this.dateFormat = format;
	} 
}