package com.example.sweater.controller;

import com.example.sweater.domain.Language;
import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import com.example.sweater.domain.UsersRepository;
import com.example.sweater.repos.FindMultipleStatistic;
import com.example.sweater.repos.LanguagesRepo;
import com.example.sweater.repos.StatisticRepo;
import com.example.sweater.repos.UsersRepositoryRepo;
import com.example.sweater.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * This for obtaining and adding information about users(main functionality of the program)
 */
@Controller
public class FindController {
    @Autowired
    private UsersRepositoryRepo usersRepositoryRepo;
    @Autowired
    private StatisticRepo statisticRepo;
    @Autowired
    private LanguagesRepo languagesRepo;
    @Autowired
    private FindMultipleStatistic findMultipleStatistic;
    @Autowired
    private StatisticService statisticService;

    /**
     * This user finds all "Statistic" objects that belong to current user and displays them
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/find")
    public String find(@AuthenticationPrincipal User user,
                       Model model){
        Iterable<Statistic> currentStatistics = findMultipleStatistic.findByAuthor(user);
        model.addAttribute("statistics", currentStatistics);
        return "listOfStatistics";
    }

    /**
     * This method determines repositories for which "Statistic" object user want to see and finds them using the OneToMany relation in the database, than displays them
     * @param statisticLogin
     * @param model
     * @return
     */
    @GetMapping("/find/repos/{statisticLogin}")
    public String findRepos(@PathVariable String statisticLogin,
                            Model model){
        Statistic currentSatistic = statisticRepo.findByLogin(statisticLogin);
        Iterable<UsersRepository> currentRepos = usersRepositoryRepo.findByStatistic(currentSatistic);
        model.addAttribute("repos", currentRepos);
        return "listOfRepositories";
    }

    /**
     * This method determines language statistic objects for which "Statistic" object user want to see and finds them using the OneToMany relation in the database, than displays them
     * @param statisticLogin
     * @param model
     * @return
     */
    @GetMapping("/find/languages/{statisticLogin}")
    public String findLanguages(@PathVariable String statisticLogin,
                                Model model){
        Statistic currentStatistic = statisticRepo.findByLogin(statisticLogin);
        Iterable<Language> currentLanguages = languagesRepo.findByStatistic(currentStatistic);
        model.addAttribute("languages", currentLanguages);
        return "listOfLanguages";
    }

    /**
     * This method obtain the information about the GitHub user by his login, creates one "Statistic" object , "Language" statistic objects which contain information
     * about programming languages that GitHub user uses, that are wired to "Statistic" objects using OneToMany relation, many "UsersRepository" object with information
     * about users repositories that are wired to the "Statistic" object using OneToMany relation , then it finds the created "Statistic" object and puts to the view.
     * @param login
     * @param user
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping("/findNew")
    public String findByLogin(@RequestParam String login,
                              @AuthenticationPrincipal User user,
                              Model model) throws IOException {
        //Here i am using autowired service to perform the logic
        if (statisticRepo.findByLogin(login)==null) {
            statisticService.obtainStatistic(login, user);
        }
        Iterable<Statistic> currentStatistic = findMultipleStatistic.findByLogin(login);
        model.addAttribute("statistics",currentStatistic);
        return "listOfStatistics";
    }

}
