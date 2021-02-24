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

import com.model.InvoiceMaster;

@Repository
public class InvoiceMasterDAOImpl extends InvoiceMasterDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<String> getAllBillNos() {
		Session session =  sessionFactory.getCurrentSession();
		Query query = session.createQuery("select billno from invoicemaster");
		List<String> bills = query.list();
		return bills;
	}
	
	@Override
	public boolean insertInvoiceMaster(InvoiceMaster invoiceMaster) {
		Session session = sessionFactory.getCurrentSession();
		session.save(invoiceMaster);
		return true;
	}
	
	@Override
	public InvoiceMaster fetchInvoiceMaster(String billno) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InvoiceMaster.class);
		criteria.add(Property.forName("billno").eq(billno));
		InvoiceMaster invoiceMaster = (InvoiceMaster)criteria.uniqueResult();
		return invoiceMaster;
	}
	
	@Override
	public List<InvoiceMaster> getAllInvoices() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InvoiceMaster.class);
		List<InvoiceMaster> invoices = criteria.list();
		return invoices;
	}
	
	@Override
	public List<InvoiceMaster> getInvoiceByBillno(String billno) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(InvoiceMaster.class);
		criteria.add(Property.forName("billno").eq(billno));
		InvoiceMaster minvoice = (InvoiceMaster) criteria.uniqueResult();
		List<InvoiceMaster> minvoices = new ArrayList<>();
		minvoices.add(minvoice);
		System.out.println("Length : "+minvoices.size());
		return minvoices;
	}
	
	@Override
	public List<InvoiceMaster> getInvoiceByCustomerId(String customerId) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(InvoiceMaster.class);
		criteria.add(Property.forName("customerid").eq(customerId));
		@SuppressWarnings("unchecked")
		List<InvoiceMaster> minvoices = criteria.list();
		minvoices.forEach(invoice->{
			System.out.println(invoice.getBillno());
		});
		return minvoices;
	}
	@Override
	public List<InvoiceMaster> getInvoiceByDate(String billdate) {
		Session session= sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(InvoiceMaster.class);
		criteria.add(Property.forName("billdate").eq(billdate));
		@SuppressWarnings("unchecked")
		List<InvoiceMaster> minvoices = criteria.list();
		minvoices.forEach(invoice->{
			System.out.println(invoice.getBillno());
		});
		return minvoices;
	}
}
