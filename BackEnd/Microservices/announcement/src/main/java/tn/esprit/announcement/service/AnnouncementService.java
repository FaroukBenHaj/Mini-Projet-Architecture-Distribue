package tn.esprit.announcement.service;

import tn.esprit.announcement.entity.Announcement;
import tn.esprit.announcement.entity.AnnouncementType;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    Announcement createAnnouncement(Announcement announcement);
    Optional<Announcement> getAnnouncementById(Long id);
    List<Announcement> getAllAnnouncements();
    List<Announcement> getAnnouncementsByType(AnnouncementType type);
    List<Announcement> getActiveAnnouncements();
    Announcement updateAnnouncement(Long id, Announcement announcement);
    void deleteAnnouncement(Long id);
    void deactivateAnnouncement(Long id);
}
