package gt.edu.umg.invoice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gt.edu.umg.invoice.model.TcMenu;
import gt.edu.umg.invoice.model.TcRole;
import gt.edu.umg.invoice.model.TcRoleMenu;
import gt.edu.umg.invoice.repository.TcMenuRepository;
import gt.edu.umg.invoice.repository.TcRoleMenuRepository;
import gt.edu.umg.invoice.repository.TcRoleRepository;
import gt.edu.umg.invoice.utils.ApiResponse;
import gt.edu.umg.invoice.utils.AppProperty;
import gt.edu.umg.invoice.utils.ResponseResult;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/role")
public class TcRoleController {

	ApiResponse apiResponse = new ApiResponse();
	private boolean showError;
	
	@Autowired
	TcRoleRepository tcRoleRepository;
	
	@Autowired
	TcRoleMenuRepository tcRoleMenuRepository;
	
	@Autowired
	TcMenuRepository tcMenuRepository;
	
	@Autowired
	public TcRoleController(AppProperty appProperties) {
		this.showError = (appProperties.getShowException() == 1);
	}

	@PostMapping("/add")
	public ApiResponse setRole(@Valid @RequestBody TcRole tcRole) {
		try {
			tcRoleRepository.save(tcRole);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}
		return apiResponse;
	}
	
	@GetMapping("/all")
	public ApiResponse getAll() {
		try {
			List<?> lista = tcRoleRepository.findAll();
			apiResponse.setData(lista);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}
		return apiResponse;
	}
	
	@GetMapping("/{roleId}")
	public ApiResponse getRole(@PathVariable(value="roleId") Long roleId) {
		try {
			Optional<TcRole> role = tcRoleRepository.findById(roleId);
			if (role == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El rol indicado no existe");
			} else {
				List<TcRole> list = new ArrayList<>();
				list.add(role.get());
				apiResponse.setData(list);
				apiResponse.setStatus(ResponseResult.success.getValue());
				apiResponse.setMessage(ResponseResult.success.getMessage());
			}			
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}		
		return apiResponse;
	}
	
	@PutMapping("/{roleId}")
	public ApiResponse updRole(@PathVariable(value="roleId") Long roleId, @Valid @RequestBody TcRole tcRole) {
		try {
			Optional<TcRole> found = tcRoleRepository.findById(roleId);
			if (found == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El rol indicado no existe");
			} else {
				TcRole role = found.get();
				role.setRoleDesc(tcRole.getRoleDesc());
				role.setStatusId(tcRole.getStatusId());
				TcRole roleUpdate = tcRoleRepository.save(role);
				List<TcRole> list = new ArrayList<>();
				list.add(roleUpdate);
				apiResponse.setData(list);
				apiResponse.setStatus(ResponseResult.success.getValue());
				apiResponse.setMessage(ResponseResult.success.getMessage());
			}
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}		
		return apiResponse;
	}
	
	@DeleteMapping("/{roleId}")
	public ApiResponse delRole(@PathVariable(value="roleId") Long roleId) {
		try {
			Optional<TcRole> found = tcRoleRepository.findById(roleId);
			TcRole role = found.get();
			role.setStatusId((byte)0);
			tcRoleRepository.save(role);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}		
		return apiResponse;
	}
	
	@GetMapping("/{roleId}/menu/all")
	public ApiResponse getAllMenuByRole(@PathVariable(value="roleId") Long roleId) {
		try {
			Optional<TcRole> role = tcRoleRepository.findById(roleId);
			TcRole tcRole = role.get();
			List<TcRoleMenu> lista = tcRoleMenuRepository.findAllByRole(tcRole);
			List<TcMenu> menus = new ArrayList<>();
			for (TcRoleMenu tcRoleMenu : lista) {
				TcMenu tcMenu = tcRoleMenu.getTcMenu();
				tcMenu.setStatusId(tcRoleMenu.getStatusId());
				menus.add(tcMenu);
			}
			tcRole.setMenus(menus);
			List<TcRole> list = new ArrayList<>();
			list.add(tcRole);
			apiResponse.setData(list);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}
		return apiResponse;
	}
	
	@PostMapping("/{roleId}/menu")
	public ApiResponse setRoleMenu(@PathVariable(value="roleId") Long roleId, @Valid @RequestBody TcRoleMenu tcRoleMenu) {
		try {
			tcRoleMenuRepository.save(tcRoleMenu);
			List<TcRoleMenu> list = new ArrayList<>();
			list.add(tcRoleMenu);
			apiResponse.setData(list);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (e.getMessage().contains("ConstraintViolationException")) {
				apiResponse.setMessage("Los datos que intenta agregar ya se encuentran");
			} else {
				if (showError) {
					apiResponse.setMessage(e.getMessage());
				} else {
					apiResponse.setMessage(ResponseResult.fail.getMessage());
				}
			}
		}
		return apiResponse;
	}
	
	@PutMapping("/{roleId}/menu")
	public ApiResponse updRoleMenu(@PathVariable(value="roleId") Long roleId, @Valid @RequestBody TcRoleMenu tcRoleMenu) {
		try {
			Optional<TcMenu> tcMenu = tcMenuRepository.findById(tcRoleMenu.getTcMenu().getMenuId());
			Optional<TcRole> tcRole = tcRoleRepository.findById(tcRoleMenu.getTcRole().getRoleId());
			Optional<TcRoleMenu> found = tcRoleMenuRepository.findByRoleAndMenu(tcRole.get(), tcMenu.get());
			TcRoleMenu role = found.get();
			role.setStatusId(tcRoleMenu.getStatusId());
			tcRoleMenuRepository.save(role);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (showError) {
				apiResponse.setMessage(e.getMessage());
			} else {
				apiResponse.setMessage(ResponseResult.fail.getMessage());
			}
		}
		return apiResponse;
	}
	
}