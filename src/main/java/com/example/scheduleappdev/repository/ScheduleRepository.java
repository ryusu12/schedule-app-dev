package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("일정이 존재하지 않습니다. id = " + id));
    }

}