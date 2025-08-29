package com.juan.scheduling.event.listener;

import com.juan.scheduling.event.AppointmentScheduledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @EventListener
    public void onAppointmentScheduled(AppointmentScheduledEvent event) {
        // Simulação de envio de email para o médico informando sobre a nova consulta agendada
        System.out.println("=========================================================");
        System.out.println("OBSERVER: Novo agendamento realizado!");
        System.out.println("Enviando email para o médico: " + event.appointment().getDoctor().getEmail());
        System.out.println("Detalhes: " + event.appointment().getDoctor().getName()
                + ", você possui uma nova consulta agendada com o(a) paciente " + event.appointment().getPatient().getName()
                + ", em " + event.appointment().getAppointmentDateTime());
        System.out.println("=========================================================");
    }
}
