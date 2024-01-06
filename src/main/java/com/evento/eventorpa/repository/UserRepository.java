package com.evento.eventorpa.repository;

import com.evento.eventorpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByContentid(int contentid);
    List<User> findByUseridOrderByIdDesc(String userid);


}
