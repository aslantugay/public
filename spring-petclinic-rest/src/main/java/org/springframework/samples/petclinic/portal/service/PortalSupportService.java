package org.springframework.samples.petclinic.portal.service;

import org.springframework.samples.petclinic.portal.model.SupportMessage;

import java.util.List;

public interface PortalSupportService {

    List<SupportMessage> recent();

    SupportMessage post(SupportMessage message);
}
