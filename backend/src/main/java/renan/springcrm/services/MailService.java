package renan.springcrm.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailService {

	@Value("${spring.mail.username}")
	private String emailFrom;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine template;

	public void sendNewUser(String firstName, String lastName, String to, String password) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					"UTF-8");

			Context context = new Context();
			context.setVariable("firstName", firstName);
			context.setVariable("lastName", lastName);
			context.setVariable("email", to);
			context.setVariable("password", password);

			String html = template.process("email/sendNewUser", context);
			helper.setTo(to);
			helper.setText(html, true);
			helper.setSubject("Dados de acesso ao System CRM");
			helper.setFrom(emailFrom, "System CRM");

			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendRecoverPassword(String firstName, String lastName, String to, String code) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					"UTF-8");

			Context context = new Context();
			context.setVariable("firstName", firstName);
			context.setVariable("lastName", lastName);
			context.setVariable("email", to);
			context.setVariable("code", code);

			String html = template.process("email/sendRecoverPassword", context);
			helper.setTo(to);
			helper.setText(html, true);
			helper.setSubject("Redefinição de senha");
			helper.setFrom(emailFrom, "System CRM");

			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
