/**
 * This class is generated by jOOQ
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
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "project_details", schema = "version_manager_test")
public class ProjectDetails implements Serializable {

    private static final long serialVersionUID = 1308103103;

    private Long   productDetailId;
    private String projectName;
    private Long   securityId;

    public ProjectDetails() {}

    public ProjectDetails(ProjectDetails value) {
        this.productDetailId = value.productDetailId;
        this.projectName = value.projectName;
        this.securityId = value.securityId;
    }

    public ProjectDetails(
        Long   productDetailId,
        String projectName,
        Long   securityId
    ) {
        this.productDetailId = productDetailId;
        this.projectName = projectName;
        this.securityId = securityId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getProductDetailId() {
        return this.productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    @Column(name = "project_name", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "security_id", nullable = false, precision = 19)
    @NotNull
    public Long getSecurityId() {
        return this.securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProjectDetails (");

        sb.append(productDetailId);
        sb.append(", ").append(projectName);
        sb.append(", ").append(securityId);

        sb.append(")");
        return sb.toString();
    }
}