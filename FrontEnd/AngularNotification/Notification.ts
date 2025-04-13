import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';  // Import HttpClient to make HTTP requests

// Define the notification interface for type-checking
interface Notification {
    email: string;
    subject: string;
    message: string;
}

@Component({
    selector: 'app-notification',
    templateUrl: './notification.html',  // The HTML template for this component
    styleUrls: ['./notification.css']    // The CSS for styling the component
})
export class NotificationComponent {

    // Form data for email, subject, and message
    email: string = '';
    subject: string = '';
    message: string = '';
    responseMessage: string = '';

    // URL of the backend API (assuming the API Gateway is running on localhost:8081)
    private apiUrl = 'http://localhost:8081/notifications/send-alert';

    constructor(private http: HttpClient) { }

    // Method to handle form submission and send the notification to the backend
    sendNotification(): void {
        const notification: Notification = {
            email: this.email,
            subject: this.subject,
            message: this.message
        };

        // Send the notification data to the backend using HttpClient
        this.http.post(this.apiUrl, notification).subscribe(
            response => {
                // On success, show a success message
                this.responseMessage = 'Notification sent successfully!';
            },
            error => {
                // On error, show an error message
                this.responseMessage = 'Error sending notification.';
            }
        );
    }
}
