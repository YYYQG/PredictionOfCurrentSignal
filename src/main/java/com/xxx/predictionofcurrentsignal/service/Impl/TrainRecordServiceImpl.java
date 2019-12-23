package com.xxx.predictionofcurrentsignal.service.Impl;

import com.xxx.predictionofcurrentsignal.dao.entity.TrainRecord;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.TrainRecordRepository;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import com.xxx.predictionofcurrentsignal.service.TrainRecordService;
import com.xxx.predictionofcurrentsignal.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainRecordServiceImpl implements TrainRecordService {

    @Autowired
    private TrainRecordRepository trainRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> getAlTrainByPage(String fileName, String start, String end, Integer pageSize, Integer page,String name) {

        Map<String,Object> result = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        Pageable pageable =  PageRequest.of(page-1,pageSize);
        User user=userRepository.findUserByName(name);
        Specification s = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotBlank(fileName)){
                    list.add(criteriaBuilder.like(root.get("fileRecord").get("fileName"),"%"+fileName+"%"));
                }
                if (StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(end)){
                    list.add(criteriaBuilder.between(root.get("trainTime"),LocalDateTime.parse(start,dateTimeFormatter),LocalDateTime.parse(end,dateTimeFormatter)));
                }
                list.add(criteriaBuilder.equal(root.get("userId"),user.getId()));
                return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };
        Page<TrainRecord> trainRecords = trainRecordRepository.findAll(s,pageable);
        long total = trainRecords.getTotalElements();
        List<TrainRecord> files = trainRecords.getContent();
        result.put("trainTotal",total);
        result.put("trainRecord",files);
        return result;

    }


    @Override
    public Map<String, Integer> getTrainCountByName(String username) {

        Map<String,Integer> result = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate tom = today.plusDays(1);
        User user = userRepository.findUserByName(username);
        int todayCount = trainRecordRepository.countTrainRecordByTrainTimeBetweenAndUserId(today.atTime(0,0,0),tom.atTime(0,0,0),user.getId());
        int weekToday = trainRecordRepository.countTrainRecordByTrainTimeBetweenAndUserId(DateUtil.GetThisWeekMonday().atTime(0,0,0),DateUtil.GetThisWeekSunday().atTime(0,0,0),user.getId());
        int count = (int) trainRecordRepository.countTrainRecordByUserId(user.getId());
        result.put("today",todayCount);
        result.put("week",weekToday);
        result.put("count",count);
        return result;

    }
}
