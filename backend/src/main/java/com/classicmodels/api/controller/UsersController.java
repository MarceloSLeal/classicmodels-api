package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.UserMapper;
import com.classicmodels.api.model.UserRepModel;
import com.classicmodels.domain.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersRepository usersRepository;
    private UserMapper userMapper;

    @GetMapping
    public List<UserRepModel> listar() {

        return userMapper.toCollectionModel(usersRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {

        if (!usersRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usersRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
