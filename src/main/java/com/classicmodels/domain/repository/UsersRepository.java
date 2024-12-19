package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.model.UsersRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

//    UserDetails findByLogin(String login);
    Optional<Users> findByLogin(String login);

    @Query( value = "SELECT role from classicmodels_api.users WHERE login= :login", nativeQuery = true)
    UsersRole findRoleByLogin(String login);

}
