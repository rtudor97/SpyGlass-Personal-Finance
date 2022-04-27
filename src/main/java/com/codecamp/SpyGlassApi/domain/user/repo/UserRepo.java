package com.codecamp.SpyGlassApi.domain.user.repo;

import com.codecamp.SpyGlassApi.domain.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}
