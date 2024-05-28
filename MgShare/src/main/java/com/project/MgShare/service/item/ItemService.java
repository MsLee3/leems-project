package com.project.MgShare.service.item;

import com.project.MgShare.dto.item.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO addItem(ItemDTO itemDTO);
    ItemDTO updateItem(ItemDTO itemDTO);
    void deleteItem(long id);
    List<ItemDTO> getAllItems();
    ItemDTO getItemById(long id);
}
