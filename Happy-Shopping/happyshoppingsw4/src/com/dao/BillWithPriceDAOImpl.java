package com.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.BillWithPrice;


@Repository
public class BillWithPriceDAOImpl extends BillWithPriceDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insertBillWithPrice(BillWithPrice billwithprice) {
		Session session = sessionFactory.getCurrentSession();
		session.save(billwithprice);
		return true;
	}
	
	@Override
	public BillWithPrice getBillWithPrice(String billno) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BillWithPrice.class);
		criteria.add(Property.forName("billno").eq(billno));
		BillWithPrice bwp =(BillWithPrice) criteria.uniqueResult();
		return bwp;
	}
}
