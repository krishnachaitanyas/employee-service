package com.krishna.employee.service.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FileWriterUtil {
	
	public static String sendFileToRemoteLocation(String fileName) throws Exception{
		String host = "192.168.127.137";
		String userName = "krishnacs";
		//String password = "root123";
		Properties properties = new Properties();
		properties.put("StrictHostKeyChecking","no");
		JSch jsch = new JSch();
		System.out.println(System.getProperties());
		jsch.addIdentity("C:\\Users\\krishnacs\\Documents\\UbuntuKeys\\Private\\private.ppk");
		Session session = jsch.getSession(userName, host, 22);
		//session.setPassword(password);
		session.setConfig(properties);
		session.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.put(fileName, fileName);
		sftpChannel.exit();
		session.disconnect();
        return "DONE";
		
		
	}

}
