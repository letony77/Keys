package com.hibernate.web;

import com.hibernate.web.controller.AnnonceController;
import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.Annonce;
import com.hibernate.web.entities.User;
import com.hibernate.web.service.AnnonceServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class WebApplicationTests {
AnnonceServiceImpl test = new AnnonceServiceImpl();
UserRepository userRepository;
AnnonceController annonceController;

@Mock
Annonce annonce;
	@Test
	public void emailSenderTest() {
		annonce = new Annonce();
		assertEquals("mailConfirm", annonceController.saveE(annonce));
	}

}
