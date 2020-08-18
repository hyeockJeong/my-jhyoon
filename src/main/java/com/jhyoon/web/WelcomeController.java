package com.jhyoon.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/helloworld")
	public String welcome(Model model) {
		
		model.addAttribute("type","<h2>It's Mustache</h2>");
		
		model.addAttribute("name", "jhYoon");
		model.addAttribute("value", 10000);
		model.addAttribute("taxed_value", 31);
		model.addAttribute("in_ca", true);
		
		model.addAttribute("company","<b>JHYOON</b>");
		model.addAttribute("age",31);
		
		List<MustacheModel> repo = Arrays.asList(new MustacheModel("jh_yoon"), new MustacheModel("jh_yoon2"));
		model.addAttribute("repo",repo);
				
		
		
		
		
		return "welcome";
	}
	
	
}
