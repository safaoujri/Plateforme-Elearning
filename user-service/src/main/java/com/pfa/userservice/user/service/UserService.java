package com.pfa.userservice.user.service;

import com.pfa.userservice.exception.UserNotFoundException;
import com.pfa.userservice.user.dto.request.UserRequest;
import com.pfa.userservice.user.dto.response.UserResponse;
import com.pfa.userservice.user.entity.User;
import com.pfa.userservice.user.mapper.UserMapper;
import com.pfa.userservice.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public Long createUser(@Valid UserRequest request) {
        var user = repository.save(mapper.toUser(request));
        return user.getId();
    }

    @Transactional
    public void updateUser(@Valid UserRequest request) {
        if (request.id() == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        var user = repository.findById(request.id())
                .orElseThrow(() -> new UserNotFoundException(
                        format("User with id '%s' not found", request.id())
                ));
        mergeUser(user, request);
        repository.save(user);
    }


    private void mergeUser(User user, @Valid UserRequest request) {
        if (StringUtils.isNotBlank(request.nom())) {
            user.setNom(request.nom());
        }
        if (StringUtils.isNotBlank(request.email())) {
            user.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.prenom())) {
            user.setPrenom(request.prenom());
        }
        if (request.role() != null) {
            user.setRole(request.role());
        }
    }

    public List<UserResponse> findAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromUser)
                .collect(Collectors.toList());
    }

    public Boolean existsById(Long id) {
        return repository.findById(id)
                .isPresent();
    }

    public UserResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::fromUser)
                .orElseThrow(() -> new UserNotFoundException(format("User with id '%s' not found", id)));
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
