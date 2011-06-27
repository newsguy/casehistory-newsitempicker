/**
 * 
 */
package com.casehistory.newsitempicker.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.casehistory.newsitempicker.search.Fetcher;

/**
 * Listens for queries from the web front-end, and initiates the retrieval of
 * web pages from the Nutch indexes. The queries have the following structure: {
 * userId:"[a-zA-Z0-9]+",query:"..." } </p>
 * 
 * <p>
 * After it fetches the list of web pages for certain query string from certain
 * user, it sends them over to the <b>Intelligence</b> component. The structure
 * of messages that it sends to the <b>Intelligence</b> component is: {
 * userId:"...", query:"...", pageurl:"http://...", pagetitle:"...",
 * pagecontent:"..." }
 * </p>
 * 
 * @author Abhinav Tripathi
 */
public class QueryListener implements MessageListener, Runnable {

	private static final Logger logger = Logger.getLogger(QueryListener.class);
	protected JmsTemplate producerTemplate;
	private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();

	@Override
	public void run() {
		while (true) {
			producerTemplate.send(new MessageCreatorImpl());
		}
	}

	public void onMessage(Message message) {
		NewsItemsPayload payload = null;
		try {
			TextMessage msg = (TextMessage) message;
			logger.info("Recieved query: " + msg.getText());
			logger.info("Fetching pages from the Nutch indexes ...");
			payload = new NewsItemsPayload(msg.getText());
			payload.setPages(Fetcher.getPages(payload.getQuery()));
			messageQueue.addAll(payload.getAsMessages());
			logger.info("Pages fetched for given query, now delivering to Intelligence ...");
		} catch (JMSException e) {
			logger.error("An error occured while recieving messages!");
			logger.error(e.getMessage());
		}
	}

	class MessageCreatorImpl implements MessageCreator {

		public Message createMessage(Session session) throws JMSException {
			TextMessage message = null;
			try {
				message = session.createTextMessage(messageQueue.take());
			} catch (InterruptedException e) {
				logger.error("Failed to fetch from the message queue!");
				logger.error(e.getMessage());
			}

			return message;
		}

	}

	public void setProducerTemplate(JmsTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

}
