package cc.aoeiuv020.panovel.server.dal.mapper;

import cc.aoeiuv020.panovel.server.dal.model.Book;

import java.util.List;
import java.util.Map;

public interface BookMapperCustom extends BookMapper {

    List<Book> querySelective(Map<String, Object> params);


}