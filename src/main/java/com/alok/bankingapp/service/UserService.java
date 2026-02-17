package com.alok.bankingapp.service;

import com.alok.bankingapp.dto.UserRequest;
import com.alok.bankingapp.dto.UserResponse;
import com.alok.bankingapp.entity.User;
import com.alok.bankingapp.exception.UserNotFoundException;
import com.alok.bankingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse create(UserRequest request) {
        User user = new User(request.getName(), request.getEmail());
        User saved = repository.save(user);
        return mapToResponse(saved);
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapToResponse(user);
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.updateName(request.getName());
        return mapToResponse(repository.save(user));
    }

    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(user);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
