package com.essensbestellung.csv;


import com.essensbestellung.entities.User;
import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.entities.Location;
import com.essensbestellung.repository.IGroupMembersRepository;
import com.essensbestellung.repository.IGroupRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.repository.IUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.List;


@Service
public class CsvImportService {

    private static final Logger logger = LoggerFactory.getLogger(CsvImportService.class);

    private final IUserRepository userRepository;
    private final CsvParser csvParser;
    private final ILocationRepository locationRepository;
    private final IGroupRepository groupRepository;
    private final IGroupMembersRepository groupMembersRepository;
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource; // Injecting DataSource to access connection pool

    @Autowired
    public CsvImportService(IUserRepository userRepository, ILocationRepository locationRepository,
                            IGroupRepository groupRepository, IGroupMembersRepository groupMembersRepository, CsvParser csvParser, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.csvParser = csvParser;
        this.groupRepository = groupRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.entityManager = entityManager;

    }


    @Transactional
    public void importUsersFromCsv(String filePath) {
        try {
            List<User> users = csvParser.parseCsvToUsers(filePath);

            for (User user : users) {

                try {
                    user.generateQRCode();
                    userRepository.save(user);
                    logger.info("Saved user to database: {}", user);
                } catch (Exception e) {
                    logger.error("Failed to save user: {}", user, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing users from CSV", e);
        }
    }


    public void importLocationsFromCsv(String filePath) {
        try {
            //Nutzt csv Parser um Liste mit Locationszu erhalten
            List<Location> locations = csvParser.parseCsvToLocations(filePath);

            for (Location location : locations) {
                try {
                    //Einfuegung in Datenbank 
                    locationRepository.save(location);
                    logger.info("Saved location to database: {}", location);
                } catch (Exception e) {
                    logger.error("Failed to save location: ", e);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to save location: ", e);
        }
    }

    public void importGroupsFromCsv(String filePath) {
        try {

            refreshDatabaseConnection();


            List<Group> groups = csvParser.parseCsvToGroups(filePath);
            for (Group group : groups) {
                try {
                    groupRepository.save(group);
                    logger.info("Saved group to database: {}", group);
                } catch (Exception e) {
                    logger.error("Failed to save group: {}", group, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing groups from CSV", e);
        }
    }
    public void importGroupMembersFromCsv(String filePath) {
        try {

            refreshDatabaseConnection();


            List<GroupMembers> members = csvParser.parseCsvGroupMembers(filePath);
            for (GroupMembers member : members) {
                try {
                    groupMembersRepository.save(member);
                    logger.info("Saved member to database: {}", member);
                } catch (Exception e) {
                    logger.error("Failed to save member: {}", member, e);
                }
            }

        } catch (IOException e) {
            logger.error("Error while parsing group-members from CSV", e);
        }
    }

//Sicherstellung das Datenbankverbindung neu erstellt wird
    public void refreshDatabaseConnection() {
        try {

            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                hikariDataSource.getHikariPoolMXBean().softEvictConnections();
                logger.info("Evicted idle connections in the pool.");
            }

            entityManager.clear();
            logger.info("Cleared EntityManager session.");

        } catch (Exception e) {
            logger.error("Error refreshing database connection", e);
        }
    }
}
