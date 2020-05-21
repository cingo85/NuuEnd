package com.leadtek.nuu.controller;

//@Controller
public class LogsController {
	
//	@Autowired
//	LogsService logsService;
	
//	@GetMapping("/logs")
//	@PreAuthorize("hasAnyRole('logs')")
//	public String logs() {
//		return "/logs/logs";
//	}
//	
//	@PostMapping("/logs/find")
//	@PreAuthorize("hasAnyRole('logs')")
//	@ResponseBody Map<String, Object> find(BasicFilterForm form, ModelMap model) {
//		Pageable pageable = PageRequest.of( form.getPage(), form.getLength() );
//		Map<String, Object> json = new HashMap<String, Object>();	
//		Page<Logs> page = logsService.findAll(pageable);   
//		json.put("draw", form.getDraw()  ); 
//		json.put("recordsTotal", page.getTotalElements() ); 
//		json.put("recordsFiltered", page.getTotalElements() ); 
//		json.put("data", page.iterator() );
//		return json;  
//    }
}
