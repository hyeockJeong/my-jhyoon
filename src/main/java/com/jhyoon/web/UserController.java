package com.jhyoon.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhyoon.domain.User;
import com.jhyoon.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("login")
	public String login(String userId, String password, HttpSession session) {
		
		User user = userRepository.findByUserId(userId);
		
		String returnFailed = "redirect:/users/loginForm";
		if(user == null) return returnFailed;
		if(!password.equals(user.getPassword())) return returnFailed;
		
		session.setAttribute("sessionedUser", user);
		
		System.out.println("loginSuccess!");
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}

	@PostMapping("")
	public String createUser(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("{seq}/form")
	public String updateForm(@PathVariable Long seq, Model model, HttpSession session) {
		
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		
		if(sessionedUser == null) return "redirect:/users/loginForm";
		
		if(!sessionedUser.getSeq().equals(seq)) {
			throw new IllegalStateException("본인정보만 수정 가능합니다.");
		}

		model.addAttribute("user", userRepository.findById(seq).get());
		return "/user/updateForm";
	}

	@PutMapping("/{seq}")
	public String update(@PathVariable Long seq, User updatedUser, HttpSession session) {
		
		User sessionedUser = (User) session.getAttribute("sessionedUser");
		
		if(sessionedUser == null) return "redirect:/users/loginForm";
		
		if(!sessionedUser.getSeq().equals(seq)) {
			throw new IllegalStateException("본인정보만 수정 가능합니다.");
		}
		
		User user = userRepository.findById(seq).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}

}
