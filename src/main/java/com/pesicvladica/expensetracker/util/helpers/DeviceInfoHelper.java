package com.pesicvladica.expensetracker.util.helpers;

import com.pesicvladica.expensetracker.dto.DeviceInfo;
import jakarta.servlet.http.HttpServletRequest;

public class DeviceInfoHelper {

    public static DeviceInfo getDeviceInfoFromRequest(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || remoteAddr.isEmpty()) {
            remoteAddr = request.getRemoteAddr();
        }

        String deviceId = request.getHeader("X-DEVICE-ID");
        String userAgent = request.getHeader("USER-AGENT");

        return new DeviceInfo(deviceId, remoteAddr, userAgent);
    }
}
