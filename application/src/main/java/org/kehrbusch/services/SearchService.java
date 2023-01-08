package org.kehrbusch.services;

import org.kehrbusch.entities.Result;
import org.kehrbusch.exceptions.InputException;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.entities.Roles;
import org.kehrbusch.procedure.sentence.searchtrigger.SearchTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final SearchTrigger searchTrigger;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public SearchService(SearchTrigger searchTrigger){
        this.searchTrigger = searchTrigger;
    }

    public Result search(String zip, String street, String country, int maxErrors, int noOfResults) throws InternalServerError, InputException {
        this.checkUserInput(street, zip, country, maxErrors, noOfResults);

        return searchTrigger.identify(zip, street, country, maxErrors, noOfResults);
    }

    public Result search(String zip, String street, String city, String country, int maxErrors, int noOfResults) throws InternalServerError, InputException{
        this.checkUserInput(street, zip, country, maxErrors, noOfResults);
        this.checkUserInputCity(city);

        return searchTrigger.identify(zip, street, city, country, maxErrors, noOfResults);
    }

    private void checkUserInput(String street, String zip, String country, int maxErrors, int noOfResults) throws InternalServerError, InputException{
        if (!profile.equals("dev") && !getAuthorities().contains(Roles.CLIENT.toString())) throw new InternalServerError("Internal server error");

        if (country == null || !country.equals("IT")) throw new InputException("Country is not supported.");
        if (zip == null || zip.equals("")) throw new InputException("No Zip Code provided.");
        if (zip.length() > 8) throw new InputException("Provided Zip Code is invalid");
        if (street == null || street.equals("")) throw new InputException("No Street provided.");
        if (street.length() > 30) throw new InputException("The street is exceeding the maximum allowed length.");
        if (noOfResults <= 0 || noOfResults > 10) throw new InputException("No of Results requested is not within the allowed range.");
        if (maxErrors < 0 || maxErrors > 6) throw new InputException("No of maximum allowed errors is not within the allowed range.");
    }

    private void checkUserInputCity(String city) throws InputException{
        if (city == null || city.equals("")) throw new InputException("No City provided.");
        if (city.length() > 30) throw new InputException("The city is exceeding the maximum allowed length.");
    }

    private List<String> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
