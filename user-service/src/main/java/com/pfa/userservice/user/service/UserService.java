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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private static final String PHOTO_DIRECTORY = "C:/Users/user/Documents";


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

    private static final String UPLOAD_DIR = "uploads/";

    public void saveUserPhoto(Long userId, MultipartFile file) throws IOException {
        File uploadDir = new File(PHOTO_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = userId + "_photo" + getExtension(file.getOriginalFilename());
        Path filePath = Path.of(PHOTO_DIRECTORY, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public String getUserPhotoPath(Long userId) {
        return Path.of(PHOTO_DIRECTORY, userId + "_photo.jpg").toString();  // Assurez-vous que l'extension correspond
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
