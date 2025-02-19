package com.scm.scm20;

import com.scm.scm20.controller.MessageController;
import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest

class ApplicationTests {

	private Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Disabled
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;



	@Disabled
	void sendEmailTest() {
		String[] eamils = {"camouflagetr1217@gmail.com", "mehrazzhossin@gmail.com"};
		//emailService.sendEmail("camouflagetr1217@gmail.com","Just testing perpose","I,m TR 86");
		String html = ""+"<h1 style='color:red'> Hello TR86</h1>";
		emailService.sendEmail(eamils,"Just testing  purpose","What's up bro");
	}

	@Autowired
	private ContactService contactService;

	//tas id
//	06a8b764-2c12-4fc2-a49b-b622f9501fb6
// ef id
//	8916b789-7384-4baf-bb95-1557b53c645d


//	hojaif contact id
	//3ad9cda7-519a-4511-928b-0d8112ef5981

	@Test
	void contactResult(){

//    Contact contact= contactService.getContactById("3ad9cda7-519a-4511-928b-0d8112ef5981");
//	if(contact!=null){
//		logger.info(contact.getEmail());
//	}
//	else {
//		logger.info("contact not found");
//	}

//		List<Contact> byUserId = contactService.getByUserId("8916b789-7384-4baf-bb95-1557b53c645d");
//
//        logger.info(byUserId.size() > 0 ? "contact list found "+byUserId.size() : "contact not found");

		for (Contact contact : contactService.getByUserIdSearchByFavorite("06a8b764-2c12-4fc2-a49b-b622f9501fb6")) {
			logger.info(contact.getEmail());
		}


	}




}
