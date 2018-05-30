package com.FT05.CloudCA.WebREST;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.FT05.CloudCA.Service.EmailService;
import com.FT05.CloudCA.Service.EmailServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FT05.CloudCA.Service.UserService;
import com.FT05.CloudCA.Entity.User;

@Controller
public class PasswordController {

@Autowired
    private UserService userService;

@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
    private EmailServiceImpl emailService;



//Display reset page

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)

        public ModelAndView displayForgotPasswordPage() {
        ModelAndView model = new ModelAndView();
        User userEmail = new User();
        model.addObject("userEmail",userEmail);
        return new ModelAndView("forgot");
    }
// Process form submission from forgotPassword page

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)

    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @Valid User user, @RequestParam("email") String userEmail,BindingResult bindingResult,HttpServletRequest request) {
        User userExists = userService.findUserByEmail(user.getEmail());

        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        } else {
            // Generate random 36-character string token for reset password
            user.getEmail();
            user.setTokenId(UUID.randomUUID().toString());

            // Save token to database
            userService.saveUser(user);

            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getTokenId());

            emailService.sendEmail(passwordResetEmail);

            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }


        modelAndView.setViewName("forgot");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token, @Valid User user) {

        User usertoken = userService.findByTokenId(user.getTokenId());

        if (usertoken !=null) {
            modelAndView.addObject("resetToken", token);
        }
        else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }
        ModelAndView model = new ModelAndView();
        User reset = new User();
        model.addObject("userEmail",reset);
        modelAndView.setViewName("reset");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        User userreset = userService.findByTokenId(requestParams.get("token"));
        if (userreset !=null) {
            userreset.getTokenId();

            userreset.setPassword(bCryptPasswordEncoder.encode(userreset.getPassword()));
            // Set the reset token to null so it cannot be used again
            userreset.setTokenId(null);

            // Save user
            userService.saveUser(userreset);

            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }
        else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("reset");
        }

        return modelAndView;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }
}


