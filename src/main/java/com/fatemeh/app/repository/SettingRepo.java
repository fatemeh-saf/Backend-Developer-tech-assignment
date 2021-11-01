package com.fatemeh.app.repository;

import com.fatemeh.app.entity.SettingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface SettingRepo extends CrudRepository<SettingEntity, Long> {

    //query to find setting by string id
    @Query(value="select * from settings s where s.id=:setId",nativeQuery=true)
    SettingEntity findDatabaseSettingsByPublicId(@Param("setId") String setId);


}
