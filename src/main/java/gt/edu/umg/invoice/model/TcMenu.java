package gt.edu.umg.invoice.model;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "tc_menu")
public class TcMenu implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuId;

	@Column(length = 150)
	@NotNull
	private String menuDesc;

	@NotNull
	@Column(columnDefinition = "default 1")
	private byte statusId;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dateCreated = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dateLastChange;

	@NotNull
	@Column(length = 50)
	private String shortName;

	@NotNull
	@Column(length = 250)
	private String icon;

	@NotNull
	@Column(length = 250)
	private String menuUrl;

	@NotNull
	@Column(length = 50)
	private String iconColor;

	@NotNull
	@Column(length = 50)
	private String labelColor;

	@NotNull
	@Column(columnDefinition = "default 1")
	private byte showTree;

	@ManyToOne
	@JoinColumn(columnDefinition="long", name = "father_id")
	private TcMenu tcFather;

	@Transient
	private List<TcMenu> children;

	public TcMenu() {
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastChange() {
		return dateLastChange;
	}

	public void setDateLastChange(Date dateLastChange) {
		this.dateLastChange = dateLastChange;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getIconColor() {
		return iconColor;
	}

	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public byte getShowTree() {
		return showTree;
	}

	public void setShowTree(byte showTree) {
		this.showTree = showTree;
	}

	public TcMenu getTcFather() {
		return tcFather;
	}

	public void setTcFather(TcMenu tcFather) {
		this.tcFather = tcFather;
	}

	public List<TcMenu> getChildren() {
		return children;
	}

	public void setChildren(List<TcMenu> children) {
		this.children = children;
	}

}