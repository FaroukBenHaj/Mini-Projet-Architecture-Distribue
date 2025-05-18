export enum AnnouncementType {
    GENERAL = 'GENERAL',
    MAINTENANCE = 'MAINTENANCE',
    EVENT = 'EVENT',
    EMERGENCY = 'EMERGENCY',
    ACADEMIC = 'ACADEMIC',
    HOUSING = 'HOUSING',
    PAYMENT = 'PAYMENT'
}

export interface Announcement {
    id?: number;
    title: string;
    content: string;
    type: AnnouncementType;
    createdAt?: Date;
    updatedAt?: Date;
    isActive: boolean;
} 