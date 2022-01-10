package com.sathyatech.app.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sathyatech.app.model.Item;
import com.sathyatech.app.service.IItemService;
import com.sathyatech.app.util.ItemUtil;

@Component
public class ItemValidator implements Validator {

	private boolean isEdit;

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Autowired
	private ItemUtil itemUtil;

	@Autowired
	private IItemService itemService;


	@Override
	public boolean supports(Class<?> clazz) {
		return Item.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Item item = (Item)target;
		if(!Pattern.compile("[a-zA-Z \\t\\n\\x0B\\f\\r]{4,12}").matcher(item.getItemCode()).matches()){
			errors.rejectValue("itemCode","","Enter 4-12 Chars Only!");
		}

		if(item.getItemWdth()==null || item.getItemWdth()<=0) {
			errors.rejectValue("itemWdth", "", "Enter Positive Number!");
		}
		if(item.getItemLnth()==null || item.getItemLnth()<=0){
			errors.rejectValue("itemLnth", "", "Enter Positive Number!");
		}
		if(item.getItemHgth()==null || item.getItemHgth()<=0) {
			errors.rejectValue("itemHgth", "", "Enter Positive Number!");
		}
		if(item.getBaseCost() ==null || item.getBaseCost()<=0){
			errors.rejectValue("baseCost", "", "Enter Base Cost For Item");
		}
		if(!itemUtil.getBaseCurrencies().contains(item.getBaseCurrency())){
			errors.rejectValue("baseCurrency","","Select Base Currency");
		}
		if(item.getUom() == null || StringUtils.isEmpty(item.getUom().getUomModel())){
			errors.rejectValue("uom","","Choose one UOM");
		}
		if(item.getOmSale() == null || StringUtils.isEmpty(item.getOmSale().getOrderCode())){
			errors.rejectValue("omSale","","Choose one Sale Type");
		}
		if(item.getOmPurchase() == null || StringUtils.isEmpty(item.getOmPurchase().getOrderCode())){
			errors.rejectValue("omPurchase","","Choose one Sale Type");
		}
		if (item.getWhVendors()==null || item.getWhVendors().isEmpty()){
			errors.rejectValue ("whVendors","","Choose atleast one Vendor");
		}
		if (item.getWhCustomers()== null || item.getWhCustomers().isEmpty()){
			errors.rejectValue ("whCustomers","","Choose atleast one Customer");
		}
		if(!Pattern.compile("[a-zA-Z \\t\\n\\x0B\\f\\r]{4,255}").matcher(item.getDescription()).matches()) {
			errors.rejectValue("description", "", "Chars only range of 10-255 accepted");
		}		
	}
}
