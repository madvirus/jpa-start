package jpastart.guide.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RecItem {
    private String name;
    private String type;

    public RecItem() {
    }

    public RecItem(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecItem recItem = (RecItem) o;
        return Objects.equals(name, recItem.name) &&
                Objects.equals(type, recItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "RecItem{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
