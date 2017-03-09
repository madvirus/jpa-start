package jpastart.reserve.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_info")
public class Room {
    @Id
    @Column(name = "room_number")
    private String number;
    private String name;

    @Column(name = "description")
    private String desc;

    private LocalDateTime createTime;

    protected Room() {
    }

    public Room(String number, String name, String desc) {
        this.number = number;
        this.name = name;
        this.desc = desc;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

}
