package next.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Question;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class QuestionDao {

	public void insert(Question question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
				question.getWriter(), 
				question.getTitle(), 
				question.getContents(),
				new Timestamp(question.getTimeFromCreateDate()), 
				question.getCountOfComment());
	}
	
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS "
				+ "order by questionId desc";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
			}
			
		};
		
		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
			}
			
		};
		
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
	
	public void updateCount(long questionId, int count) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE QUESTIONS SET countOfComment = countOfComment + ? WHERE questionId = ?";
		jdbcTemplate.update(sql, count, questionId); //count와 questionId 순서를 바꿔서 문제 해결!
	}
	
	
	public void update(long questionId, String writer, String title, String contents) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "update QUESTIONS set writer=?, title=?, contents=? where questionId = ?";
		jdbcTemplate.update(sql, writer, title, contents, questionId);
	}

	public void delete(Long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "delete from QUESTIONS where questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}
}
