package calcite;

import com.alibaba.fastjson.JSON;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class SimpleCalCiteTest {

        public static void main(String[] args) throws SqlParseException {

            // Convert query to SqlNode
            String sql = "select user_id, sum(price) total_price from transactions where status = 1 group by user_id";
            //构建config，有默认的构造也有带参数的config   返回ConfigImpl   有默认的实现
            SqlParser.Config config = SqlParser.configBuilder().build();
            //根据config构建sql解析器   reader是SourceStringReader,String流
            SqlParser parser = SqlParser.create(sql, config);
            //构建parse tree
            SqlNode node = parser.parseQuery();
            System.out.println(JSON.toJSONString(node));
        }
}
