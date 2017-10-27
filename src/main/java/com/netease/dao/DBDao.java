package com.netease.dao;

import com.netease.bean.Content;
import com.netease.bean.Image;
import com.netease.bean.Person;
import com.netease.bean.Trx;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;

/**
 * 数据库操作接口
 */
@Repository
public interface DBDao {
    @Select("select * from person where userName=#{username} ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "userName", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "nickName", property = "nickName"),
            @Result(column = "userType", property = "usertype")
    })
    List<Person> getPersons(String username);

    @Select("select * from content ")
    @Results({
            @Result(id=true, column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail"  )
    })
    List<Content> getContents();

    @Select("select t.price as buyPrice, " +
            " c.title as title," +
            " c.icon as icon," +
            " c.abstract as abstract," +
            " c.text as text, " +
            " c.id as id ," +
            " c.price as price ," +
            "  t.time as buyTime," +
            "  t.num as buyNum," +
            " \'1\' as isBuy " +
            //" case when p.usertype=\'0\' then \'1\' else \'0\' end as isBuy, " +
            //" case when p.usertype=\'1\' then \'1\' else \'0\' end as isSell " +
            " from trx t join content c join person p " +
            " on t.contentId=c.id and t.personId = p.id " +
            " where t.personId = #{personId} ")
    @Results({
            @Result(column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "buyPrice",  property = "buyPrice" ),
            @Result(column = "buyNum",  property = "buyNum" ),
            @Result(column = "buyTime",  property = "buyTime" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    List<Content> getBuyedContentsByPerson(int personId);


    @Select("select t.price as buyPrice, " +
            " c.title as title," +
            " c.icon as icon," +
            " c.abstract as abstract," +
            " c.text as text, " +
            " c.id as id ," +
            " c.price as price ," +
            "  t.time as buyTime," +
            "  t.num as buyNum," +
            " \'1\' as isBuy " +
            " from trx t join content c join person p " +
            " on t.contentId=c.id and t.personId = p.id " +
            " where t.personId = #{personId} and t.contentId = #{contentId}")
    @Results({
            @Result(column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "buyPrice",  property = "buyPrice" ),
            @Result(column = "buyNum",  property = "buyNum" ),
            @Result(column = "buyTime",  property = "buyTime" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    Content getBuyedContentByPersonAndContent(@Param("personId") int personId, @Param("contentId") int contentId);

    @Select("select c.id as id , " +
            " c.price as price, " +
            " c.title as title, "+
            " c.icon as icon, "+
            " c.abstract as abstract, "+
            " c.text as text "+
            " from content c where c.id " +
            " not in (select c2.id " +
            " from content c2 join trx t " +
            " on c2.id = t.contentId  " +
            " where t.personId=#{personId}); ")
    @Results({
            @Result(column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    List<Content> getUnBuyedContentsByPerson(int personId);


    @Select("select c.id, " +
            " c.price," +
            " c.title, " +
            " c.icon, " +
            " c.abstract, " +
            " c.text, " +
            " t.num as buyNum, " +
            " case when t.id is null then \'0\' else \'1\' end as isSell " +
            " from content c left join trx t  " +
            " on t.contentId=c.id " +
            " where  t.contentId = #{contentId}")
    @Results({
            @Result(column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    Content getContentBySeller(int contentId);


    @Select("select c.id, " +
            " c.price," +
            " c.title, " +
            " c.icon, " +
            " c.abstract, " +
            " c.text, " +
            " t.num as buyNum, " +
            " case when t.id is null then \'0\' else \'1\' end as isSell " +
            " from content c left join trx t  " +
            " on t.contentId=c.id ")
    @Results({
            @Result(column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    List<Content> getContentsBySeller();


    @Select("select * from content where id=#{contentId}")
    @Results({
            @Result(id=true, column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    Content getContentById(int contentId);

    @Select("select c.id as id , " +
            " c.price as price ," +
            " c.title as title," +
            " c.icon as icon, " +
            " c.abstract as abstract," +
            " c.text as text ," +
            "  t.num as buyNum " +
            " from content c left join trx t " +
            " on c.id = t.contentId  " +
            " where c.id=#{contentId}")
    @Results({
            @Result(id=true, column = "id",  property = "id" ),
            @Result(column = "price",  property = "price" ),
            @Result(column = "title",  property = "title" ),
            @Result(column = "icon",  property = "image"  ),
            @Result(column = "abstract",  property = "summary" ),
            @Result(column = "text",  property = "detail" )
    })
    Content getContentById2(int contentId);

    @Insert("insert into content(price, title, icon, abstract, text) " +
            "values( #{content.price}, #{content.title}, #{content.image}, #{content.summary}, #{content.detail} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "content.id")
    int insertContent(@Param("content") Content content) ;

    @Update("update content set price=#{content.price}, title=#{content.title}, " +
            " icon=#{content.image}, abstract=#{content.summary}, text=#{content.detail} " +
            " where id = #{content.id} " )
    int updateContent(@Param("content") Content content) ;


    @Delete("delete from content where id=#{contentId}")
    int deleteContentById(int contentId);

    @Insert("insert into trx(contentId, personId, price, time, num) " +
            "values( #{trx.content.id}, #{trx.person.id}, #{trx.price}, #{trx.time}, #{trx.num} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "trx.id")
    int insertTrx(@Param("trx") Trx trx) ;

    @Insert("insert into image(name, bytes) values( #{image.name}, #{image.bytes}) ")
    @Options(useGeneratedKeys = true, keyProperty = "image.id")
    int insertImage(@Param("image") Image image) ;


    @Select("select * from image where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "bytes", property = "bytes", jdbcType = JdbcType.BLOB)
    })
    Image getImageById(int id);
}
