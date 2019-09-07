package gt.edu.umg.invoice.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "tc_role_menu")
public class TcRoleMenu implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleMenuId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
	private TcMenu tcMenu;
	
    @ManyToOne
    @JoinColumn(name = "role_id")
	private TcRole tcRole;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dateCreated = new Date();
	
	@NotNull
	@Column(columnDefinition="default 1")
	private byte statusId;

	public TcRoleMenu() {
	}

	public TcRoleMenu(long roleMenuId, TcMenu tcMenu, TcRole tcRole, Date dateCreated, byte statusId) {
		this.roleMenuId = roleMenuId;
		this.tcMenu = tcMenu;
		this.tcRole = tcRole;
		this.dateCreated = dateCreated;
		this.statusId = statusId;
	}

	public long getRoleMenuId() {
		return this.roleMenuId;
	}

	public void setRoleMenuId(long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public TcMenu getTcMenu() {
		return this.tcMenu;
	}

	public void setTcMenu(TcMenu tcMenu) {
		this.tcMenu = tcMenu;
	}

	public TcRole getTcRole() {
		return this.tcRole;
	}

	public void setTcRole(TcRole tcRole) {
		this.tcRole = tcRole;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public byte getStatusId() {
		return this.statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TcRoleMenu))
			return false;
		TcRoleMenu castOther = (TcRoleMenu) other;

		return (this.tcMenu.getMenuId() == castOther.getTcMenu().getMenuId()) && (this.tcRole.getRoleId() == castOther.getTcRole().getRoleId())
				&& ((this.getDateCreated() == castOther.getDateCreated())
						|| (this.getDateCreated() != null && castOther.getDateCreated() != null
								&& this.getDateCreated().equals(castOther.getDateCreated())))
				&& (this.getStatusId() == castOther.getStatusId());
	}

	public int hashCode() {
		return Objects.hash(this.tcMenu.getMenuId(), this.tcRole.getRoleId(), this.dateCreated);
	}

}
