package com.scm.scm10.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.internal.build.AllowNonPortable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    private String title;
    @ManyToOne
    private Contact contact;


}
