package com.space.controller;

import com.space.exception.BadRequestException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.Filter;
import com.space.service.ShipService;
import com.space.service.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class Controller {
    @Autowired
    ShipService shipService;

    @PostMapping("/ships")                                                                          //2
    @ResponseStatus(HttpStatus.OK)
    public Ship createShip(@RequestBody Ship ship){
        if (    ship.getName()==null
                ||ship.getPlanet()==null
                ||ship.getSpeed()==null
                ||ship.getCrewSize()==null
                ||ship.getProdDate()==null
                )throw new BadRequestException("not valid parameters");
        new Validation().checkName(ship.getName());
        new Validation().checkPlanet(ship.getPlanet());
        new Validation().checkSpeed(ship.getSpeed());
        new Validation().checkCrewSize(ship.getCrewSize());
        new Validation().checkProdDate(ship.getProdDate());
        return shipService.create(ship);
    }

    @PostMapping("/ships/{id}")                                                                     //3
    @ResponseStatus(HttpStatus.OK)
    public Ship edit(@PathVariable("id") String id,@RequestBody Ship ship){
        Long longId = new Validation().checkId(id);
        return shipService.edit(longId,ship);
    }

    @DeleteMapping("/ships/{id}")                                                                   //4
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id){
        Long longId = new Validation().checkId(id);
        shipService.delete(longId);
    }

    @GetMapping("/ships/{id}")                                                                      //5
    @ResponseStatus(HttpStatus.OK)
    public Ship get(@PathVariable("id") String id){
        Long longId = new Validation().checkId(id);
        return shipService.get(longId);
    }

    @GetMapping("/ships")                                                                           // 1, 6
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getFilteredList(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "pageNumber", required = false,defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false,defaultValue = "3") Integer pageSize,
            @RequestParam(value = "order", required = false,defaultValue = "ID") ShipOrder order
    ){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(order.getFieldName()));
        Filter filter = new Filter();

//        new Validation().checkName(name);
//        new Validation().checkPlanet(planet);
//        new Validation().checkProdDate(after,before);
//        new Validation().checkSpeed(minSpeed,maxSpeed);
//        new Validation().checkCrewSize(minCrewSize,maxCrewSize);

        return shipService.getFilteredList(
             Specification.where(
             filter.selectByName(name)
             .and(filter.selectByPlanet(planet))
             .and(filter.selectByType(shipType))
             .and(filter.selectByProdDate(after,before))
             .and(filter.selectByIsUsed(isUsed))
             .and(filter.selectBySpeed(minSpeed,maxSpeed))
             .and(filter.selectByCrewSize(minCrewSize,maxCrewSize))
             .and(filter.selectByRating(minRating,maxRating))
        )
             ,pageable).getContent();
    }

    @GetMapping("/ships/count")                                                                         //7
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating

    ){
//        new Validation().checkName(name);
//        new Validation().checkPlanet(planet);
//        new Validation().checkProdDate(after,before);
//        new Validation().checkSpeed(minSpeed,maxSpeed);
//        new Validation().checkCrewSize(minCrewSize,maxCrewSize);

        Filter filter = new Filter();
        return shipService.getCount(Specification.where(
            filter.selectByName(name)
            .and(filter.selectByPlanet(planet))
            .and(filter.selectByType(shipType))
            .and(filter.selectByProdDate(after,before))
            .and(filter.selectByIsUsed(isUsed))
            .and(filter.selectBySpeed(minSpeed,maxSpeed))
            .and(filter.selectByCrewSize(minCrewSize,maxCrewSize))
            .and(filter.selectByRating(minRating,maxRating))
             ));
    }
}
/*
1. получать список всех существующих кораблей;
2. создавать новый корабль;
3. редактировать характеристики существующего корабля;
4. удалять корабль;
5. получать корабль по id;
6. получать отфильтрованный список кораблей в соответствии с переданными фильтрами;
7. получать количество кораблей, которые соответствуют фильтрам.
 */

/* //параметры запроса из файла script.js -> function processSearch(root, currentPage) {
   sufix += "name=" + name;
   sufix += "&planet=" + planet;
   sufix += "&shipType=" + shipType.toUpperCase();
   sufix += "&after=" + yearAfter;
   sufix += "&before=" + yearBefore;
   sufix += "&isUsed=" + isUsed;
   sufix += "&minSpeed=" + speedMin;
   sufix += "&maxSpeed=" + speedMax;
   sufix += "&minCrewSize=" + crewSizeMin;
   sufix += "&maxCrewSize=" + crewSizeMax;
   sufix += "&minRating=" + ratingMin;
   sufix += "&maxRating=" + ratingMax;
   sufix += "&pageNumber=" + (+currentPage - 1);
   sufix += "&pageSize=" + +limit;
   sufix += "&order="
 */