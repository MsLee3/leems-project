package com.project.MgShare.dto.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private long id;
    private String itemName;
    private int quantity;
    private String borrower;
}
