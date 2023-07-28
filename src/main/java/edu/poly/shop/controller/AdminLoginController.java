package edu.poly.shop.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.AdminLoginDto;
import edu.poly.shop.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller


public class AdminLoginController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("alogin")
	public String login(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		return "/admin/accounts/admin-login";
	}
	
	@GetMapping("register")
	public String register() {
		return "/admin/accounts/addOrEdit";
	}
	
	@PostMapping("alogin")
	public ModelAndView login(ModelMap model,
			@Valid @ModelAttribute("account") AdminLoginDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("/admin/accounts/admin-login", model); 
		}
		
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		
		if (account == null) {
			model.addAttribute("message", "không tìm thấy username hoặc password");
			return new ModelAndView("/admin/accounts/admin-login", model);
		}
		
		session.setAttribute("username", account.getUsername());
		
		Object ruri = session.getAttribute("redirect-uri"); //kiểm tra nếu tồn tại redirect-uri trong session thì chuyển hướng đến ruri
		if (ruri != null ) {
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:" + ruri);
		}
		
		
		return new ModelAndView("forward:/admin/categories",model);
	}
	
	
	
	@PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/alogin";
    }
}
