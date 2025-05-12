package com.example.ProjectSem4_JavaMongo.Repository;


import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, Integer> {
//    List<CartItem> findByCart(Integer cartId);
//    CartItem findByProductIdAndCartId(Integer productId , Integer cartId);
//    public CartItem findByProductId(Integer productId , Integer orderId);
//
//    public CartItem findByAccountId(String accId);
//    public void deleteCart(String accId);
}
