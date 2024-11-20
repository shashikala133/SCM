package com.scm.scm10.helpers;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    @Builder.Default
    private MessageType type=MessageType.blue;

}
