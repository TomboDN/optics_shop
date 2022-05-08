package com.mirea.optics_shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
@Setter
@Getter
public class Group {
    @Id
    @SequenceGenerator(name = "groups_seq", sequenceName = "groups_sequence", allocationSize = 1)
    @GeneratedValue(generator = "groups_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    public Group(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Group() {

    }
}
