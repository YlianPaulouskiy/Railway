package by.itacademy.railway.http.controller;

import by.itacademy.railway.dto.order.OrderReadDto;
import by.itacademy.railway.dto.pageable.PageResponse;
import by.itacademy.railway.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userId}")
    public String findByUserId(@PathVariable Long userId, Model model, @PageableDefault(value = 5)Pageable pageable) {
        model.addAttribute("orders", PageResponse.of(orderService.findAllByUserId(userId, pageable)));
        return "order/orders";
    }


}
