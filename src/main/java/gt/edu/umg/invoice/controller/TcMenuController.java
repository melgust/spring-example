package gt.edu.umg.invoice.controller;

import java.util.ArrayList;
import java.util.Date;
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
import gt.edu.umg.invoice.repository.TcMenuRepository;
import gt.edu.umg.invoice.utils.ApiResponse;
import gt.edu.umg.invoice.utils.AppProperty;
import gt.edu.umg.invoice.utils.ResponseResult;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/menu")
public class TcMenuController {

	ApiResponse apiResponse = new ApiResponse();
	private boolean showError;
	
	@Autowired
	TcMenuRepository tcMenuRepository;
	
	@Autowired
	public TcMenuController(AppProperty appProperty) {
		this.showError = (appProperty.getShowException() == 1);
	}
	
	@PostMapping("/add")
	public ApiResponse setMenu(@Valid @RequestBody TcMenu tcMenu) {
		try {
			tcMenuRepository.save(tcMenu);
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
			List<?> lista = tcMenuRepository.findAll();
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
	
	@GetMapping("/{menuId}")
	public ApiResponse getMenu(@PathVariable(value="menuId") Long menuId) {
		try {
			Optional<TcMenu> menu = tcMenuRepository.findById(menuId);
			if (menu == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El menu indicado no existe");
			} else {
				List<TcMenu> list = new ArrayList<>();
				list.add(menu.get());
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
	
	@PutMapping("/{menuId}")
	public ApiResponse updMenu(@PathVariable(value="menuId") Long menuId, @Valid @RequestBody TcMenu tcMenu) {
		try {
			Optional<TcMenu> found = tcMenuRepository.findById(menuId);
			TcMenu menu = found.get();
			menu.setMenuDesc(tcMenu.getMenuDesc());
			menu.setStatusId(tcMenu.getStatusId());
			menu.setDateLastChange(new Date());
			menu.setIcon(tcMenu.getIcon());
			menu.setIconColor(tcMenu.getIconColor());
			menu.setLabelColor(tcMenu.getLabelColor());
			menu.setMenuUrl(tcMenu.getMenuUrl());
			menu.setShortName(tcMenu.getShortName());
			menu.setTcFather(tcMenu.getTcFather());
			menu.setShowTree(tcMenu.getShowTree());
			TcMenu menuUpdate = tcMenuRepository.save(menu);
			List<TcMenu> list = new ArrayList<>();
			list.add(menuUpdate);
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
	
	@DeleteMapping("/{menuId}")
	public ApiResponse delMenu(@PathVariable(value="menuId") Long menuId) {
		try {
			Optional<TcMenu> found = tcMenuRepository.findById(menuId);
			if (found == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El menu indicado no existe");
			} else {
				TcMenu menu = found.get();
				menu.setStatusId((byte)0);
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
	
}