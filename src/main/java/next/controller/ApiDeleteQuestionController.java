package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class ApiDeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		Question qu = questionDao.findById(questionId);
		String questionWriter = qu.getWriter();
		
		String errorMessage = "질문 삭제를 할 수 없습니다.";
		String successMessage = "질문 삭제를 완료 했습니다.";
		
		ModelAndView mav = jsonView();
		
		if(!delete(questionId, questionWriter, answerDao, questionDao)) {
			mav.addObject("result", Result.fail(errorMessage));
		}
		else {
			mav.addObject("result", Result.fail(successMessage));
		}
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
