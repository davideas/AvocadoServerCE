package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.menu.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Results(value = {
            @Result(property = "translationId", column = "title_trans_id"),
            @Result(property = "title", column = "text"),
    })
    @Select("select m.*, te.text from menus m inner join translation_entry te on m.title_trans_id = te.id" +
            " where restaurant_id = #{restaurantId} and status != 'DELETED' and language_code = #{languageCode,jdbcType=VARCHAR}" +
            " order by order_id asc")
    List<Menu> getMenus(@Param("restaurantId") Long restaurantId, @Param("languageCode") String languageCode);

    @Results(value = {
            @Result(property = "translationId", column = "title_trans_id"),
            @Result(property = "title", column = "text"),
    })
    @Select("select m.*, te.text from menus m inner join translation_entry te on m.title_trans_id = te.id" +
            " where menu_id = #{menuId} and restaurant_id = #{restaurantId} and status != 'DELETED'")
    Menu getMenuById(@Param("restaurantId") Long restaurantId, @Param("menuId") Long menuId);

}