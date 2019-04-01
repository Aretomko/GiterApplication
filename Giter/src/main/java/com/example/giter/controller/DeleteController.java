package com.example.sweater.controller;

import com.example.sweater.domain.Language;
import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import com.example.sweater.domain.UsersRepository;
import com.example.sweater.repos.FindMultipleStatistic;
import com.example.sweater.repos.LanguagesRepo;
import com.example.sweater.repos.StatisticRepo;
import com.example.sweater.repos.UsersRepositoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
Processing request for deleting of all statistic related to the target from the db and displaying "Statistic" objects that left.
 */
@Controller
public class DeleteController {
    @Autowired
    private StatisticRepo statisticRepo;
    @Autowired
    private UsersRepositoryRepo usersRepositoryRepo;
    @Autowired
    private LanguagesRepo languagesRepo;
    @Autowired
    private FindMultipleStatistic findMultipleStatistic;
    @GetMapping("/delete/{statisticLogin}")
    /**
    Extracting the login of GitHub user that should be deleted from the request, than obtaining the user of the application, to show as the response set of "Statistic" objects
    that belong to user that performing the request.
     */
    public String deleteStatistic(@PathVariable String statisticLogin,
                                  @AuthenticationPrincipal User user,
                                  Model model){
        //Finding the set of statistic that user want to delete;
        Statistic currentStatistic = statisticRepo.findByLogin(statisticLogin);
        //Finding all repositories ("UsersRepository" Objects) that belong to Statistic object that we want to delete
        Iterable<UsersRepository> currentRepositories = usersRepositoryRepo.findByStatistic(currentStatistic);
        //Deleting all repositories ("UsersRepository" Objects) that belong to Statistic object that we want to delete
        usersRepositoryRepo.deleteAll(currentRepositories);
        //Finding all languages statistic ("Language" Objects) that belong to Statistic object that we want to delete
        Iterable<Language> currentLanguages = languagesRepo.findByStatistic(currentStatistic);
        //Deleting all languages statistic ("Language" Objects) that belong to Statistic object that we want to delete
        languagesRepo.deleteAll(currentLanguages);
        //Deleting the "Statistic" Object;
        statisticRepo.delete(currentStatistic);
        /*
        If user will delete the last "Statistic" which belongs to him, the model will put null value into the freemarker migration, it will cause freemarker error,
        so if the last "Statistic" object will be deleted - controller will redirect the user to the default main page.
         */
        if (findMultipleStatistic.findByAuthor(user) != null) {
            //Finding all "Statistic" records that left and belongs to current user
            Iterable<Statistic> statistics = findMultipleStatistic.findByAuthor(user);
            //Displaying the "Statistic" objects
            model.addAttribute("statistics", statistics);
            return "listOfStatistics";
        }else {
            //Redirecting to the main page
            return "redirect:/";
        }
    }
}
