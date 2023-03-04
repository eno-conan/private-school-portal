package com.school.portal.base;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BaseController {

	@GetMapping("/")
	String searchStudent() {
		try {
			return "base dir";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
