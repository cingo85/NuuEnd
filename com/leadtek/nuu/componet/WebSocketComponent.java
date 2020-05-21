package com.leadtek.nuu.componet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.leadtek.nuu.etlEntity.ETlMaster;
import com.leadtek.nuu.etlRepository.EtlMasterRepository;

@Component
public class WebSocketComponent {

	@Autowired
	EtlMasterRepository etlMasterRepository;
	
	@Autowired
    private SimpMessagingTemplate simpMessageSendingOperations;//訊息傳送模板

	@Async
	public void sendMessage(String count) {
        simpMessageSendingOperations.convertAndSend("/topic/ip", count);//將訊息推送給‘、topic/ip’的客戶端
	}
	
	@Scheduled(fixedRate = 1000 * 5)
	public void etlmaster() {
		
		List<ETlMaster> item = etlMasterRepository.findAll();
		
		List<Object> result = new ArrayList<Object>();
		
		for(ETlMaster temp : item) {
			Map<String,Object> re = new LinkedHashMap<>();
			re.put("tablename", temp.getTablename());
			re.put("status", temp.getStatus());
			re.put("count", temp.getTablecount());
			result.add(re);
		}
		
		String json = new Gson().toJson(item);
		
		 simpMessageSendingOperations.convertAndSend("/topic/db", json);//將訊息推送給‘、topic/ip’的客戶端
	}
}
