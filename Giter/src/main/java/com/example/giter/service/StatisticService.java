package com.example.sweater.service;

import com.example.sweater.domain.Language;
import com.example.sweater.domain.Statistic;
import com.example.sweater.domain.User;
import com.example.sweater.domain.UsersRepository;
import com.example.sweater.repos.FindMultipleStatistic;
import com.example.sweater.repos.LanguagesRepo;
import com.example.sweater.repos.StatisticRepo;
import com.example.sweater.repos.UsersRepositoryRepo;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This service performs the logic of obtaining and saving the GitHub users information using open GitHub API , which was added by maven dependency and autowired JPA repositories
 */
@Service
public class StatisticService {
    //Here i am autowiring JPARepositories to save obtained data to the database
    @Autowired
    private StatisticRepo statisticRepo;
    @Autowired
    private LanguagesRepo languagesRepo;
    @Autowired
    private UsersRepositoryRepo usersRepositoryRepo;
    @Autowired
    private FindMultipleStatistic findMultipleStatistic;

    /**
     * This method uses public GitHub API to obtain the information about the GitHub user by his login, creates one "Statistic" object , "Language" statistic objects which contain information
     * about programming languages that GitHub user uses, that are wired to "Statistic" objects using OneToMany relation, many "UsersRepository" object with information
     * about users repositories that are wired to the "Statistic" object using OneToMany relation
     * @param login
     * @param user
     * @throws IOException
     */
    public void obtainStatistic(String login, User user ) throws IOException {
            GitHub gitHub = GitHub.connect();
            GHUser gitUser = null;
            //If there is no such user we stop the performance
            try {
                gitUser = gitHub.getUser(login);
            }catch (GHFileNotFoundException e){
                return;
            }
            Map<String, GHRepository> ghRepositories = gitUser.getRepositories();
            //This Set is for storing names of languages that GitHub user used
            Set<String> languages = new HashSet<>();
            /*
            This List is for storing the quantity of uses of each language (the first element in "languages" belongs to the first element in "numberOfTimesLangugeOcurred",
            second element in "languages" belongs to the second element in "numberOfTimesLangugeOcurred", and so on
             */
            ArrayList<Integer> numberOfTimesLangugeOcurred = new ArrayList<>();
            //Iterating through HitHub users repositories , extracting language and for "UsersRepository" objects and counting the number of uses for each language
            for (GHRepository iterator : ghRepositories.values()) {
                if (languages.contains(iterator.getLanguage())) {
                    int indexCounter = 0;
                    for (String languageIterator : languages){
                        if (languageIterator.equals(iterator.getLanguage())){
                            break;
                        } else {
                            indexCounter++;
                        }
                    }
                    if (languages.size()>0) {
                        int num = numberOfTimesLangugeOcurred.get(indexCounter);
                        numberOfTimesLangugeOcurred.remove(indexCounter);
                        numberOfTimesLangugeOcurred.add(indexCounter, ++num);
                    }else {
                        if (iterator.getLanguage() == null) {
                            languages.add("Not specified");
                            numberOfTimesLangugeOcurred.add(1);
                        }
                        else{
                            languages.add(iterator.getLanguage());
                            numberOfTimesLangugeOcurred.add(1);
                        }
                    }

                } else if (iterator.getLanguage() == null){
                    languages.add("Not specified");
                    numberOfTimesLangugeOcurred.add(1);
                }else {
                    languages.add(iterator.getLanguage());
                    numberOfTimesLangugeOcurred.add(1);
                }
            }
            //Constructing the "Statistic" object using obtained data
            Statistic currentStatistic = new Statistic(languages.size(), ghRepositories.size(), gitUser.getEmail(), login, user);
            //Storing "Statistic" object in the database
            statisticRepo.save(currentStatistic);
            //Constructing and saving "Language" objects using the obtained data, wiring them the "Statistic" object usin OneToMany relation
            int i = 0;
            for (String languageIterator : languages) {
                while (i<languages.size()) {
                    Language currentLanguage = new Language(languageIterator, numberOfTimesLangugeOcurred.get(i), currentStatistic);
                    languagesRepo.save(currentLanguage);
                    i++;
                    break;
                }
            }
            //Constructing and saving "UsersRepository" objects using the obtained data, wiring them the "Statistic" object usin OneToMany relation
            for (GHRepository iterator : ghRepositories.values()) {
                if (iterator.getLanguage() == null) {
                    UsersRepository currentUsersRepository = new UsersRepository(iterator.getName(), ".txt file", currentStatistic);
                    usersRepositoryRepo.save(currentUsersRepository);
                }else {
                    UsersRepository currentUsersRepository = new UsersRepository(iterator.getName(), iterator.getLanguage(), currentStatistic);
                    usersRepositoryRepo.save(currentUsersRepository);
                }
            }
    }
}
