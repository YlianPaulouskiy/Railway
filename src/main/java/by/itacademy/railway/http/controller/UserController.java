package by.itacademy.railway.http.controller;

import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.dto.role.RoleReadDto;
import by.itacademy.railway.service.OrderService;
import by.itacademy.railway.service.RoleService;
import by.itacademy.railway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {
        model.addAttribute("users", PageResponse.of(userService.findAll(pageable)));
        model.addAttribute("roles", roleService.findAll());
        return "user/users";
    }


    // TODO: 28.06.2023 передать значение из html в controller не создавая нового класса
    @PostMapping("/{id}/role/update")
    public String updateRole(@PathVariable Long id, @ModelAttribute Integer roleId) {
        userService.updateRole(id, RoleReadDto.builder().build());
        return "redirect:/users";
    }

    @PostMapping("/{id}/remove")
    public String removeUser(@PathVariable Long id) {
        userService.remove(id);
        return "redirect:/users";
    }

}
