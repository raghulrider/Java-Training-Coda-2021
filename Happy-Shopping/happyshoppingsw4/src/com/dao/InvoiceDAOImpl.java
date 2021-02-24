package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Invoice;

@Repository
public class InvoiceDAOImpl extends InvoiceDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean setInvoice(Invoice invoice) {
		Session session = sessionFactory.getCurrentSession();
		session.save(invoice);
		return true;
	}
	
	@Override
	public List<Invoice> getInvoice(String billno) {
		List<Invoice> invoices = new ArrayList<>();
		Session session =  sessionFactory.getCurrentSession();
		Query query=session.createQuery("select itemid, quantity from invoice where billno=:b");
		query.setParameter("b", billno);
		@SuppressWarnings("unchecked")
		List<Object[]> list=query.list();
		for(Object[] i:list) {
			Invoice invoice = new Invoice();
			invoice.setBillno(billno);
			invoice.setItemid((String)i[0]);
			invoice.setQuantity((String)i[1]);
			invoices.add(invoice);
		}
		return invoices;
	}
}
