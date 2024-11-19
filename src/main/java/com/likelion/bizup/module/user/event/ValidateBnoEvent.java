package com.likelion.bizup.module.user.event;

import com.likelion.bizup.module.user.entity.ValidateUser;

public record ValidateBnoEvent(
        ValidateUser validateUser
) {
}
