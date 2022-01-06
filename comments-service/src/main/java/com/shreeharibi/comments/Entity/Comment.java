package com.shreeharibi.comments.Entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class to represent Comment Entity
 */
@Entity
@Data
@ToString
public class Comment {

    /**
     * Primary key
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String noteid;

    private String comment;
}
