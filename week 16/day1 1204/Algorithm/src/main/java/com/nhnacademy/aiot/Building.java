package com.nhnacademy.aiot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum Status {
    Completed,
    UnderConstruction
}

class BuildingUnderConstructionException extends RuntimeException {

    public BuildingUnderConstructionException(String message) {
        super(message);
    }
}

class Building {

    private final String name;
    private final String location;
    private final int height;
    private final int floorCount;
    private final LocalDateTime startedDate;
    private final LocalDateTime completedDate;
    private final Status status;

    private Building(Builder builder) {
        this.name = builder.name;
        this.location = builder.location;
        this.height = builder.height;
        this.floorCount = builder.floorCount;
                this.startedDate = parseDateTime(builder.startedDate);

        this.completedDate = parseDateTime(builder.completedDate);
        this.status = builder.status;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartedDate() {
        return startedDate;
    }

    public LocalDateTime getCompletedDate() {
        if (status == Status.UnderConstruction) {
            throw new BuildingUnderConstructionException("건설 중인 건물은 완공 날짜를 조회할 수 없습니다.");
        }
        return completedDate;
    }

    private LocalDateTime parseDateTime(String date) {
        return date != null
                ? LocalDateTime.parse(date + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String location;
        private int height;
        private int floorCount;
        private String startedDate;
        private String completedDate;
        private Status status;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder floorCount(int floorCount) {
            this.floorCount = floorCount;
            return this;
        }

        public Builder startedDate(String startedDate) {
            this.startedDate = startedDate;
            return this;
        }

        public Builder completedDate(String completedDate) {
            this.completedDate = completedDate;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Building build() {
            validateBuilding();
            return new Building(this);
        }

        private void validateBuilding() {
            if (status == Status.UnderConstruction && completedDate != null) {
                
                throw new BuildingUnderConstructionException("건설 중인 건물의 완공 날짜는 지정할 수 없습니다.");
            } else if (status == Status.Completed && completedDate == null) {
                
                throw new BuildingUnderConstructionException("완공된 건물은 완공 날짜를 지정해야 합니다.");
            }
        }


    }
}
