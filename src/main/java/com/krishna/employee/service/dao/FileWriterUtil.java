package com.krishna.employee.service.dao;

import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FileWriterUtil {
	
	public static void main(String args[]) throws Exception{
		String host = "192.168.127.135";
		String userName = "krishnacs";
		//String password = "root123";
		Properties properties = new Properties();
		properties.put("StrictHostKeyChecking","no");
		JSch jsch = new JSch();
		jsch.addIdentity("C:\\Users\\krishnacs\\Documents\\UbuntuKeys\\Private\\private.ppk");
		Session session = jsch.getSession(userName, host, 22);
		//session.setPassword(password);
		session.setConfig(properties);
		session.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.put("C:\\Users\\krishnacs\\Pictures\\some.txt", "some.txt");
		sftpChannel.exit();
		session.disconnect();
		
		
	}

}
