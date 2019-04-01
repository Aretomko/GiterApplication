package com.example.sweater.repos;

import com.example.sweater.domain.Language;
import com.example.sweater.domain.Statistic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguagesRepo extends CrudRepository<Language, Long> {
    List<Language> findByStatistic (Statistic statistic);
}
