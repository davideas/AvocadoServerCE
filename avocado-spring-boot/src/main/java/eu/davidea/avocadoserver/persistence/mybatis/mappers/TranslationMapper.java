package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.translation.Translation;
import eu.davidea.avocadoserver.business.translation.TranslationEntry;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TranslationMapper {

    /* TRANSLATION */

    @Insert("insert into translation (type, name) values (#{type,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR})")
    void insertNewTranslation(Translation translation);

    @Select("select * from translation where id = #{id}")
    Translation getTranslation(Long id);

    // https://stackoverflow.com/a/45786973/3397345
    @Select("select last_insert_id()")
    Long getLastInsertId();

    /* TRANSLATION ENTRY */

    @Insert("insert into translation_entry (id, language_code, text) " +
            "values (#{id}, #{languageCode,jdbcType=VARCHAR}, #{text,jdbcType=VARCHAR})")
    void insertTranslationEntry(TranslationEntry entry);

    @Results
            (value = {
            @Result(property = "languageCode", column = "language_code"),
    })
    @Select("select * from translation_entry where id = #{id} and language_code = #{languageCode,jdbcType=VARCHAR}")
    TranslationEntry getTranslationEntry(@Param("id") Long id, @Param("languageCode") String languageCode);

    @Select("select * from translation_entry where id = #{id} order by te.language_code asc")
    List<TranslationEntry> getTranslationEntriesForId(Long id);

    @Update("update translation_entry set text = #{text,jdbcType=VARCHAR} where id = #{id} and language_code = #{languageCode,jdbcType=VARCHAR}")
    int updateTranslationEntry(@Param("id") Long id, @Param("languageCode") String languageCode, @Param("text") String text);

}