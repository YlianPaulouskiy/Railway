package by.itacademy.railway.http.controller;

import by.itacademy.railway.dto.menu.SearchDto;
import by.itacademy.railway.dto.train.TrainSearchDto;
import by.itacademy.railway.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;

    @PostMapping("/find")
    public String findByRoute(Model model, @ModelAttribute SearchDto searchDto) {
        model.addAttribute("trains", trainService.findByRoute(searchDto.getFrom(), searchDto.getTo(), LocalDateTime.of(searchDto.getWhen(), LocalTime.MIN))); //просто проверка работоспособности
        return "train/search-result";
    }

//    @PostMapping("/find")
//    @ResponseBody
//    public List<TrainSearchDto> findByRoute(Model model, @ModelAttribute SearchDto searchDto) {
////        model.addAttribute("trains", trainService.findByRoute(searchDto.getFrom(), searchDto.getTo(), LocalDateTime.of(searchDto.getWhen(), LocalTime.MIN))); //просто проверка работоспособности
////        return "train/search-result";
//        return trainService.findByRoute(searchDto.getFrom(), searchDto.getTo(), LocalDateTime.of(searchDto.getWhen(), LocalTime.MIN));
//    }

}
