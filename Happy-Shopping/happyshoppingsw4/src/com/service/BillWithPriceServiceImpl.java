package com.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BillWithPriceDAO;
import com.model.BillWithPrice;

@Service
@Transactional
public class BillWithPriceServiceImpl implements BillWithPriceService{
	
	@Autowired
	BillWithPriceDAO billWithPriceDAO;
	public BillWithPriceDAO getBillWithPriceDAO() {
		return billWithPriceDAO;
	}
	public void setBillWithPriceDAO(BillWithPriceDAO billWithPriceDAO) {
		this.billWithPriceDAO = billWithPriceDAO;
	}


	@Override
	public boolean insertBillWithPrice(BillWithPrice billwithprice) {
		boolean result = billWithPriceDAO.insertBillWithPrice(billwithprice);
		return result;
	}
	
}
