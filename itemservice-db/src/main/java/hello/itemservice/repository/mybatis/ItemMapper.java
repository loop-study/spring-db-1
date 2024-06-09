package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    List<Item> findAll(ItemSearchCond itemSearch);

    // 간단한 쿼리라면 @Select를 사용해도 되지만, xml에 같은 id가 존재할때 충돌나고 xml 의 기능을 쓰기 어려워서 비권장한다.
//    @Select("select id, item_name, price, quantity from item where id = #{id}")
    Optional<Item> findById(Long id);
}
