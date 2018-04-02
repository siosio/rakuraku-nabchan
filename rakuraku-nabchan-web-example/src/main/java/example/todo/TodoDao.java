package example.todo;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Result;

import nablarch.integration.doma.DomaConfig;

@Dao(config = DomaConfig.class)
public interface TodoDao {
    
    @Insert
    Result<Todo> insert(Todo entity);

    @Select
    List<Todo> findAll();
}
