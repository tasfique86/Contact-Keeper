package com.scm.scm20;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest

class ApplicationTests {


	@Disabled
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;



	@Test
	void sendEmailTest() {
		emailService.sendEmail("tasfiquerishad1217@gmail.com","Just testing perpose","I,m TR 86");
	}




}
