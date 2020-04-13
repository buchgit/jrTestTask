package com.space.service;

import com.space.exception.NotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;


@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipsRepository shipsRepository;

    @Override
    public Integer getCount(Specification<Ship>specification) {
        return shipsRepository.findAll(specification).size();
    }

    @Override
    public Ship get(Long id) {
        if (!shipsRepository.existsById(id)){
            throw new NotFoundException("not valid id");
        }else return shipsRepository.findById(id).get();
    }

    @Override
    public Ship create(Ship ship) {
        if (ship.getUsed()==null)ship.setUsed(false);
        ship.setRating(calculateRating(ship.getSpeed(), ship.getUsed(), ship.getProdDate()));
        return shipsRepository.saveAndFlush(ship);
   }

    @Override
    public Page<Ship> getFilteredList(Specification<Ship> specification, Pageable sortedBy) {
        return shipsRepository.findAll(specification, sortedBy);
    }

    @Override
    public Ship edit(Long id, Ship newVersion) {
        if (!shipsRepository.existsById(id)) throw new NotFoundException("not valid id");
        Validation validation = new Validation();
        Ship ship = shipsRepository.findById(id).get();
        if (newVersion.getName() != null) {
            validation.checkName(newVersion.getName());
            ship.setName(newVersion.getName());
        }
        if (newVersion.getPlanet() != null) {
            validation.checkPlanet(newVersion.getPlanet());
            ship.setPlanet(newVersion.getPlanet());
        }
        if (newVersion.getCrewSize() != null) {
            validation.checkCrewSize(newVersion.getCrewSize());
            ship.setCrewSize(newVersion.getCrewSize());
        }
        if (newVersion.getProdDate() != null) {
            validation.checkProdDate(newVersion.getProdDate());
            ship.setProdDate(newVersion.getProdDate());
        }
        if (newVersion.getShipType() != null) {
            ship.setShipType(newVersion.getShipType());
        }
        if (newVersion.getSpeed() != null) {
            validation.checkSpeed(newVersion.getSpeed());
            ship.setSpeed(newVersion.getSpeed());
        }
        if (newVersion.getUsed() != null) {
            ship.setUsed(newVersion.getUsed());
        }

        ship.setRating(calculateRating(ship.getSpeed(), ship.getUsed(), ship.getProdDate()));

        return shipsRepository.save(ship);
    }

    @Override
    public void delete(Long id) {
        if (shipsRepository.existsById(id)) {
            shipsRepository.deleteById(id);
        }else throw new NotFoundException("not valid id");
    }
    Double calculateRating(Double speed, Boolean isUsed, Date prodDate){
        Double k = isUsed?0.5:1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        Integer year = calendar.get(Calendar.YEAR);
        Integer delta = 3019-year;
        BigDecimal rating = new BigDecimal(80*speed*k/(delta+1));
        return rating.setScale(2,RoundingMode.HALF_UP).doubleValue();
    }
}
/*
𝑅 =80·𝑣·𝑘/(𝑦0−𝑦1+1)

v — скорость корабля;
k — коэффициент, который равен 1 для нового корабля и 0,5 для использованного;
y0 — текущий год (не забудь, что «сейчас» 3019 год);
y1 — год выпуска корабля.
 */