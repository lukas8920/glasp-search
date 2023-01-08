package org.kehrbusch.repositories.impl.redis;

import org.kehrbusch.entities.City;
import org.kehrbusch.mappers.redis.CityMapMapper;
import org.kehrbusch.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Qualifier("redisCityRepository")
@Deprecated
public class RedisCityRepositoryImpl implements CityRepository {
    private CityMapMapper cityMapMapper;
    private ReactiveHashOperations<String, String, String> hashOperations;
    private ReactiveListOperations<String, String> reactiveListOperations;

    /*@Autowired
    public RedisCityRepositoryImpl(ReactiveStringRedisTemplate redisTemplate, CityMapMapper cityMapMapper){
        this.cityMapMapper = cityMapMapper;
        this.hashOperations = redisTemplate.opsForHash();
        this.reactiveListOperations = redisTemplate.opsForList();
    }*/

    @Override
    public City findByZipAndCountry(String zip, String country) {
        String key = country + "C" + zip;

        City city = new City();
        this.hashOperations.entries(key)
                .doOnNext(entry -> {
                    switch (entry.getKey()){
                        case "city":
                            city.setCity(entry.getValue());
                            break;
                        case "country":
                            city.setCountry(entry.getValue());
                            break;
                    }
                }).blockLast();

        if (city.getCity() == null) return null;

        city.setZip(zip);

        reactiveListOperations.range(key + ":children", 0, -1)
                .doOnNext(city::addSubdistrict)
                .blockLast();

        return city;
    }

    @Override
    public void persist(City city) {
        Map<String, String> element = this.cityMapMapper.map(city);
        String key = city.getCountry() + "C" + city.getZip();
        this.hashOperations.putAll(key, element).block();
        city.getSubdistricts().forEach(subdistrict -> {
            this.addChild(key, subdistrict);
        });
    }

    @Override
    public void persistAll(List<City> cities) {

    }

    @Override
    public Map<String, City> getAllCities() {
        return null;
    }

    private void addChild(String parent, String child){
        this.reactiveListOperations.leftPush(parent + ":children", child).block();
    }
}
