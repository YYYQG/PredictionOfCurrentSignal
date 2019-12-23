package com.xxx.predictionofcurrentsignal.dao.repository;

import com.xxx.predictionofcurrentsignal.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User findUserByTel(String tel);
    public User findUserByName(String name);
    public User findUserByAccount(String account);
    @Modifying
    @Query("update User  set tel = :tel where id = :id")
    public void bindTel(@Param(value = "tel") String tel,@Param(value = "id") Integer id);

}
