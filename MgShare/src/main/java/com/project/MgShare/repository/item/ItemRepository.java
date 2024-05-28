package com.project.MgShare.repository.item;

import com.project.MgShare.model.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
