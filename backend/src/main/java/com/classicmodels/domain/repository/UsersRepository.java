package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.model.UsersRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    UserDetails findByEmail(String email);
//    Optional<Users> findByLogin(String login);

    @Query( value = "SELECT role from classicmodels_api.users WHERE email= :email", nativeQuery = true)
    UsersRole findRoleByEmail(String email);

}
