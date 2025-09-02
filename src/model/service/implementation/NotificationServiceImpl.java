package model.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    
    private static final Logger LOG = LogManager.getLogger(NotificationServiceImpl.class);

    @Override
    public void sendNotification(String email , String message) {
  
        LOG.info("Notification envoyée à : " + email + " | Message : " + message);
    }
}
