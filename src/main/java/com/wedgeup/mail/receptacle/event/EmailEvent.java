package com.wedgeup.mail.receptacle.event;

import jakarta.validation.constraints.NotBlank;

public record EmailEvent(@NotBlank String id, @NotBlank String from, @NotBlank String to) {
}
