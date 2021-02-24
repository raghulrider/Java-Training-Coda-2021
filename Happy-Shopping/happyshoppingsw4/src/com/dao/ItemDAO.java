package com.dao;

import java.util.List;

import com.model.Item;

public abstract class ItemDAO {
	public abstract Item getItem(String itemId);
	public abstract List<Item> getAllItems();
}
