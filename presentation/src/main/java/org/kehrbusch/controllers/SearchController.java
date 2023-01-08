package org.kehrbusch.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.kehrbusch.entities.*;
import org.kehrbusch.exceptions.EmptyResultException;
import org.kehrbusch.exceptions.InputException;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.mapper.ResultMapper;
import org.kehrbusch.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
@RequestMapping(path = "/app")
@Tag(name = "Address Search")
@SecurityRequirement(name = "bearerAuth")
public class SearchController {
    private static final Logger logger = Logger.getLogger(SearchController.class.getName());

    private final SearchService searchService;
    private final ResultMapper mapper;

    @Autowired
    public SearchController(SearchService searchService, ResultMapper mapper){
        this.mapper = mapper;
        this.searchService = searchService;
    }

    @Operation(summary = "Search request when the zip code and the street are known.", operationId = "searchZipStreet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The search results.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResult.class)) }),
            @ApiResponse(responseCode = "400", description = "Error in the provided data, please check the response message for details.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "There is no address matching the ZipStreet.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content)
    })
    @PostMapping("/search/zipstreet")
    public SearchResult search(@RequestBody ZipStreetRequest zipStreetRequest) throws InternalServerError, InputException, EmptyResultException {
        Result result = searchService.search(zipStreetRequest.getZip(), zipStreetRequest.getStreet(), zipStreetRequest.getCountry(),
                zipStreetRequest.getMaxErrors(), zipStreetRequest.getNoOfResults());
        if (result.getAddresses().isEmpty()) throw new EmptyResultException("No address is matching.");
        return mapper.map(result);
    }

    @Operation(summary = "Search request when the zip code, the street and the city are known.", operationId = "searchZipStreetCity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The search results.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResult.class)) }),
            @ApiResponse(responseCode = "400", description = "Error in the provided data, please check the response message for details.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "There is no address matching the ZipStreet.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.",
                    content = @Content)
    })
    @PostMapping("/search/zipstreetcity")
    public SearchResult search(@RequestBody ZipStreetCityRequest zipStreetCityRequest) throws InternalServerError, InputException, EmptyResultException {
        Result result = searchService.search(zipStreetCityRequest.getZip(), zipStreetCityRequest.getStreet(),
                zipStreetCityRequest.getCity(), zipStreetCityRequest.getCountry(), zipStreetCityRequest.getMaxErrors(), zipStreetCityRequest.getNoOfResults());
        if (result.getAddresses().isEmpty()) throw new EmptyResultException("No address is matching.");
        return mapper.map(result);
    }
}
