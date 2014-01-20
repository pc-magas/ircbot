package IRCBot;

import IRCBot.*;
import java.io.*;
/**
*Storable class for data essential rof connecting the IRC Bot to the server
*@author pc_magas
*@version 1.0.0
*/
public class Data implements Serializable
{
	/** 
	*String to store the password*/
	private String password;
	/**
	*String to Store the server address (ip or url)
	*/
	private String server;
	/**
	*Here we store the port to connect to the server
	*/
	private String port;
	/**
	*Constructor Metohd
	*@param password: Here you put the password of the server you want to connect
	*@param server: the server adress you want to be connected
	*@param port: the port of the server you want to connect
	*/
	public Data(String password,String server, String port)
	{
		this.password=password;
		this.server=server;
		this.port=port;
	}
	/**
	*Non Parametric constructor method
	*/
	public Data()
	{
	}
	/**
	*With this method we get the password sotred in class
	*/
	public String getPassword()
	{
		return password;
	}
	/**
	*With this we get the server adress
	*/
	public String getServer()
	{
		return server;
	}
	/**
	*With this we get the number of port in string form
	*/
	public String getPort()
	{
		return port;
	}
	/**
	*With this methos we set a specific string for password
	*@param password: the password we want to set
	*/
	public void setPassword(String password)
	{
		this.password=password;
	}
	/**
	*With this we set a string for a server adress
	*@param server: The adress we want to set for server
	*/
	public void setServer(String server)
	{
		this.server=server;
	}
	/**
	*With this we set a specific string for port to be connected to the server
	*@param port:the port number we want to set
	*/
	public void setPort(String port)
	{
		this.port=port;
	}
	/**
	*Methos for loading data to the object
	*@param f: File object that indicates the file you want to load
	*/
	public boolean load(File f)
	{
		Data temp=null;
		try
		{
			ObjectInputStream input=new ObjectInputStream(new BufferedInputStream((new FileInputStream(f))));
			temp=(Data)input.readObject();//reading data and storing into a temporary object file

			/* loading data to this object*/
			password=temp.getPassword();
			port=temp.getPort();
			server=temp.getServer();

			input.close();
			return true;

		}catch(Exception z)
		{
			return false;
		}

	}

	/**
	*Same as load Methon but this stores the Data into file f
	*@param f: File for storing the data
	*/

	public boolean store(File f)
	{
		if(f.exists())//if file already exists we delete in in order to overwrite on it
		{
			f.delete();
		}
		try
		{
			ObjectOutputStream output=new ObjectOutputStream(new BufferedOutputStream((new FileOutputStream(f))));
			output.writeObject(this);//writing file
			output.close();
			return true;//write sucess
		}
		catch(IOException e)
		{
			return false;//write fail
		}	
	}
	/*
	*Test main this main can be used for testing
	*/
	public static void main(String[] argc)
	{
		Data d=new Data("Malakia","ass.gr","1123");
		d.store(new File("ass.d"));
		Data d2=new Data();
		d2.load(new File("ass.d"));
		System.out.println(d2.getPassword()+' '+d2.getServer()+' '+d2.getPort());
	}
}
