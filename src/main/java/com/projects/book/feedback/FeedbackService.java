package com.projects.book.feedback;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.projects.book.book.Book;
import com.projects.book.book.BookRepository;
import com.projects.book.common.PageResponse;
import com.projects.book.exception.OperationNotPermittedException;
import com.projects.book.user.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
	private final FeedbackRepository feedbackRepository;
	private final FeedbackMapper feedbackMapper;
	private final BookRepository bookRepository;
	
	public Integer save(FeedbackRequest request, Authentication authentication) {
		Book book = bookRepository.findById(request.bookId())
				.orElseThrow(()-> new EntityNotFoundException("No book found with ID: "+request.bookId()));
		if(book.isArchived() || !book.isShareable()) {
			throw new OperationNotPermittedException("You can't give a feedback for and archived or not shareabl book");
		}
		User user = (User)authentication.getPrincipal();
		if(Objects.equals(book.getOwner().getId(),user.getId() )) {
			throw new OperationNotPermittedException("You cannot give feedback for your own book");
		}
		Feedback feedback = feedbackMapper.toFeedback(request);
		return feedbackRepository.save(feedback).getId();
			
		
	}
	
	public PageResponse<FeedbackResponse> findAllFeebacksByBook(Integer bookId, int page, int size, Authentication connectedUser)
	{
		Pageable pageable = PageRequest.of(page, size);
		User user = (User)connectedUser.getPrincipal();
		Page<Feedback> feedbacks = feedbackRepository.findAllBookId(bookId, pageable);
		List<FeedbackResponse> feedbackResponses = feedbacks.stream()
				.map(f->feedbackMapper.toFeedbackResponse(f, user.getId()))
				.toList();
		return new PageResponse<>(
				feedbackResponses,
				feedbacks.getNumber(),
				feedbacks.getSize(),
				feedbacks.getTotalElements(),
				feedbacks.getTotalPages(),
				feedbacks.isFirst(),
				feedbacks.isLast()
				);
	}
}
