package com.example.otclients.models.process;

import com.example.otclients.models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Client extends BaseModel {

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String name;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String lastName;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String email;
    @Column(columnDefinition = "varchar(20)")
    private String phoneNumber;

    public String getFullName() {
        return this.name + " " + this.lastName;
    }
}
