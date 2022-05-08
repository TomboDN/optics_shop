package com.mirea.optics_shop.repository;

import com.mirea.optics_shop.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByCode(String code);
}
