package com.casehistory.newsitempicker.app;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The entry point to the <b>newsitempicker</b> component.
 * 
 * @author Abhinav Tripathi
 */
public class NewsItemPicker {

	private static Logger logger = Logger.getLogger(NewsItemPicker.class);

	public static void main(String[] args) {
		logger.info("Starting the newsitempicker component ...");
		logger.info("Loading the application context ...");
		final ApplicationContext context = new ClassPathXmlApplicationContext("newsitempicker-context.xml");
		logger.info("Context loaded, beans initialized ...");
		new Thread((QueryListener) context.getBean("queryListener")).start();
		logger.info("queryListener started ...");
		logger.info("Component startup complete.");
	}

}
