package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface ShipService {
    Page<Ship>  getFilteredList(Specification<Ship> specification, Pageable sortedBy);  //1,6
    Integer     getCount(Specification<Ship> specification);                            //7
    Ship        get(Long id);                                                           //5
    Ship        create(Ship ship);                                                      //2
    Ship        edit(Long id,Ship newVersion);                                          //3
    void        delete(Long id);                                                        //4
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
