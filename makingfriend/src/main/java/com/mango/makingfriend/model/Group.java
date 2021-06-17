package com.mango.makingfriend.model;



import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@DynamicUpdate
@Table(name = "groups")
@Data
public class Group {

 //   private int id;
    @Id
    private String groupId;//群id
    private String groupName;//群名称
    private String groupAvatar;//群头像
    private String creator;//创建人

    private String owner;//创建人
    private  Integer  affiliations;//群成员数
    private  String type;//类型 group ,chatRoom
    private Long created;//创建时间
    private Long last_modified;//最后修改时间


/*

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
*/


}
