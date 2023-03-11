package com.borbely.pokemon.service;

import com.borbely.pokemon.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByPokemonId(int id);

    ReviewDto getReviewById(int pokemonId, int reviewId);

    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int reviewId);
}
