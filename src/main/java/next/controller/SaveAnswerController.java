package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class SaveAnswerController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		
		Answer an = new Answer(writer, contents, questionId);
		answerDao.insert(an);
		questionDao.updateCount(questionId, 1);
		
		ModelAndView mav = jstlView("redirect:/list.next");
		return mav;
	}
	
}
