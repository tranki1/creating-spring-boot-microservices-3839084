package com.example.explorecalijpa.web;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.explorecalijpa.model.TourRating;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.explorecalijpa.business.TourRatingService;

import jakarta.validation.Valid;

/**
 * Tour Rating Controller
 * <p>
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
	private TourRatingService tourRatingService;

	public TourRatingController(TourRatingService tourRatingService) {
		this.tourRatingService = tourRatingService;
	}

	/**
	 * Create a Tour Rating.
	 *
	 * @param tourId
	 * @param ratingDto
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createTourRating(@PathVariable(value = "tourId") int tourId,
								 @RequestBody @Valid RatingDto ratingDto) {
		tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
		List<TourRating> tourRatings = tourRatingService.lookupRatings(tourId);
		return tourRatings.stream().map(RatingDto::new).toList();
	}

	@GetMapping(path = "/average")
	public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
		return Map.of("average", tourRatingService.getAverageScore(tourId));
	}

	@PutMapping
	public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDto ratingDto) {
		return new RatingDto(tourRatingService.update(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment()));
	}

	@DeleteMapping(path = "/{customerId}")
	public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
		tourRatingService.delete(tourId, customerId);
	}

	@PatchMapping
	public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDto ratingDto) {
		TourRating tourRating = tourRatingService.updateSome(
				tourId,
				ratingDto.getCustomerId(),
				Optional.ofNullable(ratingDto.getScore()),
				Optional.ofNullable(ratingDto.getComment())
		);
		return new RatingDto(tourRating);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String return404(NoSuchElementException exception) {
		return exception.getMessage();
	}


}
