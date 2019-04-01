package com.example.sweater.repos;

import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FindMultipleStatistic extends CrudRepository<Statistic, Long> {
    List<Statistic> findByAuthor (User user);
    List<Statistic> findByLogin (String login);
}
