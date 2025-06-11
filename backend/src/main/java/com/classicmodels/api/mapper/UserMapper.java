package com.classicmodels.api.mapper;

import com.classicmodels.api.model.UserRepModel;
import com.classicmodels.api.model.input.UsersInput;
import com.classicmodels.domain.model.Users;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {

    private ModelMapper modelMapper;

    public UserRepModel toModel(Users users) {
        return modelMapper.map(users, UserRepModel.class);
    }

    public List<UserRepModel> toCollectionModel(List<Users> users) {
        return users.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Users toEntity(UsersInput usersInput) {
        return modelMapper.map(usersInput, Users.class);
    }

}
