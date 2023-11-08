package com.decagon.karrigobe.repositories;

import com.decagon.karrigobe.entities.model.ItemDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDescriptionRepository extends JpaRepository<ItemDescriptionEntity, Long> {
}
