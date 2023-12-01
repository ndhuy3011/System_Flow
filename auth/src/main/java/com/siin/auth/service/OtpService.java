package com.siin.auth.service;

import com.siin.auth.dto.otp.CreateOtpDTO;
import com.siin.auth.models.Otp;

public interface OtpService {
    public Otp createOtp(CreateOtpDTO input);

    public Otp refOtp(CreateOtpDTO input);
}
