package by.itacademy.railway.http.controller;

import by.itacademy.railway.dto.menu.LoginDto;
import by.itacademy.railway.dto.user.UserStringDto;
import by.itacademy.railway.service.StationService;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class MenuController {

    private final StationService stationService;
    private final UserService userService;

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("stations", stationService.findAll());
        return "menu/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "menu/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute LoginDto loginDto) {
        userService.login(loginDto.getEmail(), loginDto.getPassword())
                .map(userReadDto -> model.addAttribute("user", userReadDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "redirect:";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "menu/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute UserStringDto userDto) {
        return userService.create(userDto).map(userReadDto -> {
            return "redirect:/login";
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_MODIFIED));
    }

    // TODO: 23.06.2023 ДОДЕЛАТЬ не работает
    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("user", null);
        return "redirect:";
    }


}
