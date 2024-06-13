package com.mounwan.moudules;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
        if(errors.hasErrors()) {
            return "account/sign-up";
        }
       Account account = accountService.processNewAccount(signUpForm);
        ///accountService.login(account);
        return "redirect:/"; //에러없을시 첫화면
    }

    @GetMapping("/check-email-token") //이메일에 해당하는 사용자가 있는지 확인
    public String checkEmailToken(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);

        if(account == null) { //이메일 존재하지 않을때
            model.addAttribute("ERROR","email is wrong");
            return "account/checked-email";
        }

        if(!account.isValidToken(token)) { //이메일 토큰값이 불일치 할때
            model.addAttribute("ERROR","token is wrong");
            return "account/checked-email";
        }
        accountService.completeSignUp(account);

        model.addAttribute("nickname", account.getNickname());
        return "account/checked-email";
    }

    @GetMapping("/checked-email")
    public String checkEmail() {

    }
}
