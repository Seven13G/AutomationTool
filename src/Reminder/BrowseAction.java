package Reminder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class BrowseAction implements ActionListener {
	
	public static String xmlURL = "";
	public static String bat_path = "";
	
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(Reminder.open)) {
    	// TODO Auto-generated method stub  
        JFileChooser jfc=new JFileChooser();  
        jfc.setDialogTitle("select the Setup.bat file");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
        int returnval = jfc.showDialog(new JLabel(), "select");  
        if(returnval==JFileChooser.APPROVE_OPTION)
        {
            String bat_path=jfc.getSelectedFile().getPath();
            Reminder.jta.setText(bat_path);
        }
        
    } else if (e.getSource().equals(Reminder.buttonBrowseTarget)) {
      JFileChooser fcDlg = new JFileChooser();
      fcDlg.setDialogTitle("sele the data file");
      fcDlg.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = fcDlg.showOpenDialog(null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String filepath = fcDlg.getSelectedFile().getPath();
        Reminder.jta1.setText(filepath);
       // Reminder.targetfile.setText(filepath);
      }
    }
	    else if (e.getSource().equals(Reminder.buttonStart)) {
	        
	    	if(Reminder.jta.getText().trim().equals(""))
		    	 JOptionPane.showMessageDialog(null, "select the Setup.bat");  
		  	else
		     {
		    	 if(Reminder.jta1.getText().trim().equals(""))
			    	 JOptionPane.showMessageDialog(null, "select the data.xml"); 
				     else
				     {
				    	xmlURL = Reminder.jta1.getText();
						Reminder.txaDisplay.append(" data.xml path : " + xmlURL + "\n");
						bat_path = Reminder.jta.getText();
					  	Reminder.txaDisplay.append(" setup.bat path : " + bat_path + "\n");
					  	Reminder.start();
				     }
		    	
		     }
       
	    }
		 else if (e.getSource().equals(Reminder.buttonStop)) 
		 {
			        
			 	Reminder.timer.cancel();
			 	Reminder.txaDisplay.setText("");
			 	Reminder.txaDisplay.append(" Stop the program!\n");
		}
  }
}