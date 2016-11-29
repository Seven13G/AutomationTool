package Reminder;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;  
import java.util.TimerTask; 

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Email.General;
import Email.ShowMail;

public class Reminder extends JFrame{
	
	/**
	 * the block of GUI tools
	 */
	private static final long serialVersionUID = 1L;
	static Timer timer;  
	public static int hour = 25;
	public static int minute = 01;
	public static int second = 01;
	public static int sign = 0;
	public static int signt = 0;
	private static JComboBox<String> jcb = new JComboBox<String>();
	public static JButton open = new JButton("Select");  
	public static JTextArea jta01 = new JTextArea("Setup : ");
	public static JTextArea jta02 = new JTextArea("Data* : ");
	public static JButton buttonBrowseTarget = new JButton("Select"); 
	
	public static JButton buttonStart = new JButton("start");
	public static JButton buttonStop = new JButton("stop");
	
	public static JTextField  jta = new JTextField(25);
	public static JTextField  jta1 = new JTextField(25);
	
	public static JTextArea jta0 = new JTextArea("Set the run time : ");
	public static JTextField jtf2 = new JTextField(6);
	public static JTextArea jta3 = new JTextArea("H");
	public static JTextField jtf4 = new JTextField(6);
	public static JTextArea jta5 = new JTextArea("M");
	public static JTextField jta6 = new JTextField(6);
	public static JTextArea jta7 = new JTextArea("S");
	public static JTextArea jta8 = new JTextArea("Run in background :");
	
	public static JTextArea jta_Log = new JTextArea();
	public static JTextArea txaDisplay = new JTextArea(10,39);
	
	public static JPanel jp = new JPanel();
	
	
    public Reminder(){ 
    	
    	Calendar calendar = Calendar.getInstance();  
	    int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, second);
    	Date time = calendar.getTime();  
        timer = new Timer();  
        timer.schedule(new RemindTask(), time);  
    }  
 
    public static void main(String args[]){  
    	
    	/**
    	 * the block of GUI tools
    	 */
        JFrame frame=new JFrame("Time Sch");
        frame.setSize(450,320);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);         
        frame.setResizable(false);
        frame.setLocationRelativeTo(null) ;
        frame.setLayout(null); 
        frame.getContentPane().add(jp);
        jp.setSize(450, 320);
        jp.setVisible(true);      
        jp.setLayout(new FlowLayout(FlowLayout.LEADING));
        jp.add(jta01);    
        jta01.setEditable(false);
        jp.add(jta);
        jta.setEnabled(true);
        jta.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
        jta.setHorizontalAlignment(JTextField.LEFT);
        jp.add(open);
        open.addActionListener(new BrowseAction()); 
        jp.add(jta02);
        jta02.setEditable(false);
        jp.add(jta1);
        jta1.setEnabled(true);
        jta1.setFont(new Font("谐体",Font.BOLD|Font.ITALIC,13));
        jta1.setHorizontalAlignment(JTextField.LEFT); 
        jp.add(buttonBrowseTarget);
        buttonBrowseTarget.addActionListener(new BrowseAction());
        jp.add(jta0);
        jp.add(jtf2);
        jp.add(jta3);
        jp.add(jtf4);
        jp.add(jta5);
        jp.add(jta6);
        jp.add(jta7);
        jp.add(jta8); 
        jp.add(jcb);
        jcb.addItem("one time");
        jcb.addItem("continuously run");
        jp.add(buttonStart);
        buttonStart.addActionListener(new BrowseAction());
        jp.add(buttonStop);
        buttonStop.addActionListener(new BrowseAction());  
        txaDisplay.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(txaDisplay); 
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        jp.add(scroll);
        scroll.setSize(50,50);     
    }
    
    public static void start(){
    	/**
    	 * the block of set file content
    	 */
    	
    	int  num = 0;
    	General.xmlURL = BrowseAction.xmlURL.replaceAll("\\\\", "\\\\\\\\");
    	System.out.println("xmlURL---:" + General.xmlURL);
    	ShowMail.file = BrowseAction.bat_path.replaceAll("\\\\", "/");
    	System.out.println("filepath---:" + ShowMail.file);
    	try {   
    	    FileWriter fw = new FileWriter(ShowMail.file);   
    	    fw.write("set CLASSPATH=" + BrowseAction.xmlURL.substring(0,BrowseAction.xmlURL.length()-9) + "\\Level2\\lib\\*");  
    	    fw.write("\r\n");
    	    fw.write("java org.testng.TestNG -d "+ BrowseAction.xmlURL.substring(0,BrowseAction.xmlURL.length()-9) + "\\Level2-output\\Setup " + BrowseAction.xmlURL.substring(0,BrowseAction.xmlURL.length()-9) + "\\Setup\\testng.xml" + "\n");   
    	    fw.flush();   
    	    fw.close();
    	    num++;
    	} catch (IOException e) {   
    	    e.printStackTrace();   
    	}  
    	
    	if(jtf2.getText().trim().equals("")||jtf4.getText().trim().equals("")||jta6.getText().trim().equals(""))
    	{
    		JOptionPane.showMessageDialog(null, "time cannot be blank"); 
    	}
    	else{
    		if(Integer.parseInt(jtf2.getText().trim())>=24||Integer.parseInt(jtf4.getText().trim())>=60||Integer.parseInt(jta6.getText().trim())>=60)
    		{
    			JOptionPane.showMessageDialog(null, "time range run"); 
    		}
    		else{
    			hour = Integer.parseInt(jtf2.getText().trim());
    			minute = Integer.parseInt(jtf4.getText().trim());
    			second = Integer.parseInt(jta6.getText().trim());
    			txaDisplay.append(" Schedule time : " + hour + "H" +  minute + "M"+ second + "S" +"\n");
    			num++;
    		}
    	}
    	
    	if(num==2)
    	{
    		if(jcb.getSelectedItem().equals("one time"))	
    			sign = 0;
    		else if(jcb.getSelectedItem().equals("continuously run"))
    			sign = 1;
    		txaDisplay.append(" Setup finished! run the " + signt+1 + " task... " + "\n");
    		new Reminder();
    	}
    	else
    		JOptionPane.showMessageDialog(null, " Setup error"); 
    }
    
    
    class RemindTask extends TimerTask{  
    	/**
    	 * the block of call show mail
    	 */
    	@Override
        public void run(){  
    		txaDisplay.append(" Reading mailbox, preparing to start the task......" + "\n"); 
    		try {
				ShowMail.setup();
				
				if(sign == 0)
				{
					Reminder.txaDisplay.append(" Task finished! Stop the program\n");
					timer.cancel();
				}	
				else
				{
					signt++;
					Reminder.txaDisplay.append(" The"+signt+" task finished! Waitting for the next schedule...\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        }  
    }
    

}
