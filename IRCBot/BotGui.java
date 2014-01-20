package IRCBot;

import IRCBot.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
/**
*Τhis is the official Api of PC_MAGAS' Control GUI of the bot
*@author pc_magas
*@Version 1.0.0
*/
public class BotGui extends JFrame implements ActionListener,Runnable
{

	/**
	*This is the main Bot tha does all the Job in the IRC Server
	*/
	protected Bot b=null;
	/*
	*We use the thread to check if the bot is connnectet to the server
	*/
	private Thread t=null;
	/*Connection Form*/
	/*
	*This is a LAbel that goes after the Server jtextfield
	*/
	private JLabel serverLabel=new JLabel("Server");
	/*
	*Same as here for password
	*/
	private JLabel chonnelLabel=new JLabel("Channel:");
	/*
	*Same hare for port
	*/
	private JLabel cportLabel=new JLabel("port");
	/*
	*Jpanel for connection form
	*/
	private JPanel form=new JPanel();//Here we put the connection Form
	/*Connection Input*/
	/*
	*In this field we put the server adress
	*/
	private JTextField server=new JTextField(15);//field for entering the server url
	/*
	*Field label for password
	*/
	private JLabel passwordLabel= new JLabel("Password");
	/*
	*HEre we enter the password
	*/
	private JPasswordField password=new JPasswordField(15);//if any password we enter here
	/*
	*Field for port
	*/
	private JTextField ccport=new JTextField("6667");//and here we enter the port
	/*
	*Button that triggers the connection and we this one we stop it
	*/
	private JButton connect=new JButton("connect");//Connection Disconnection Button
	/**
	*With thi Button we reconnect to the server
	*/
	private JButton reconnect=new JButton("reconnect");
	/*
	*Here we set the nick of the Bot
	*/
	private JTextField nick=new JTextField(8);
	/*
	*Labet that Indicates whiotch label is for nick
	*/
	private JLabel nickLabel=new JLabel("Nick");
	/*Join*/
	/*
	*Pannel we set the left join/part channel option 
	*/
	private JPanel chonnels= new JPanel();
	/*
	*Here we enter the channel we want to join
	*/
	private JTextField chonnelField=new JTextField();
	/*
	*Button for joining channel
	*/
	private JButton join=new JButton("Join");
	/*
	*Button for parting channel
	*/
	private JButton depart=new JButton("Part");
	/*
	*with this Button we say to bot to auto wave anyone who joins in channel
	*/
	private JButton removeSkaseButton=new JButton("Remove !skase mode");
	/*
	*with this Button we say to bot not to auto-wave anyone who joins in channel
	*/
	private JButton skaseButton= new JButton("!skase mode");
	/*
	*Button for changing nick
	*/
	private JButton changeNicks=new JButton("Change");
	/*
	*Scroll pane for viewing thew logs
	*/
	private JScrollPane viewlog=null;
	/*
	*Layout for side bars
	*/
	private GridLayout sideLayout=new GridLayout(3,2);
	/*
	*Thi layout is for up and bottom forms
	*/
	private FlowLayout formLayout=new FlowLayout();
	/**
	*Bar for Menus
	*/
	private JMenuBar bar=new JMenuBar();
	/**
	*Menu for File Options
	*/
	private JMenu file=new JMenu("Αρχείο");
	/**
	*Item for saving server settings
	*/
	private JMenuItem save= new JMenuItem("Αποθήκευση server & password");
	/**
	*Item for loading server-password options
	*/
	private JMenuItem loadServerOptions= new JMenuItem("Φορτωση server & password");
	/**
	*Item for saving the logs
	*/
	private JMenuItem logSave= new JMenuItem("Αποθήκευση log");	
	/*
	*This method uses to connect the bot into the server by using the info of tge user has hiven in this gui
	*/
	private void connecti()
	{
		if(!b.isConnected())
		{
				try
				{	
					b.setNick(nick.getText());	
					if(ccport.getText().equals(""))
					{
						ccport.setText("6667");
					}
					b.connect(server.getText(),Integer.parseInt(ccport.getText()));
					id(password.getText());
					connect.setText("Disconnect");
					t=new Thread();
				}catch(Exception z)
				{
					b.log("To Bot δεν μπορεί να συνδεθεί στον σέρβερ");
					connect.setText("Connect");
				}
		}
		
	}
	/*
	*This method Identifies the Bot into the server
	*/
	private void id(String pass)
	{
		if(!pass.equals("")||!pass.equals(null))
		{
			b.identify(pass);
		}
	}
	/*
	*Method for choosing file
	*@param store: select if it is for storing file or loading file
	*@oaram logOption: select if it is log file or not
	*/
	public File selectFile(boolean store,boolean logOption)
	{
		int returnVal;
		JFileChooser file=new JFileChooser();
		FileNameExtensionFilter filter1;
		if(logOption==true)
		{
			filter1=new FileNameExtensionFilter("Webpage","html");
		}
		else
		{
			filter1=new FileNameExtensionFilter("Bot option files","bot");
		}	 
		file.setFileFilter(filter1);//Seting file filters
		File f=null;
		if(store==false)
		{
			file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			returnVal = file.showOpenDialog(this);
		}
		else
		{
			returnVal = file.showSaveDialog(this);
		}
		
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			f=file.getSelectedFile();
			if(store==true && f.exists())
			{
				int option=JOptionPane.showConfirmDialog(this,"To αρχείο όπου επιλέξατε υπάρχει. Aντικατάσταση?","Αντικατάσταση αρχείου",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
				if(option==JOptionPane.NO_OPTION)
				{
					f=null;
					JOptionPane.showMessageDialog(file,"Το αρχείο όπου επιλέξατε δεν θα αντικατασταθεί");
				}
				else if(option==JOptionPane.YES_OPTION)
				{
					JOptionPane.showMessageDialog(file,"Το αρχείο όπου επιλέξατε θα αντικατασταθεί");
				}
			}
		}
		return f;
	}
	public BotGui()
	{
		/*Generating Frame*/
		super("Pc_magas bot:");
		setSize(1200,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("IRCBot/i.png"));
		setLayout(new BorderLayout());
		/*Menu*/
		save.addActionListener(this);
		loadServerOptions.addActionListener(this);
		file.add(loadServerOptions);
		file.add(save);
		file.add(logSave);
		logSave.addActionListener(this);
		bar.add(file);
		setJMenuBar(bar);
		/*Connection Form*/
		form.setLayout(formLayout);
		form.add(nickLabel);
		form.add(nick);
		changeNicks.addActionListener(this);
		form.add(changeNicks);
		form.add(serverLabel);
		form.add(server);
		form.add(passwordLabel); 
		form.add(password);
		form.add(cportLabel);
		form.add(ccport);
		connect.addActionListener(this);
		form.add(connect);
		reconnect.addActionListener(this);
		form.add(reconnect);
		add(form,BorderLayout.SOUTH);//Adding to the north of the Window
		/*chonnel pANEL*/
		chonnels.setLayout(sideLayout);
		chonnels.add(chonnelLabel);
		chonnels.add(chonnelField);
		chonnels.add(removeSkaseButton);
		chonnels.add(skaseButton);
		chonnels.add(depart);
		skaseButton.addActionListener(this);
		removeSkaseButton.addActionListener(this);
		depart.addActionListener(this);
		join.addActionListener(this);
		chonnels.add(join);
		/*Addind to Window*/
		add(chonnels,BorderLayout.WEST);
		/*add log and making the Bot*/		
		b=new Bot("sampleBot","IRCBot made with PircBot 1.5 made by pc_magas",true);		
		viewlog=new JScrollPane(b.logViewer,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(viewlog,BorderLayout.CENTER);		
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Object performed=e.getSource();//Pairnoume thn dieu8insi tou antikeimenou opou ektelestike h efamogi
		String command=e.getActionCommand();//KAi to String opou die8ete
		String kanalaki=chonnelField.getText();//For kanali options
		/*Connection Nick Options*/
		if(performed==connect)
		{
			if(command.equalsIgnoreCase("connect"))
			{
				
				connecti();
				connect.setText("disconnect");
			}
			else
			{
				b.quitServer("GoodBye my Lover GoodBye my friend you have been one you have been one for me.....");
				connect.setText("connect");
			}
			
		}
		if(performed==reconnect)
		{
			try
			{
				b.reconnect();
			}
			catch(Exception z)
			{
				b.log("<b>Could Not connect to server</b>");
			}
		}
	
		if(performed==changeNicks)
		{
			b.changeNick(nick.getText());
		}
		/*Options for channel*/
		if(performed==depart)
		{
			b.partChannel(kanalaki,"Manual Parting By user");
		}
		if(performed==join)
		{

			b.joinChannel(kanalaki);
		}
		if(performed==skaseButton)
		{
			b.skasee(kanalaki);
		}
		if(performed==removeSkaseButton)
		{		
			b.unskase(kanalaki);
		}
		/*File options*/
		if(performed==save)
		{
			File f=selectFile(true,false);//epilogi arxeiou
			if(f!=null)
			{
				Data d=new Data(password.getText(),server.getText(),ccport.getText());
				d.store(f);//Apo8ikeusi egrafwn
			}
		}
		if(performed==loadServerOptions)
		{
			File f=selectFile(false,false);//epilogi arxeiou
			Data d=new Data();
			if(d.load(f))
			{
				server.setText(d.getServer());
				password.setText(d.getPassword());
				ccport.setText(d.getPort());
			}
			else
			{
				b.log("<b>Δεν μπορεί να φορτωθεί το αρχείο</b>");
			}
		}
		if(performed==logSave)
		{
			File f=selectFile(true,true);
			if(f!=null)
			{
				if(!b.storeLog(f))
				{
					JOptionPane.showMessageDialog(this,"File cannnot be Saved;");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this,"File cannnot be Saved;");
			}
		}
	}
	/*
	*In this we check if the bot is connected
	*/
	public void run()
	{
		while(t!=null)
		{
			if(!b.isConnected())//An den einai sindedemeno
			{
				connect.setText("connect");
				try
				{
					b.changeNick("Terminator");
					connecti();
					b.sendMessage("NickServ","RELEASE RoboCop "+password.getText());
					b.changeNick("RoboCop");
					connect.setText("disconnect");
				}
				catch(Exception z)//Se periptwsi opou den mporei na ksanasyndethei prepei na mas loggaroun
				{
					b.log("<p><b>Δεν μπορεί να συνδεθεί στον server</b></p>");
				}
			}			
			else
			{
				connect.setText("disconnect");
			}
		}
	}
	public static void main(String[] args)
	{
		BotGui g=new BotGui();
		g.setVisible(true);
	}
}
