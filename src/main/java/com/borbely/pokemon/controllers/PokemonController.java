package com.borbely.pokemon.controllers;

import com.borbely.pokemon.dto.PokemonDto;
import com.borbely.pokemon.dto.PokemonResponse;
import com.borbely.pokemon.model.Pokemon;
import com.borbely.pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    public PokemonResponse getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return pokemonService.getAllPokemon(pageNo, pageSize);
    }

    @GetMapping("pokemon/{id}")
    public PokemonDto pokemonDetail(@PathVariable int id) {
        return pokemonService.getPokemonDtoByInt(id);
    }

    @PostMapping("pokemon/create")
//    @ResponseStatus(HttpStatus.CREATED)
    public PokemonDto createPokemon(@RequestBody PokemonDto pokemonDto) {
//        System.out.println(pokemon.getName());
//        System.out.println(pokemon.getType());
//        return ResponseEntity.ok(pokemon);

        return pokemonService.createPokemon(pokemonDto);
    }

//    @PostMapping("pokemon/create")
//    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
//        System.out.println(pokemon.getName());
//        System.out.println(pokemon.getType());
//        return pokemon;
//    }

//    @PutMapping("pokemon/{id}/update")
//    public ResponseEntity<Pokemon> updatePokemon(@RequestBody Pokemon pokemon, @PathVariable("id") int pokemonID) {
//        System.out.println(pokemon.getName());
//        System.out.println(pokemon.getType());
//        return ResponseEntity.ok(pokemon);
//    }

    @PutMapping("pokemon/{id}/update")
    public PokemonDto updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int pokemonID) {

        return pokemonService.updatePokemon(pokemonDto, pokemonID);
    }

    @DeleteMapping("pokemon/{id}/delete")
    public String deletePokemon(@PathVariable("id") int pokemonId) {

        pokemonService.deletePokemon(pokemonId);

        return "Pokemon deleted";

    }
}
