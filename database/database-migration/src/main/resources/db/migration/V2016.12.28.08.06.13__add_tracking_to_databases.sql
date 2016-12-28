CREATE TABLE project_detail_tracker (
    project_detail_tracker_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_detail_id         BIGINT NOT NULL,
    user_id                   BIGINT NOT NULL,
    FOREIGN KEY (product_detail_id) REFERENCES project_details (product_detail_id),
    FOREIGN KEY (user_id) REFERENCES user_details (user_id)
)
    ENGINE = innodb;
