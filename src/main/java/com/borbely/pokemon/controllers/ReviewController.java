package com.borbely.pokemon.controllers;

import ch.qos.logback.core.boolex.EvaluationException;
import com.borbely.pokemon.dto.PokemonDto;
import com.borbely.pokemon.dto.ReviewDto;
import com.borbely.pokemon.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/review")
    public ReviewDto createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto) {

        return reviewService.createReview(pokemonId, reviewDto);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId) {

        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("pokemon/{pokemonId}/review/{id}")
    public ReviewDto getReviewById(@PathVariable("pokemonId") int pokemonId,
                                   @PathVariable("id") int reviewId) {
        return reviewService.getReviewById(pokemonId, reviewId);
    }

    @PutMapping("pokemon/{pokemonId}/review/{id}")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto,
                                  @PathVariable("pokemonId") int pokemonId,
                                  @PathVariable("id") int id) {

        return reviewService.updateReview(pokemonId, id, reviewDto);
    }

    @DeleteMapping("pokemon/review/{id}")
    public String deleteReview(// @PathVariable("pokemonId") int pokemonId,
                               @PathVariable("id") int reviewId) {

        reviewService.deleteReview(reviewId);

        return "Review deleted";

    }
}
