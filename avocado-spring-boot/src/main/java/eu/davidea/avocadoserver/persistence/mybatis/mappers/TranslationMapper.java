package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.translation.TranslationEntry;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TranslationMapper {

    @Insert("insert into translation (id) values (null)")
    void newTranslationPk();

    // https://stackoverflow.com/a/45786973/3397345
    @Select("select last_insert_id()")
    Long getLastInsertId();

    @Insert("insert into translation_entry (id, cre_date, mod_date, language_code, name, text) " +
            "values (#{id}, #{creDate,jdbcType=TIMESTAMP}, #{modDate,jdbcType=TIMESTAMP}, #{languageCode,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{text,jdbcType=VARCHAR})")
    void insertTranslationEntry(TranslationEntry entry);

    @Results(value = {
            @Result(property = "languageCode", column = "language_code"),
    })
    @Select("select * from translation_entry where id = #{id} and language_code = #{languageCode,jdbcType=VARCHAR}")
    TranslationEntry getTranslationEntry(@Param("id") Long id, @Param("languageCode") String languageCode);

    @Select("select * from translation_entry where id = #{id} order by te.language_code asc")
    List<TranslationEntry> getTranslationsForId(Long id);

    @Update("update translation_entry set text = #{text,jdbcType=VARCHAR} where id = #{id} and language_code = #{languageCode,jdbcType=VARCHAR}")
    List<TranslationEntry> updateTranslation(@Param("id") Long id, @Param("languageCode") String languageCode, @Param("text") String text);

}