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
public class RegisterController {
	Map <String,User> users=new HashMap<String,User>();
public RegisterController() {
	User u=new User("ashish","ashish","flyashish123@gmail.com");
	users.put("ashish", u);
}
@RequestMapping(value = "/users/register", method=RequestMethod.POST)
@ResponseBody
public String registerUser(@ModelAttribute("userId") String userId, @ModelAttribute("password") String password, @ModelAttribute("email") String email) {
	System.out.println("Values fetched while registration are: UserID ="+userId+"Password ="+password);
	
	User u= new User(userId, password, email);
	users.put(userId, u);
	System.out.println("Users Map details are"+users);
	return "<html><body>Registration Successful"+"<a href='http://localhost:5061/index.html'> home to Login</a>"+"</body></html>";
}

@RequestMapping(value = "/users/all", method=RequestMethod.GET)
@ResponseBody
public Map<String,User> getAllUsers()
{
	return users;
}

@RequestMapping(value = "users/{userId}", method=RequestMethod.GET)
@ResponseBody
public User getUser(@PathVariable("userId")String userId)
{
	return users.get(userId);
}

@RequestMapping(value = "users/login", method=RequestMethod.POST)
public String loginUser(@ModelAttribute("userId") String userId, @ModelAttribute("password") String password, HttpServletRequest request)
{
	System.out.println("Entered Login user");
	User uu=users.get(userId);
	System.out.println("userId while login is"+userId);
	request.getSession().setAttribute("userId", uu);
	if(users.get(userId)!=null)
	{
		if(users.get(userId).getPassword().equals(password))
		{
			return "Trade";
		}
		else
			
		{
			return "PasswordError";
		}
	}
	else
	return "Redirect";
}
}
