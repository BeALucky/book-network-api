package com.projects.book.feedback;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.projects.book.book.Book;

@Service
public class FeedbackMapper {
	public Feedback toFeedback(FeedbackRequest request) {
		return Feedback.builder()
				.note(request.note())
				.comment(request.comment())
				.book(Book.builder()
							.id(request.bookId())
							.shareable(false)
							.archived(false)
							.build())
				.build();
	}
	
	public FeedbackResponse toFeedbackResponse(Feedback f, Integer id) {
		
		return FeedbackResponse.builder()
				.note(f.getNote())
				.comment(f.getComment())
				.ownFeedback(Objects.equals(f.getCreatedBy(), id))
				.build();
	}
}
