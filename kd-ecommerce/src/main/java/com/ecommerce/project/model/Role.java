package com.ecommerce.project.model;

import com.ecommerce.project.model.enums.AppRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
   @Column(name = "role_name", length = 20)
    private AppRole roleName;
}
