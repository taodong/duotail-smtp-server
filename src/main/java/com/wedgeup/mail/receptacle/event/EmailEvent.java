package com.wedgeup.mail.receptacle.event;

import jakarta.validation.constraints.NotBlank;
import org.springframework.modulith.events.Externalized;

//@Externalized("com.wedgeup.mail.receptacle.event::#{id()}")
public record EmailEvent(@NotBlank String id, @NotBlank String from, @NotBlank String to) {
}
