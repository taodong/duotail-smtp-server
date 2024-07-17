package com.duotail.smtp.common.event.model;

import jakarta.validation.constraints.NotBlank;

public record EmailEvent(@NotBlank String id, @NotBlank String from, @NotBlank String to) {
}
