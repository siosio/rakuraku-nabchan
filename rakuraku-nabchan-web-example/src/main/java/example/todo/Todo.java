package example.todo;

import java.io.Serializable;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class Todo implements Serializable {

    private final Long id;

    private final String title;

    public Todo(final Long id, final String title) {
        this.id = id;
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
