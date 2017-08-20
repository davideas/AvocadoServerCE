package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.menu.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Results(value = {
            @Result(property = "translationId", column = "title"),
            @Result(property = "title", column = "text"),
    })
    @Select("select m.*, te.text from menus m inner join translation_entry te on m.title = te.id" +
            " where restaurant_id = #{restaurantId} and status != 'DELETED' and language_code = #{languageCode,jdbcType=VARCHAR}" +
            " order by order_id asc")
    List<Menu> getMenus(@Param("restaurantId") Long restaurantId, @Param("languageCode") String languageCode);

    @Results(value = {
            @Result(property = "translationId", column = "title"),
            @Result(property = "title", column = "text"),
    })
    @Select("select m.*, te.text from menus m inner join translation_entry te on m.title = te.id" +
            " where m.id = #{menuId} and status != 'DELETED' and language_code = #{languageCode,jdbcType=VARCHAR}")
    Menu getMenu(@Param("menuId") Long menuId, @Param("languageCode") String languageCode);

}