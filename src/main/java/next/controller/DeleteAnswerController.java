package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteAnswerController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long answerId = ServletRequestUtils.getRequiredLongParameter(request, "answerId");
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		
		answerDao.delete(answerId);
		questionDao.updateCount(questionId, -1);
		
		ModelAndView mav = jstlView("redirect:/list.next");
		return mav;
	}
}
