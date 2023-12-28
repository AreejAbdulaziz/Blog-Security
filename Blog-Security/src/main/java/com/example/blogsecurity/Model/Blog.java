package com.example.blogsecurity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "title cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @Column(columnDefinition = "varchar(40) unique not null")
    private String title;
    @NotEmpty(message = "body cannot be null")
    @Pattern(regexp = "^[a-zA-Z ]+$")
    @Column(columnDefinition = "varchar(2000) not null")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}
