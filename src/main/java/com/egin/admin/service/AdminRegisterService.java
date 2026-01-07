package com.egin.admin.service;

import com.egin.admin.model.Admin;
import com.egin.admin.model.dto.request.AdminRegisterRequest;

public interface AdminRegisterService {

    Admin registerAdmin(final AdminRegisterRequest request);

}
