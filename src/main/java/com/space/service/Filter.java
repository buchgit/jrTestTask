package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class Filter {
    public Specification<Ship> selectByName (final String name) {
        new Validation().checkName(name);
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(name==null)return null;
                return criteriaBuilder.like(root.get("name"), "%"+name+"%");
            }
        };
    }
    public Specification<Ship> selectById(final Long id){
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(id==null)return null;
                return criteriaBuilder.equal(root.get("id"),id);
            }
        };
    }
    public Specification<Ship> selectByPlanet(final String planet){
        new Validation().checkPlanet(planet);
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(planet==null)return null;
                return criteriaBuilder.like(root.get("planet"),"%"+planet+"%");
            }
        };
    }
    public Specification<Ship> selectByType(final ShipType shipType){
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(shipType==null)return null;
                return criteriaBuilder.equal(root.get("shipType"),shipType);
            }
        };
    }
    public Specification<Ship> selectByProdDate(final Long min,final Long max){
        new Validation().checkProdDate(min,max);
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(min==null&&max==null)return null;

                if(min==null&&max!=null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),new Date(max));
                }
                if(max==null&&min!=null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"),new Date(min));
                return criteriaBuilder.between(root.get("prodDate"),new Date(min),new Date(max));
            }
        };
    }
    public Specification<Ship> selectByIsUsed(final Boolean idUsed){
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(idUsed==null)return null;
                return criteriaBuilder.equal(root.get("isUsed"),idUsed);
            }
        };
    }
    public Specification<Ship> selectBySpeed (final Double min,final Double max){
        new Validation().checkSpeed(min,max);
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(min==null&&max==null)return null;

                if(min==null&&max!=null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("speed"),max);
                }
                if(max==null&&min!=null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"),min);
                return criteriaBuilder.between(root.get("speed"),min,max);
            }
        };
    }
    public Specification<Ship> selectByCrewSize(final Integer min, final Integer max){
        new Validation().checkCrewSize(min,max);
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(min==null&&max==null)return null;

                if(min==null&&max!=null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"),max);
                }
                if(max==null&&min!=null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"),min);
                return criteriaBuilder.between(root.get("crewSize"),min,max);
            }
        };
    }
    public Specification<Ship> selectByRating(final Double min,final Double max){
        return new Specification<Ship>() {
            @Override
            public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(min==null&&max==null)return null;

                if(min==null&&max!=null) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get("rating"),max);
                }
                if(max==null&&min!=null)
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"),min);
                return criteriaBuilder.between(root.get("rating"),min,max);
            }
        };
    }
}
