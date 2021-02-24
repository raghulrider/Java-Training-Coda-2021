package com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Item;

@Repository
public class ItemDAOImpl extends ItemDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Item> getAllItems() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Item.class);
		@SuppressWarnings("unchecked")
		List<Item> items = criteria.list();
		return items;
	}
	@Override
	public Item getItem(String itemId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Item.class);
		criteria.add(Property.forName("itemid").eq(itemId));
		Item item = (Item) criteria.uniqueResult();
		return item;
	}
}
