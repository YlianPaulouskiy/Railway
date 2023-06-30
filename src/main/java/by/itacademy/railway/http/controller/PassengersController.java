package by.itacademy.railway.http.controller;

import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.dto.passenger.PassengerStringDto;
import by.itacademy.railway.entity.DocumentType;
import by.itacademy.railway.entity.Gender;
import by.itacademy.railway.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengersController {

    private final PassengerService passengerService;

    @GetMapping("/{userId}")
    public String findByUserId(@PathVariable Long userId, Model model, @PageableDefault(value = 5) Pageable pageable) {
        model.addAttribute("passengers", PageResponse.of(passengerService.findAllByUserId(userId, pageable)));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("documents", DocumentType.values());
        return "passenger/passengers";
    }

    @PostMapping("/{userId}/remove/{id}")
    public String remove(@PathVariable Long userId, @PathVariable Long id) {
        passengerService.remove(id);
        return "redirect:/passengers/".concat(String.valueOf(userId));
    }

    @PostMapping("/{userId}/update/{id}")
    public String update(@PathVariable Long userId, @PathVariable Long id, @ModelAttribute PassengerStringDto passengerStringDto) {
        passengerService.update(id,passengerStringDto);
        return "redirect:/passengers/".concat(String.valueOf(userId));
    }

}
