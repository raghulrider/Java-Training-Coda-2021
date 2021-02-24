package com.dao;



import com.model.BillWithPrice;


public abstract class BillWithPriceDAO {

	public abstract boolean insertBillWithPrice(BillWithPrice billwithprice);

	public abstract BillWithPrice getBillWithPrice(String billno);

}
