package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

}
