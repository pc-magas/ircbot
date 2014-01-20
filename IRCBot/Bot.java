package IRCBot;

import IRCBot.*;
import org.jibble.pircbot.*;
import java.util.*;
import javax.swing.JLabel;
import java.io.*;
/**
*Τhis is the official Api of PC_MAGA's Bot
*@author pc_magas
*@Version 1.0.0
*/
public class Bot extends PircBot
{
	/**
	*	AN ARRAYLIST OF ALL THE JOINED CHANNELS
	*/
	protected ArrayList<String> channels=new ArrayList<String>();//joined channels
	/**
	*Here we set up the channels that it have been kicked
	*/
	private ArrayList<String> kicked= new ArrayList<String>();//kicked channels
	/**
	*An arraylisty to keep the logs of the bot
	*/
	private ArrayList<String> logs=new ArrayList<String>();//logs of the bot
	/**
	*Here the bot store the chanels where NOT to auto gesture each user that joins in
	*/
	private ArrayList<String> skase=new ArrayList<String>();//channels to shut the fuck up the Bot
	/*
	*With this I keep the latest log
	*/
	private  String latestLog="";//Here I keep the latest log
	/**
	*A component to keep the logs if I need to display into a JFrame
	*/
	public JLabel logViewer=null;//A graphic way to display the logs
	/*
	*A Constructor of the Bot
	*
	*@param name: The nick or the name of the Bot
	*
	*@param version: The Version on the Bot. This message will be displayed when you type /version and the nick of the bot into an IRC Client like mIRC or Xchat.
	*
	*@param graphMode: If you need a GUI of the Bot and need to get a JLabel of the logs must be true. In any other case toy can use false
	*
	*/
	public Bot(String name,String version,boolean graphMode)//Whits this I  give Birth to the Bot
	{
		this.setName(name);
		this.setVersion(version);
		if(graphMode)
		{
			 logViewer=new JLabel("<html>");
			 logViewer.setVerticalAlignment(JLabel.BOTTOM);
			 logViewer.setHorizontalAlignment(JLabel.LEFT);
		}
		setVerbose(false);
	}
	/**
	*Whith this method you can say hello to somebody in private messase or in a channel depending on Bot's Local
	*time
	*@param to: Is the parameter that says where I send the waving message. If yuo want to gesture on pm just use the same value with the reiciever
	*@param	reciever: Is the person that you gesture
	*
	*/
	public void xairetismos(String to,String reciever)//A way to say Hello into somewere like channel tpo Somebody
	{
		Date d=new Date();
		
		if(d.getHours()<=13&&d.getHours()>=5)
		{
			sendMessage(to,reciever+" Kalimera");
		}
		else if(d.getHours()>14||d.getHours()<=2)
		{
			sendMessage(to,reciever+" Kalispera");
		}
		else
		{    
			sendMessage(to,reciever+" Geia sou/sas");
		}
		if(reciever.equals("")){reciever="olous";}
		log("Το Bot μόλις χεραίτησε τον/την/tous "+reciever+"από το"+to);
	}
	/**
	*This methos tels the time to the sender
	*@param sender: The value represents where to tell the time
	*/
	public void time(String sender)//Time teller
	{
		Date d=new Date();
		String time="H topiki wra einai: "+Colors.BOLD+Colors.RED+d.getHours()+":"+d.getMinutes();
		sendMessage(sender,time);
		log("<b> χρήστης / η στο κανάλι "+sender+" ζήτησε να δει την ώρα</b>");
	}
	/**
	*@override
	*The method that tels the Bot what to do on pm. For more info <a>http://www.jibble.org/ </a>
	*
	*/
	protected void onPrivateMessage(String sender,String login,String hostname,String message)
	{
		if(message.equalsIgnoreCase("time")||message.equalsIgnoreCase("wra"))//An zita kapoion na toy peis thn wra
		{
			time(sender);			
		}
		if(message.equalsIgnoreCase("hi")||message.equalsIgnoreCase("Hello")||message.equals("Geia")||message.equals("geia sou"))//An kapoios tou xeretisei xeraita ton analogws
		{
			xairetismos(sender,sender);
		}
		if(sender.equalsIgnoreCase("pc_magas")&&message.equalsIgnoreCase("log"))//An soy pw se pm na mou emfanisei to log
		{
			sendMessage(sender,"To bot sinde8ike sta eksis kanalia:");
			
			for(int i=0;i<channels.size();i++)
			{
				sendMessage(sender,channels.get(i)+"\n");
				try
				{
					Thread.sleep(500);
				}catch (Exception t)
				{
				}
				
			}
			sendMessage(sender,"To bot petax8ike eksw apo ta eksis kanalia");
			for(int i=0;i<kicked.size();i++)
			{
				sendMessage(sender,kicked.get(i)+"\n");
				try
				{
					Thread.sleep(500);
				}catch (Exception t)
				{
				}
			}
			sendMessage(sender,"To bot kategrapse ta eksis");
			for(int i=0;i<logs.size();i++)
			{
				sendMessage(sender,logs.get(i)+"\n");
				try
				{
					Thread.sleep(500);
				}catch (Exception t)
				{
				}
			}
			sendMessage(sender,"Latest log: "+getLatestLog());
		}
	}
	/**
	*@override
	*
	*/
	protected void onMessage(String channel, String sender,String login, String hostname, String message)
	{
				
			if(message.equalsIgnoreCase("!time")||message.equalsIgnoreCase("!wra"))//Otan kapoio zhta apo to kanali na peis thn wra
			{
				time(channel);
				log("Ο χρήστης "+sender+" ζήτησε να δει την ώρα"+" στο κανάλι "+channel+"\n");
			}
			else if(message.equalsIgnoreCase("!iq"))//PAixnidi me thn IQ
			{
				Random r=new Random();
				sendMessage(channel, sender+", your iq level is: "+r.nextInt(200));
				log("O χρήστης: "+sender+"Ζήτησε να δει το IQ του"+" στο κανάλι "+channel+"\n");
			}
			else if(message.equalsIgnoreCase("Variemai"))//Otan kapoios pei variemai
			{
				sendMessage(channel, sender+": Dokimase na asxoli8eis me ena Hobby");
				log("Tο Bot ενθάρυνε τον/την "+sender+" στο κανάλι "+channel+"\n");
			}
			else if(message.equalsIgnoreCase("Back"))//KAi otan er8ei kapoios pisw
			{
				sendMessage(channel, "Welcome Back "+sender);
				log("To Bot καλωσόρισε τον/την"+sender+" στο κανάλι "+channel+"\n");
			}
			else if(message.equalsIgnoreCase("!skase")||message.equalsIgnoreCase("!silance")||message.equalsIgnoreCase("!toumpeki")||message.equalsIgnoreCase("!voulosto"))//Here we turn on the skase mode into a channel
			{
					skasee(channel);
					sendMessage(channel, "Ok den 8a sas ksanamilisw ksana eidika ston/sthn "+sender+" Zen me agapaei poia :-( Mamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!");
			}
			else if(message.equalsIgnoreCase("!unskase")||message.equalsIgnoreCase("!xaireta"))//Turning off skase mode
			{
				unskase(channel);
				sendMessage(channel, "Telika den kaneis xwris emena "+sender+" e? Em ena toso kalo Bot san kai emena panta se kannei na 8es na se xaireta ;-)");
			}
			else
			{
				String[] messages=message.split(" ");
			 	if(messages[0].equalsIgnoreCase("!sex"))
				{
					if(messages.length==1)
					{
						sendMessage(sender,"Ela sthsou kai erxomai");

					}
					else
					{
						sendMessage(sender,"Den mporw na gamisw ton/thn "+messages[1]+" exw periodo");
					}
					log("<p style=\"color=red\"><strong>To bot απάντησε στο sex request του/της "+sender+" στο κανάλι "+ channel+"</strong></p>");
				}
			}
	}
	/**
	*@override
	*
	*/
	protected void onJoin(String channel, String sender, String login, String hostname)
	{
		
		
		if(sender.equals(this.getNick()))//An o xrhstis opou mpainei einai to idio to bot
		{
			xairetismos(channel,"");//xeraita
			channels.add(channel);//KAI PROS8ESE STA SINDEDEMENA KANALIA TO KANALI
			log("<p style=\"color=red\"><strong>To Bot μπήκε στο κανάλι"+channel+"</strong></p>");
		}
		else//OTAN MPAINEI KAPOIOS ALLOS
		{
			if(isSkase(channel)==false)//an den sou exoun pei na to voulwseis
			{
				xairetismos(channel,sender);//xaireta
			}
		}

	}
	/**
	*@override
	*
	*/
	protected void onInvite(String targetNick, 
				String sourceNick, 
				String sourceLogin, 
				String sourceHostname, 
				String channel) 
	{
		
		if(targetNick.equals(this.getNick()))//An exoun proskalesei esena sto kanali
		{
			joinChannel(channel);//sindesou
			log("<p style=\"color=red\"><strong>To Bot προσκλήθηκε από τον "+sourceNick+"στο κανάλι"+channel+"</strong></p>");
		}
		
	}
	/**
	*@override
	*
	*/
	protected  void onPart(String channel, String sender, String login, String hostname)
	{
		
		if(sender.equals(this.getName()))//An eisai esy opou apoxwrises apo to kanali
		{
			channels.remove(channel);//Afairese apo thn ArrayList twn sindedemenwn kanaliwn to kanali
			log("<p style=\"color=red\"><strong>To Bot απoχώρισε από το κανάλι"+channel+"</strong></p>");
		}
	}
	/**
	*@override
	*
	*/
	protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason)
	{	
		kicked.add(channel);//Shmeiwse to kanali opou se ksanaklwtsisane
		channels.remove(channel);//Kai afairese to apo ta sindedemena kanalia
		log("<p style=\"color=red\"><strong>Πεταξε έξω το Bot ο/η"+kickerNick+"από το κανάλι "+channel+" για τον λόγο: \""+reason+"\" </strong></p>");
		
	}
	/**
	*@override
	*
	*/
	protected void onAction(String sender,String login,String hostname,String target,String action)
	{
		if(action.equalsIgnoreCase("back"))
		{
			sendMessage(sender,"Welcome Back "+sender);
		}
		else if(action.equalsIgnoreCase("away"))
		{
			sendMessage(sender,"Come Back "+sender);
		}
		log("<p style=\"color=red\"><strong> Ο/H "+sender+" έκανε το action "+action+" με στόχο το "+target+"</strong></p>");	
	}
	/**
	*@override
	*
	*/
	protected void onDisconnect()
	{
		
		for(int i=0;i<channels.size();i++)
		{
			channels.remove(i);
		}
		for(int i=0;i<kicked.size();i++)
		{
			kicked.remove(i);
		}
		log("<p style=\"color=red\"><strong>Το Bot aposynde8ike apo ton server</strong></p>");
	}
	/**
	*@override
	*
	*/
	protected void onConnect()
	{
		log("<p style=\"color=red\"><strong>To Bot sinde8ike ston server</strong></p>");
		
	}
	/**
	*With this method you get all the channels the bot joined into
	*/
	public ArrayList<String> getConnectedChannels()//Epistrefei ola ta sindedemena kanalia
	{
		ArrayList<String> h=new ArrayList<String>();
		for(int i=0;i<channels.size();i++)
		{
			h.add(channels.get(i));
		}
		return h;
	} 
	/**
	*With this you get all the channels that an operator kicked it
	*/
	public ArrayList<String> getKickedChannels()//Epistrefei ola ta kanalia opou petaksan eksw to bot
	{
		return (ArrayList<String>)kicked.clone();
	}
	/**
	*With this you get the channel that the bot last joined
	*/
	public String lastJoined()//Epistrefei to teleutaio kanali opou mpike to Bot
	{
		return channels.get(channels.size()-1);
	}
	/**
	*With this I can get the logs of the bot
	*/
	public ArrayList<String> getLog()//epistrofi tis ArrayList tou Log
	{
		return (ArrayList<String>)logs.clone();
	}
	/**
	*With this I can get the last log have occured
	*/
	public String getLatestLog()//Epistrefei thn poio prosfati katagrafi
	{
		return latestLog;
	}
	/**
	*With this I can put a log line into the bot and into the graphical object if it is not null
	*/
	public void log(String line)//Me8odos katagrafis
	{
		super.log(line);
		latestLog=line;
		logs.add(latestLog);
		if(logViewer!=null)
		{
			logViewer.setText(logViewer.getText()+latestLog+"<br>");
		}
	}
	/**
	*With this method we tell the bot not to auto gesture somebody in a channel
	*@param skasee: IS where to shut the fuck up (oooops)
	*/
	public void skasee(String channel)//Me8odos na to voulwnei se ena kanali
	{
		if(isSkase(channel)==false)
		{
			skase.add(channel);
			log("<p style=\"color=red\"><strong>Ζητήθηκε το βοτ να το βουλώσει στο καναλι "+channel+"</strong></p>");
		}
		else
		{
			log("<p style=\"color=red\"><strong>Το έχει ήδη βουλώσει στο κανάλι "+channel+" <p style=\"color=red\"><strong>");
		}
	}
	/**
	*With this method you can tell if in a channnel auto gestures or not.
	*It returns true if the channel is in the list of thje channels to not to auto-gesture
	*@param Is the channel where you want ot check if auto gestures 
	*/
	public boolean isSkase(String channel)//Me8odos opou sou lei an to exei voulwsei se ena kanali
	{
		if(skase.contains(channel)==true)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	/**
	*With this method you can tell to the bot to auto gesture to a channel that you told not to.
	* 
	*@param The channel to continue auto gesture on a channel
	*/
	public void unskase(String channel)//Afairesi tou skasmou se ena kanali
	{
		if(isSkase(channel)==true)
		{
			skase.remove(channel);
			log("<p style=\"color=red\"><p style=\"color=red\"><strong>Το Bot δεν το βουλώνει στο καναλι "+channel+"</strong></p></p>");
		}
		else
		{
			log("<p style=\"color=red\"><strong>Φίλε μίλαω, κι άλλο! Πόσο ποια?</strong></p>");
		}
	}
	/**
	*With this method you can see if the bot is joined into a channel
	*
	*@param channel: The channel you want to know if the bot is joined
	*/
	public boolean isJoined(String channel)
	{
		return channels.contains(channel);
	}
	/**
	*Method for loading file
	*@param f: File for storing the logs
	*/
	public boolean storeLog(File f)
	{
		String head="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n <html>\n<head>\n<title> IRCBOT LOG on "+ new Date().toLocaleString()+"</title>\n</head>\n<body>";
		String s,si[];
		si=(String [])logs.toArray();
		try
		{
			BufferedWriter w=new BufferedWriter(new FileWriter(f));
			w.write(head,0,head.length());
			for(int j=0;j<si.length;j++)
			{
				w.write(si[j],0,si[j].length());
			}
			s="</body></html>";
			w.write(s,0,s.length());
			w.close();
			return true;
		}catch(IOException e)
		{
			return false;
		}
	}
	/** 
	*With this you can change the nick of your bot when you are connected to an irc server or not
	*@param nick: The nickname you want to change
	*/
	public void setNick(String nick)
	{
		if(isConnected())
		{
			changeNick(nick);
		}
		else
		{
			super.setName(nick);
		}
	}
}
