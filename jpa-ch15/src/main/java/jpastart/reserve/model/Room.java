package jpastart.reserve.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "room_info")
public class Room {
    @Id
    private String number;
    private String name;

    @Column(name = "description")
    private String desc;

    @Column(name = "id", insertable = false, updatable = false)
    private Long dbId;

    private Date createTime;

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

    public Long getDbId() {
        return dbId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

}
