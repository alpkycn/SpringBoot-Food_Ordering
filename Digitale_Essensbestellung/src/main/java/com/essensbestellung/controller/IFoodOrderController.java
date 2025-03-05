package com.essensbestellung.controller;

import java.util.List;


import com.essensbestellung.entities.FoodOrder;

public interface IFoodOrderController {

    public FoodOrder getOrderbyId(Long id);

    public FoodOrder saveOrder(FoodOrder order);

    public List<FoodOrder> getAllOrders();

    public void deleteOrder(Long id);

    public FoodOrder updateOrder(Long id,FoodOrder updateOrder);
	
//    public FoodOrder updateOrder2(Long id,FoodOrder updateOrder);
}