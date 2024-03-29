CREATE TABLE APPOINTMENT
(
    APPOINTMENT_ID     VARCHAR(10) NOT NULL,
    APPOINTMENT_DATE   VARCHAR(10),
    APPOINTMENT_STATUS INTEGER,
    ASSIGNED_VET       VARCHAR(10),
    PET_ID             VARCHAR(10),
    PRIMARY KEY (APPOINTMENT_ID),
    CONSTRAINT FK_PET FOREIGN KEY (PET_ID) REFERENCES PET (PET_ID)
);
