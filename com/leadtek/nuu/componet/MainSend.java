package com.leadtek.nuu.componet;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;

@Component
public class MainSend {

//	@Scheduled(fixedRate = 1 * 1000)
	public void send(String account,String mailinfo,String title) throws GeneralSecurityException {

		getTrust();
		final String username = "iro";
		final String password = "iro381021";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "mail.nuu.edu.tw");
		props.put("mail.smtp.port", "25");
		

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.ssl.socketFactory", sf);

		Authenticator auth = new SMTPAuthenticator(username, password);

		Session session = Session.getInstance(props,auth);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("iro@nuu.edu.tw"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(account+"@nuu.edu.tw"));
			message.setSubject(title);
			message.setText(mailinfo);

//			Transport transport = session.getTransport("smtp");
//			   transport.connect(host, port, username, password);

			Transport.send(message);

			System.out.println("寄送email結束.");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static void getTrust() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		private String SMTP_AUTH_PWD;
		private String SMTP_AUTH_USER;

		public SMTPAuthenticator(String SMTP_AUTH_USER, String SMTP_AUTH_PWD) {
			super();
			this.SMTP_AUTH_USER = SMTP_AUTH_USER;
			this.SMTP_AUTH_PWD = SMTP_AUTH_PWD;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}

}
