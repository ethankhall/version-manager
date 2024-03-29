/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "project_detail_tracker", schema = "version_manager_test")
public class ProjectDetailTracker implements Serializable {

    private static final long serialVersionUID = -229593600;

    private Long projectDetailTrackerId;
    private Long productDetailId;
    private Long userId;

    public ProjectDetailTracker() {}

    public ProjectDetailTracker(ProjectDetailTracker value) {
        this.projectDetailTrackerId = value.projectDetailTrackerId;
        this.productDetailId = value.productDetailId;
        this.userId = value.userId;
    }

    public ProjectDetailTracker(
        Long projectDetailTrackerId,
        Long productDetailId,
        Long userId
    ) {
        this.projectDetailTrackerId = projectDetailTrackerId;
        this.productDetailId = productDetailId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_detail_tracker_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getProjectDetailTrackerId() {
        return this.projectDetailTrackerId;
    }

    public void setProjectDetailTrackerId(Long projectDetailTrackerId) {
        this.projectDetailTrackerId = projectDetailTrackerId;
    }

    @Column(name = "product_detail_id", nullable = false, precision = 19)
    @NotNull
    public Long getProductDetailId() {
        return this.productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    @Column(name = "user_id", nullable = false, precision = 19)
    @NotNull
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProjectDetailTracker (");

        sb.append(projectDetailTrackerId);
        sb.append(", ").append(productDetailId);
        sb.append(", ").append(userId);

        sb.append(")");
        return sb.toString();
    }
}
