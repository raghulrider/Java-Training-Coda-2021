package utility;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

public class BillWithPriceDAO {
	public static final List<Items> getBillWithPrice(String billno){
		Session session = HibernateUtility.getSession();
		Criteria criteria = session.createCriteria(BillWithPrice.class);
		criteria.add(Property.forName("billno").eq(billno));
		BillWithPrice billwithprice = (BillWithPrice) criteria.uniqueResult();
		List<Items> items = billwithprice.getItems();
		HibernateUtility.closeSession(null);
		return items;
	}
	public static final boolean insertBillWithPrice(BillWithPrice billwithprice){
		Session session = HibernateUtility.getSession();
		session.save(billwithprice);
		HibernateUtility.closeSession(null);
		return false;
	}
}
