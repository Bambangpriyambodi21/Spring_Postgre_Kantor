package com.kantor.kantor.service;

import com.kantor.kantor.constant.ERole;
import com.kantor.kantor.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
