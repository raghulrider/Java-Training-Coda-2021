package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ItemDAO;
import com.model.Item;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemDAO itemDAO;
	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	
	@Override
	public List<Item> getAllItems() {
		List<Item> items = itemDAO.getAllItems();
		return items;
	}
	
	@Override
	public Item getItem(String itemId) {
		Item item = itemDAO.getItem(itemId);
		return item;
	}

	
}
