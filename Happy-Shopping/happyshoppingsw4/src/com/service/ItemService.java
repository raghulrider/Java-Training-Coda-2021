package com.service;

import java.util.List;

import com.model.Item;

public interface ItemService {
	public List<Item> getAllItems();
	public Item getItem(String itemId);
}
