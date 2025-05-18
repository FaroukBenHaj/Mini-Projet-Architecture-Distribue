package tn.esprit.announcement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.announcement.entity.Announcement;
import tn.esprit.announcement.entity.AnnouncementType;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByType(AnnouncementType type);
    List<Announcement> findByIsActive(boolean isActive);
    List<Announcement> findByTypeAndIsActive(AnnouncementType type, boolean isActive);
}
