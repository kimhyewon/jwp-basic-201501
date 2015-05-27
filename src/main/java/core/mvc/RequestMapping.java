package core.mvc;

import java.util.HashMap;
import java.util.Map;

import next.controller.ApiDeleteQuestionController;
import next.controller.ApiListController;
import next.controller.DeleteAnswerController;
import next.controller.DeleteQuestionController;
import next.controller.ListController;
import next.controller.SaveAnswerController;
import next.controller.SaveController;
import next.controller.ShowController;
import next.controller.UpdateController;
import next.controller.UpdateFormController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private Map<String, Controller> mappings = new HashMap<String, Controller>();
	
	public void initMapping() {
		mappings.put("/list.next", new ListController());
		mappings.put("/show.next", new ShowController());
		mappings.put("/form.next", new ForwardController("form.jsp"));
		mappings.put("/save.next", new SaveController());
		mappings.put("/api/addanswer.next", new SaveAnswerController());
		mappings.put("/api/deleteanswer.next", new DeleteAnswerController());
		mappings.put("/api/list.next", new ApiListController());
		mappings.put("/updateForm.next", new UpdateFormController());
		mappings.put("/update.next", new UpdateController());
		mappings.put("/delete.next", new DeleteQuestionController());
		mappings.put("/api/delete.next", new ApiDeleteQuestionController());
		
		
		logger.info("Initialized Request Mapping!");
	}

	public Controller findController(String url) {
		return mappings.get(url);
	}

	void put(String url, Controller controller) {
		mappings.put(url, controller);
	}

}
