package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
//		String questionWriter = ServletRequestUtils.getRequiredStringParameter(request, "questionWriter");
		Question qu = questionDao.findById(questionId);
		String questionWriter = qu.getWriter();

		ModelAndView mav;
		
		if(!delete(questionId, questionWriter, answerDao, questionDao)) {
			mav = jstlView("redirect:/show.next?questionId="+questionId);
		}
		else mav = jstlView("redirect:/list.next");
		

		return mav;
	}
	
	boolean delete(Long questionId, String questionWriter, AnswerDao answerDao, QuestionDao questionDao) {
		Question question = questionDao.findById(questionId);
		if(!question.deleteOk(question, questionWriter, answerDao))
			return false;
		questionDao.delete(questionId);
		
		return true;
	}
}
