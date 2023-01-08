package org.kehrbusch.services.redis;

import org.kehrbusch.entities.City;
import org.kehrbusch.entities.Roles;
import org.kehrbusch.entities.database.Element;
import org.kehrbusch.entities.database.ZipStreet;
import org.kehrbusch.exceptions.InternalServerError;
import org.kehrbusch.repositories.CityRepository;
import org.kehrbusch.repositories.ZipStreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Deprecated
public class DbInflateService {
    private static final Logger logger = Logger.getLogger(DbInflateService.class.getName());

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${initial.street.data.path}")
    private String pathToInitialData;

    private final CityRepository cityRepository;
    private final ZipStreetRepository zipStreetRepository;

    private final Element element0 = new Element("", "0", "ITS01");
    private final Element element1 = new Element("", "1", "ITS11");
    private final Element element2 = new Element("", "2", "ITS21");
    private final Element element3 = new Element("", "3", "ITS31");
    private final Element element4 = new Element("", "4", "ITS41");
    private final Element element5 = new Element("", "5", "ITS51");
    private final Element element6 = new Element("", "6", "ITS61");
    private final Element element7 = new Element("", "7", "ITS71");
    private final Element element8 = new Element("", "8", "ITS81");
    private final Element element9 = new Element("", "9", "ITS91");

    @Autowired
    public DbInflateService(ZipStreetRepository zipStreetRepository, @Qualifier("redisCityRepository") CityRepository cityRepository){
        this.zipStreetRepository = zipStreetRepository;
        this.cityRepository = cityRepository;
    }

    public void add(ZipStreet zipStreet){
        logger.info("Add zipstreet");
        logger.info(zipStreet.getZip() + " " + zipStreet.getStreet());
        char[] baseArray = zipStreet.toString().toCharArray();
        Element firstElement = identifiyRootElement(baseArray[0]);
        if (firstElement == null) return;

        for (int i = 0; i <= 9; i++){
            if (firstElement.getChildren().size() == 0){
                Element rootElement = new Element("", String.valueOf(i), "ITS" + i + "1");
                zipStreetRepository.add(rootElement);
            }
        }

        Element parent = firstElement;
        Element child;
        for (int i = 1; i < baseArray.length; i++){
            List<String> list = parent.getChildren();
            child = zipStreetRepository.find(list, String.valueOf(baseArray[i]));

            if (child == null){
                parent = new Element(parent.getKey(), String.valueOf(baseArray[i]), "ITS" + baseArray[0] +
                        zipStreetRepository.getNextFreeKey(String.valueOf(baseArray[0])));
                zipStreetRepository.add(parent);
                zipStreetRepository.addChild(parent.getParent(), parent.getKey());
            } else {
                parent = child;
            }
        }
    }

    public void add(List<ZipStreet> zipStreets) throws InternalServerError{
        if (!getAuthorities().contains(Roles.ADMIN.toString())) throw new InternalServerError("Internal server error");

        zipStreets.forEach(this::add);
        List<String> keys = zipStreetRepository.getKeys("ITS");
        logger.info("Datbase contains " + keys.size() + " nodes.");
    }

    //Init may be used when parent key, element key and children keys are known
    public void initZipStreet() throws InternalServerError {
        if (!profile.equals("dev") && !getAuthorities().contains(Roles.ADMIN.toString())) throw new InternalServerError("Internal server error");

        try {
            File initFile = new File(pathToInitialData);
            InputStream inputStream = new FileInputStream(initFile);
            Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if (line.length() <= 2) return;
                Element element = Element.buildFromString(line);
                zipStreetRepository.add(element);
                element.getChildren().forEach(child -> {
                    zipStreetRepository.addChild(element.getKey(), child);
                });
            }
            logger.info("Finished importing.");
        } catch (FileNotFoundException e){
            throw new InternalServerError("IO Exception");
        }
    }

    public void initCity() throws InternalServerError {
        if (!profile.equals("dev") && !getAuthorities().contains(Roles.ADMIN.toString())) throw new InternalServerError("Internal server error");

        try {
            File initFile = new File(pathToInitialData);
            InputStream inputStream = new FileInputStream(initFile);
            Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if (line.length() <= 2) return;
                City city = City.buildFromString(line);
                cityRepository.persist(city);
            }
        } catch (FileNotFoundException e){
            throw new InternalServerError("IO Exception");
        }
    }

    public List<Element> getTestElements(String pattern){
        return zipStreetRepository.getTestElements(pattern);
    }

    public void deleteTestElements(List<String> keys){
        List<String> childrenKeys = new ArrayList<>();
        keys.forEach(key -> childrenKeys.add(key + ":children"));
        keys.addAll(childrenKeys);
        zipStreetRepository.deleteTestElements(keys);
    }

    private Element identifiyRootElement(char firstChar){
        Element element = zipStreetRepository.getElement("ITS" + firstChar + "1");
        switch (firstChar){
            case '0': return element.getKey() == null ? element0 : element;
            case '1': return element.getKey() == null ? element1 : element;
            case '2': return element.getKey() == null ? element2 : element;
            case '3': return element.getKey() == null ? element3 : element;
            case '4': return element.getKey() == null ? element4 : element;
            case '5': return element.getKey() == null ? element5 : element;
            case '6': return element.getKey() == null ? element6 : element;
            case '7': return element.getKey() == null ? element7 : element;
            case '8': return element.getKey() == null ? element8 : element;
            case '9': return element.getKey() == null ? element9 : element;
        }
        return null;
    }

    private List<String> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
