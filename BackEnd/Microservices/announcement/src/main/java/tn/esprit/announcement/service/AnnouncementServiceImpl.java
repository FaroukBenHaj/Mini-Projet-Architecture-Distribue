package tn.esprit.announcement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.announcement.entity.Announcement;
import tn.esprit.announcement.entity.AnnouncementType;
import tn.esprit.announcement.repository.AnnouncementRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public Optional<Announcement> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public List<Announcement> getAnnouncementsByType(AnnouncementType type) {
        return announcementRepository.findByType(type);
    }

    @Override
    public List<Announcement> getActiveAnnouncements() {
        return announcementRepository.findByIsActive(true);
    }

    @Override
    public Announcement updateAnnouncement(Long id, Announcement announcement) {
        return announcementRepository.findById(id)
                .map(existingAnnouncement -> {
                    existingAnnouncement.setTitle(announcement.getTitle());
                    existingAnnouncement.setContent(announcement.getContent());
                    existingAnnouncement.setType(announcement.getType());
                    existingAnnouncement.setActive(announcement.isActive());
                    return announcementRepository.save(existingAnnouncement);
                })
                .orElseThrow(() -> new RuntimeException("Announcement not found with id: " + id));
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void deactivateAnnouncement(Long id) {
        announcementRepository.findById(id)
                .ifPresent(announcement -> {
                    announcement.setActive(false);
                    announcementRepository.save(announcement);
                });
    }
}