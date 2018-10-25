package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TokenVerifition;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.EmailDTO;
import com.example.demo.entity.dto.PasswordDTO;
import com.example.demo.service.MailService;
import com.example.demo.service.PasswordService;
import com.example.demo.service.TokenVerificationService;
import com.example.demo.service.UserService;
import com.example.demo.util.VerificationUtil;

@Controller
public class ForgetPasswordController {
	@Autowired
	private MailService mailService;

	@Autowired
	UserService userService;

	@Autowired
	private VerificationUtil veritificationUtil;

	@Autowired
	private TokenVerificationService tokenVerificationService;
	
	@Autowired
	private PasswordService passwordService;


	@GetMapping("/forget-password")
	public String creadEmailDto(ModelMap modelMap) {
		EmailDTO email = new EmailDTO();
		modelMap.addAttribute("emailDTO", email);
//		return "check-email";
		return "checkmail";
	}


	@PostMapping("/forget-password")
	public String checkOutEmailAndSendMail(@Valid @ModelAttribute("emailDTO") EmailDTO emailDTO,
			BindingResult bindingResult, ModelMap modelMap) {
		String email = emailDTO.getEmail();
		User existEmail = userService.getUserByEmail(email);
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError objectError : list) {
				System.out.println(objectError);
			}
			modelMap.addAttribute("emailDTO", emailDTO);
			return "checkmail";
		}
		// kiem tra k  ton tai
		if (existEmail == null || !existEmail.getNonDel()) {
			modelMap.addAttribute("msg", "Your email does not exist");
		} else {
			// da ton tai
			String registCode = veritificationUtil.generateVerificationCode(email);
			Date expireDate = veritificationUtil.calculatorExpireTime();
			try {

				// tim kiem user co gmail la ?
				User user = userService.getUserByEmail(email);
				// neu da goi email roi thi ra ve thong bao
				if (user.getTokenVerifition() != null) {
					modelMap.addAttribute("msg", "Sent email please check email");
				} else {
					// goi email
					mailService.sendMail("FORGET PASSWORD", "/change-password", email,
							registCode, expireDate);
					TokenVerifition tokenVerifition = new TokenVerifition(user, registCode, expireDate, 1);
					tokenVerificationService.addToken(tokenVerifition);
					modelMap.addAttribute("msg", "please check email");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		return "check-email";
		return "checkmail";
	}

	@GetMapping("/change-password")
	public String viewChangePassword(@RequestParam("token") String token, ModelMap modelMap) {
		TokenVerifition checkToKenVerifition = tokenVerificationService.findTokenByTokenCode(token);
		// kiem tra token da duoc su dung hay chua
		if (checkToKenVerifition == null) {
			modelMap.addAttribute("msg", "This code is no longer valid");
			return "error-active";
		} else {
			Date nowDate = new Date();
			// kiem tra het thoi gian qua han
			if (checkToKenVerifition.getExpireTime().getTime() < nowDate.getTime()) {
				checkToKenVerifition.setExpireTime(veritificationUtil.calculatorExpireTime());
				checkToKenVerifition.setTokenCode(veritificationUtil.generateVerificationCode(checkToKenVerifition.getUser().getEmail()));
	            tokenVerificationService.editToken(checkToKenVerifition);
	            try {
					mailService.sendMail("FORGET PASSWORD","/change-password",checkToKenVerifition.getUser().getEmail(),checkToKenVerifition.getTokenCode(),checkToKenVerifition.getExpireTime());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				modelMap.addAttribute("msg", "This code is no longer valid");
				return "error-active";
			}
			modelMap.addAttribute("emailToken", checkToKenVerifition.getUser().getEmail());
			
			// set email cua nguoi kich hoat 
			PasswordDTO passwordDTO = new PasswordDTO();
			passwordDTO.setEmail(checkToKenVerifition.getUser().getEmail());
			passwordDTO.setToken(token);
			passwordDTO.setPasswordCurrent("a");
			modelMap.addAttribute("changePasswordDTO", passwordDTO);
			return "change-password";
		}

	}


	@PostMapping("/change-password")
	public String toDoChangePassword(@Valid @ModelAttribute("changePasswordDTO") PasswordDTO passwordDTO,
			BindingResult bindingResult, ModelMap modelMap) {
		String email = passwordDTO.getEmail();
		String token = passwordDTO.getToken();
		User user = userService.getUserByEmail(email);
		
		String dbPassword = user.getPassword(); 
		String newPassword = passwordDTO.getNewPassword();
		String newMatchingPassword= passwordDTO.getNewMatchingPassword();
		
		boolean checkDuplicatePasswordCurrent= passwordService.checkDuplicatePasswordCurrent(newPassword, dbPassword);
		boolean checkDuplicateMatchingPassword = passwordService.checkDuplicateMatchingPassword(newPassword, newMatchingPassword);
	
		// not bank
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors().toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError objectError : list) {
				System.out.println(objectError);
			}
			modelMap.addAttribute("changePasswordDTO", passwordDTO);
			return "change-password";
		}
		// check password trung
		if (checkDuplicatePasswordCurrent) {
			modelMap.addAttribute("msg", "The password you entered is the same as your current password");
			return "change-password";
		}
		if (!checkDuplicateMatchingPassword) {
			modelMap.addAttribute("msg", "You enter a mismatched password");
			return "change-password";
		}

		// save change
		passwordService.saveNewPasswords(user, newPassword);

		// delete token
		TokenVerifition tokenVerifition = tokenVerificationService.findTokenByTokenCode(token);
		tokenVerificationService.deleteTokenById(tokenVerifition.getId());
		
		modelMap.addAttribute("notification", "You have successfully changed your password");
		return "login-page";
	}

}
