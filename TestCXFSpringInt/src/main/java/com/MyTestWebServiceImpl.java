package com;

import javax.annotation.PostConstruct;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//@Component
@WebService(endpointInterface = "com.IMyTestWebService" )
public class MyTestWebServiceImpl implements IMyTestWebService{

	@PostConstruct
	public void init() {
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Autowired
	private MyComponent myComponent;
	
	@Override
	public void sayHello() {
		myComponent.test();
	}

}
