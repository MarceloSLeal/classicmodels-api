package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<Users, String> {

    UserDetails findByLogin(String login);

}
