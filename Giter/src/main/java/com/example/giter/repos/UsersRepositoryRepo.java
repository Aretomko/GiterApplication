package com.example.sweater.repos;

import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import com.example.sweater.domain.UsersRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepositoryRepo extends CrudRepository<UsersRepository, Long> {
    List<UsersRepository> findByStatistic (Statistic statistic);
}
