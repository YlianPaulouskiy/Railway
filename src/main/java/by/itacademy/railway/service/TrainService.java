package by.itacademy.railway.service;

import by.itacademy.railway.dto.train.TrainSearchDto;
import by.itacademy.railway.entity.RouteStation;
import by.itacademy.railway.entity.Train;
import by.itacademy.railway.mapper.TrainMapper;
import by.itacademy.railway.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;

    @Transactional
    public List<TrainSearchDto> findByRoute(String from, String to, LocalDateTime when) {
        List<TrainSearchDto> trainSearchList = new ArrayList<>();
        for (Train train : trainRepository.findAll()) {
            var stations = train.getRouteStations();
            for (int i = 0; i < stations.size(); i++) {
                if (stations.get(i).getStation().getName().equals(from)) { //совпадение станции отправления
                    for (int j = i + 1; j < stations.size(); j++) {
                        if (stations.get(j).getStation().getName().equals(to)) { //совпадение станции прибытия
                            for (RouteStation station : stations) {
                                if (station.getDepartureTime() != null
                                    && station.getDepartureTime().toLocalDate().isEqual(when.toLocalDate())
                                    && station.getDepartureTime().toLocalTime().isAfter(when.toLocalTime())) {
                                    trainSearchList.add(trainMapper.toSearchDto(train, from, to));
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return trainSearchList;
    }


}
