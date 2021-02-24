package com.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.Item;
import com.service.ItemService;

@Controller
public class ShopController {
	
	@Autowired
	ItemService itemService;
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}


	@RequestMapping(value="/shop", method = RequestMethod.POST)
	public ModelAndView addSelectedItems(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		Enumeration<String> en = request.getParameterNames();
		System.out.println(en.nextElement());
		Map<Item, String> items = new HashMap<>();
		
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			String value  = request.getParameter(key);
			if(!value.equals("0")) {
				Item item = itemService.getItem(key);
				System.out.println("Item ID : "+item+" - Item QTY : "+value);
				items.put(item, value);
			}
		}
		session.setAttribute("selectedItems", items);
		ModelAndView view = new ModelAndView();
		view.setViewName("customerentry");
		return view;
	}
}
