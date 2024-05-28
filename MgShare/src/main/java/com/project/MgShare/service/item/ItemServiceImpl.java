package com.project.MgShare.service.item;

import com.project.MgShare.dto.item.ItemDTO;
import com.project.MgShare.model.item.ItemEntity;
import com.project.MgShare.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        ItemEntity itemEntity = toEntity(itemDTO);
        itemRepository.save(itemEntity);
        return toDTO(itemEntity);
    }

    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {
        ItemEntity itemEntity = toEntity(itemDTO);
        itemRepository.save(itemEntity);
        return toDTO(itemEntity);
    }

    @Override
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ItemDTO getItemById(long id) {
        return itemRepository.findById(id).map(this::toDTO).orElse(null);
    }

    private ItemEntity toEntity(ItemDTO itemDTO) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemDTO.getId());
        itemEntity.setItemName(itemDTO.getItemName());
        itemEntity.setQuantity(itemDTO.getQuantity());
        itemEntity.setBorrower(itemDTO.getBorrower());
        return itemEntity;
    }

    private ItemDTO toDTO(ItemEntity itemEntity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(itemEntity.getId());
        itemDTO.setItemName(itemEntity.getItemName());
        itemDTO.setQuantity(itemEntity.getQuantity());
        itemDTO.setBorrower(itemEntity.getBorrower());
        return itemDTO;
    }
}
