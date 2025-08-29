package com.juan.scheduling.event;

import com.juan.scheduling.model.Appointment;

public record AppointmentScheduledEvent(Appointment appointment) {
}
