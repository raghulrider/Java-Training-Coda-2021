package utility;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity(name="billwithprice")
@Table(name="billwithprice")
public class BillWithPrice {
	@Id
	private String billno;
	@Type(type = "serializable")
	private List<Items> items;
	
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
}
