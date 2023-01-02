package com.supportportaljsc.repository;

import com.supportportaljsc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername  (String name);
    User findUserByEmail      (String email);


}
