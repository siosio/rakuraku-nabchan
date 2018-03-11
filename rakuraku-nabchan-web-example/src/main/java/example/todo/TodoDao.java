package example.todo;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import nablarch.integration.doma.DomaConfig;

@Dao(config = DomaConfig.class)
public interface TodoDao {

    @Select
    List<Todo> findAll();
}
