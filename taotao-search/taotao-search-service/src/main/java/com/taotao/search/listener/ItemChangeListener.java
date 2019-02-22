package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.SearchItemService;

/**
 * 同步索引库Listener
 * @author 10309
 *
 */
public class ItemChangeListener implements MessageListener{
	@Autowired 
	private SearchItemService searchItemService;

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				//取消息内容
				String text = textMessage.getText();
				long itemId = Long.parseLong(text);
				Thread.sleep(500);
				searchItemService.importItem2IndexById(itemId);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
