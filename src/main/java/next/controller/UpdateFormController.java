package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class UpdateFormController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		Question question = questionDao.findById(questionId);
		
		ModelAndView mav = jstlView("form.jsp");
		mav.addObject("question", question);
		return mav;
	}
	
}
