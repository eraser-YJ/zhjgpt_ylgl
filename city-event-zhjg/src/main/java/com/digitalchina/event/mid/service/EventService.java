package com.digitalchina.event.mid.service;

import com.digitalchina.event.mid.entity.Bedept;

public interface EventService {

    Bedept getBedptByUserId(Integer curuid);

    Bedept getBedptByBedId(Integer bedid);


}
