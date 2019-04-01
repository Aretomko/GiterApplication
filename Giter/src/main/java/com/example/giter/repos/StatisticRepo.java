package com.example.sweater.repos;

import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticRepo extends CrudRepository<Statistic, Long> {
    Statistic findByAuthor (User user);
    Statistic findByLogin (String login);
}
