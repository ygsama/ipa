package g.ygsama.ipa.test_es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

/**
 * @description 创建一个Repository的相关接口（可以加注解@RepositoryDefinition），这个接口必须继承Repository接口，
 * 这里的ElasticsearchRepository的上级上级。。。接口就是继承自Repository
 * ElasticsearchRepository接口泛型通常写成<存储的实体类型, 主键类型>，这样就将这个仓库定制化为某个文档的专用，比如这里
 * 就是Book文档的专用，我们也可以定义更加通用的Repository，比如
 *
 * @NoRepositoryBean
 * interface MyBaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
 *   …
 * }
 */
public interface BookRepository extends ElasticsearchRepository<Book,String> {

    Book findByName(String name);
    List<Book> findByAuthor(String author);
    Book findBookById(String id);
}
