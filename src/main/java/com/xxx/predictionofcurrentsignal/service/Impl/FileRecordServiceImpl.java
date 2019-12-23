package com.xxx.predictionofcurrentsignal.service.Impl;

import com.xxx.predictionofcurrentsignal.dao.entity.FileRecord;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.FileRecordRepository;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import com.xxx.predictionofcurrentsignal.service.FileRecordService;
import com.xxx.predictionofcurrentsignal.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileRecordServiceImpl implements FileRecordService {

    private static final String FOLDER="D:\\train_data";

    @Autowired
    private FileRecordRepository fileRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveFileRecord(MultipartFile file, Authentication authentication, String uid,String type) throws IOException {
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(String.valueOf(file.getSize()));
        LocalDateTime localDateTime = LocalDateTime.now();
        /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String time = localDateTime.format(dateTimeFormatter);*/
        String name = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()+"."+StringUtils.substringAfterLast(file.getOriginalFilename(),".");
        log.info(name);
        File file1 = new File(FOLDER,name);
        file.transferTo(file1);
        User user = userRepository.findUserByName((String)authentication.getPrincipal());
        FileRecord fileRecord = new FileRecord(0,user.getId(),file.getOriginalFilename(),String.valueOf(file.getSize()),name,LocalDateTime.now(),uid,type,null);
        fileRecordRepository.save(fileRecord);
    }

    @Override
    public void findFileRecord(String[] selectedItems) {
        fileRecordRepository.findDistinctByUidIn(selectedItems);
    }

    @Override
    public Map<String,Object> getAllFileByPage(String fileName,String start,String end,Integer pageSize, Integer page) {

        Map<String,Object> result = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        Pageable pageable =  PageRequest.of(page-1,pageSize);
        Specification s = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotBlank(fileName)){
                    list.add(criteriaBuilder.like(root.get("fileName"),"%"+fileName+"%"));
                }
                if (StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(end)){
                    list.add(criteriaBuilder.between(root.get("uploadTime"),LocalDateTime.parse(start,dateTimeFormatter),LocalDateTime.parse(end,dateTimeFormatter)));
                }
                return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };
        Page<FileRecord> fileRecords = fileRecordRepository.findAll(s,pageable);
        long total = fileRecords.getTotalElements();
        List<FileRecord> files = fileRecords.getContent();
        result.put("fileTotal",total);
        result.put("fileRecord",files);
        return result;
    }

    @Override
    @Transactional
    public void deleteFileByFileName(String fileNameSystem) {
        fileRecordRepository.deleteFileRecordByFileNameSystem(fileNameSystem);
    }

    @Override
    public Map<String, Object> getAllFileByName(String fileName, String start, String end, Integer pageSize, Integer page, String username) {

        Map<String,Object> result = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        Pageable pageable =  PageRequest.of(page-1,pageSize);
        User user = userRepository.findUserByName(username);
        Specification s = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if(StringUtils.isNotBlank(fileName)){
                    list.add(criteriaBuilder.like(root.get("fileName"),"%"+fileName+"%"));
                }
                if (StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(end)){
                    list.add(criteriaBuilder.between(root.get("uploadTime"),LocalDateTime.parse(start,dateTimeFormatter),LocalDateTime.parse(end,dateTimeFormatter)));
                }
                list.add(criteriaBuilder.equal(root.get("userId"),user.getId()));
                return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
            }
        };
        Page<FileRecord> fileRecords = fileRecordRepository.findAll(s,pageable);
        long total = fileRecords.getTotalElements();
        List<FileRecord> files = fileRecords.getContent();
        result.put("fileTotal",total);
        result.put("fileRecord",files);
        return result;
    }

    @Override
    public Map<String,Integer> getFileCountByName(String username) {
        Map<String,Integer> result = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate tom = today.plusDays(1);
        User user = userRepository.findUserByName(username);
        int todayCount = fileRecordRepository.countFileRecordByUploadTimeBetweenAndUserId(today.atTime(0,0,0),tom.atTime(0,0,0),user.getId());
        int weekToday = fileRecordRepository.countFileRecordByUploadTimeBetweenAndUserId(DateUtil.GetThisWeekMonday().atTime(0,0,0),DateUtil.GetThisWeekSunday().atTime(0,0,0),user.getId());
        int count = (int) fileRecordRepository.countFileRecordByUserId(user.getId());
        result.put("today",todayCount);
        result.put("week",weekToday);
        result.put("count",count);
        return result;
    }
}
