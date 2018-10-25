package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private Configuration freemarkerConfigg;

	/**
	 * @summary send mail
	 * @author Thehap Rok
	 * @param title
	 * @param href
	 * @param userMail
	 * @param registCode
	 * @param expireDate
	 * @throws MessagingException
	 * @return void
	 */
	public void sendMail(String title, String href, String userMail, String registCode, Date expireDate)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		try {
			freemarkerConfigg.setClassForTemplateLoading(this.getClass(), "/templates");
			Template t = freemarkerConfigg.getTemplate("email-template.ftl");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("expireDate", expireDate.toString());
			model.put("href", href);
			model.put("token", registCode);
			String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			helper.setSubject(title);
			helper.setText(mailContent, true);
			helper.setTo(userMail);
			mailSender.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
