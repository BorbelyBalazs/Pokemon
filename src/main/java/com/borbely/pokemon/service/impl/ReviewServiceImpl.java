package com.borbely.pokemon.service.impl;

import com.borbely.pokemon.dto.PokemonDto;
import com.borbely.pokemon.dto.ReviewDto;
import com.borbely.pokemon.exceptions.PokemonNotFoundException;
import com.borbely.pokemon.exceptions.ReviewNotFoundException;
import com.borbely.pokemon.model.Pokemon;
import com.borbely.pokemon.model.Review;
import com.borbely.pokemon.repository.PokemonRepository;
import com.borbely.pokemon.repository.ReviewRepository;
import com.borbely.pokemon.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {

        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon doesn't exist"));

        review.setPokemon(pokemon);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {

        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews.stream().map(review -> mapToDto(review)).toList();
    }

    @Override
    public ReviewDto getReviewById(int pokemonId, int reviewId) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("This pokemon doesn't exists"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("This review doesn't exists"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("This pokemon doesn't exists"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("This review doesn't exists"));

        if (review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        //    review.setPokemon(reviewDto.getPokemon());

        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }


    @Override
    public void deleteReview(int reviewId) {

//        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("This pokemon doesn't exists"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("This review doesn't exists"));

//        if (review.getPokemon().getId() != pokemon.getId()) {
//            throw new ReviewNotFoundException("This review does not belong to a pokemon");
//        }

        reviewRepository.delete(review);
    }


    private ReviewDto mapToDto(Review review) {
        return new ReviewDto(review.getId(), review.getTitle(), review.getContent(), review.getStars());
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        return new Review(reviewDto.getTitle(), reviewDto.getContent(), reviewDto.getStars());
    }
}
