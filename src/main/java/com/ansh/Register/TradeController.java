package com.ansh.Register;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TradeController {
	User user=new User();
	Map<String, Double> companies=new  HashMap<String, Double>();
	Map<String, Trade> trade=new  HashMap<String, Trade>();
	public TradeController(){
		
		companies.put("RELIANCE", 298.77);
		companies.put("AIRTEL", 134.77);
		companies.put("VODAFONE", 89.77);
		companies.put("IDEA", 24.77);
	}
	
	@RequestMapping(value = "/trade/do", method=RequestMethod.GET)
	@ResponseBody
	public String tradeDo(@ModelAttribute("ticker") String ticker, @ModelAttribute("qty") int qty,HttpServletRequest request)
	{
		System.out.println("Values fetched from Trade html are: Ticker="+ticker+" qty ="+qty);
		Double price=companies.get(ticker);
		System.out.println("Price value is ="+price);
		Trade t=new Trade(ticker, price, qty);
		double total=price*qty;
		trade.put(ticker, t);
		user=(User) request.getSession().getAttribute("userId");
		double bal=user.getBalance();
		if(bal<total)
		{
			return "error"+new RuntimeException("SORRY !!YOUR BALANCE IS TOO LOW");
		}
		else
		{
			bal=user.getBalance()-total;
			user.setBalance(bal);
			t.setTotalCost(total);
		}
		return "<html><body>Traded Successfully<br>"+user.getUserId()+" Your Balance is"+user.getBalance();
	}
	
	@RequestMapping(value="/trade/all", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Trade> getAllRegisteredUsers()
	{
		return trade;
	}
	
	@RequestMapping(value="/trade/{ticker}", method=RequestMethod.GET)
	@ResponseBody
	public Trade getUser(@PathVariable("ticker")String ticker) {
	return trade.get(ticker);	
	}

}
