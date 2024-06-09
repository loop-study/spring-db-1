package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNameLike(String itemName);

//    List<Item> findByPriceLessThenEqual(Integer price); // 오타나서 에러난 코드
    List<Item> findByPriceLessThanEqual(Integer price);

    // 쿼리 메서드 - 메서드명이 복잡해지면 잡하면 아래의 방법을 권장.
    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);
    // 쿼리 직접 실행
    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);
}
