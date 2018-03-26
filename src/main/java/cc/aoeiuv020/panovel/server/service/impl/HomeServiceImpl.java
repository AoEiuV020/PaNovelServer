package cc.aoeiuv020.panovel.server.service.impl;

import cc.aoeiuv020.panovel.server.dal.mapper.BookMapper;
import cc.aoeiuv020.panovel.server.dal.model.BookExample;
import cc.aoeiuv020.panovel.server.service.HomeService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Lucas on 2017-09-08.
 */
@Service("homeService")
public class HomeServiceImpl implements HomeService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Map<String, Integer> countBooks() {
        Map<String, Integer> count = Maps.newHashMap();
        BookExample e = new BookExample();
//        int bookCount = bookMapper.countByExample(e);
        count.put("bookCount", 99);

//         int orderCount = orderMapper.countByExample(new OrderExample());
        count.put("orderCount", 11);

        // int userCount = userMapper.countByExample(new UserExample());
        count.put("userCount", 120);

        return count;
    }
}
