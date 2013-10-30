package rero.config;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

public class ServerConfig implements Serializable {
	public String host;
	public int port;
	public String login;
	public String password;
	public int delay;
	public String encoding;

	public static ServerConfig createFromString(String binary) {
		try {
			byte[] data = DatatypeConverter.parseBase64Binary(binary);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			ServerConfig o = (ServerConfig)ois.readObject();
			ois.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}

	public String toString() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			return DatatypeConverter.printBase64Binary(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}
}
