CREATE TABLE allergy
(
    id         UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,

    created_at TIMESTAMPTZ  NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_at TIMESTAMPTZ  NOT NULL,
    updated_by VARCHAR(100) NOT NULL,

    CONSTRAINT pk_allergy PRIMARY KEY (id),
    CONSTRAINT uq_allergy_name UNIQUE (name)
);
