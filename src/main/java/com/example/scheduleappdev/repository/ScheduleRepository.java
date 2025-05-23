package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findScheduleByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("일정이 존재하지 않습니다. id = " + id));
    }

    List<Schedule> findAllByOrderByUpdatedDateTimeDesc(Pageable pageable);

}