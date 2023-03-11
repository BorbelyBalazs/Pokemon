package com.borbely.pokemon.service.impl;

import com.borbely.pokemon.dto.PokemonDto;
import com.borbely.pokemon.dto.PokemonResponse;
import com.borbely.pokemon.exceptions.PokemonNotFoundException;
import com.borbely.pokemon.model.Pokemon;
import com.borbely.pokemon.repository.PokemonRepository;
import com.borbely.pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {

        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);

        //        pokemonResponse.setId(newPokemon.getId());
//        pokemonResponse.setName(newPokemon.getName());
//        pokemonResponse.setType(newPokemon.getType());

        return new PokemonDto(newPokemon.getId(), newPokemon.getName(), newPokemon.getType());

    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDto> content = listOfPokemon.stream()
                .map(this::mapToDto)
                .toList();

        PokemonResponse pokemonResponse = new PokemonResponse(
                content,
                pokemons.getNumber(),
                pokemons.getSize(),
                pokemons.getTotalElements(),
                pokemons.getTotalPages(),
                pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonDtoByInt(int id) {
       Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("This pokemon doesn't exists"));

        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException("This pokemon doesn't exists"));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        return new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getType());
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        return new Pokemon(pokemonDto.getName(), pokemonDto.getType());
    }


}
