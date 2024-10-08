package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
@Data
@ToString
@AllArgsConstructor

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate date;

    public Task(String title, String description, LocalDate date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
