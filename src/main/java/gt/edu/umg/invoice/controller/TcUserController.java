package gt.edu.umg.invoice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import gt.edu.umg.invoice.model.TcUser;
import gt.edu.umg.invoice.repository.TcMenuRepository;
import gt.edu.umg.invoice.repository.TcRoleMenuRepository;
import gt.edu.umg.invoice.repository.TcUserRepository;
import gt.edu.umg.invoice.utils.ApiResponse;
import gt.edu.umg.invoice.utils.AppProperty;
import gt.edu.umg.invoice.utils.JwtProvider;
import gt.edu.umg.invoice.utils.ResponseResult;
import gt.edu.umg.invoice.utils.User;

import org.apache.tomcat.util.codec.binary.Base64;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class TcUserController {

	ApiResponse apiResponse = new ApiResponse();
	private boolean showError;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	TcUserRepository tcUserRepository;

	@Autowired
	TcRoleMenuRepository tcRoleMenuRepository;

	@Autowired
	TcMenuRepository tcMenuRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired

	public TcUserController(AppProperty appProperties) {
		this.showError = (appProperties.getShowException() == 1);
	}

	@PostMapping("/login")
	public ApiResponse authenticateUser(@Valid @RequestBody User loginRequest) {

		try {
			byte[] tmpBPass = Base64.decodeBase64(loginRequest.getPassword());
			String tmpPass = new String(tmpBPass);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), tmpPass));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			Optional<TcUser> user = tcUserRepository.findByUsername(loginRequest.getUsername());
			TcUser tcUser = user.get();
			tcUser.setPassword("");
			TcRole tcRole = tcUser.getTcRole();
			List<TcRoleMenu> lista = tcRoleMenuRepository.findAllByRootMenu(tcRole);
			List<TcMenu> menus = new ArrayList<>();
			for (TcRoleMenu tcRoleMenu : lista) {
				TcMenu tcMenu = tcRoleMenu.getTcMenu();
				List<TcMenu> children = tcMenuRepository.findByTcFather(tcMenu);
				for (TcMenu child : children) {
					List<TcMenu> grandchildren = tcMenuRepository.findByTcFather(child);
					for (TcMenu grandchild : grandchildren) {
						// remove father
						grandchild.setTcFather(null);
					}
					child.setChildren(grandchildren);
					// remove father
					child.setTcFather(null);
				}
				tcMenu.setChildren(children);
				// remove father
				tcMenu.setTcFather(null);
				menus.add(tcMenu);
			}
			tcRole.setMenus(menus);
			tcUser.setTcRole(tcRole);
			List<TcUser> list = new ArrayList<>();
			list.add(tcUser);
			apiResponse.setData(list);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
			apiResponse.setSingleResponse(jwt);
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (e.getMessage().equals("Bad credentials")) {
				apiResponse.setMessage("El usuario y/o la contraseña no son correctos");
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
	
	@PostMapping("/add")
	public ApiResponse setUser(@Valid @RequestBody TcUser tcUser) {
		try {

			boolean continuar = true;
			if (tcUserRepository.existsByUsername(tcUser.getUsername())) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El usuario ya existe");
				continuar = false;
			}

			if (continuar) {
				if (tcUserRepository.existsByEmail(tcUser.getEmail())) {
					apiResponse.setStatus(ResponseResult.fail.getValue());
					apiResponse.setMessage("El correo ya se encuentra registrado");
					continuar = false;
				}
			}
			if (continuar) {
				String password = "Inicio123";
				tcUser.setPassword(encoder.encode(password));
				tcUserRepository.save(tcUser);
				apiResponse.setStatus(ResponseResult.success.getValue());
				apiResponse.setMessage("Usuario creado correctamente, su contraseña inicial es: " + password);
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

	@GetMapping("/all")
	public ApiResponse getAll() {
		try {
			List<TcUser> lista = tcUserRepository.findAll();
			for (TcUser tcUser : lista) {
				tcUser.setPassword("");
			}
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

	@GetMapping("/{userId}")
	public ApiResponse getUser(@PathVariable(value = "userId") Long userId) {
		try {
			Optional<TcUser> user = tcUserRepository.findById(userId);
			if (user == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El usuario indicado no existe");
			} else {
				List<TcUser> list = new ArrayList<>();
				list.add(user.get());
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

	@PutMapping("/{userId}")
	public ApiResponse updUser(@PathVariable(value = "userId") Long userId, @Valid @RequestBody TcUser tcUser) {
		try {
			Optional<TcUser> found = tcUserRepository.findById(userId);
			TcUser user = found.get();
			user.setFullname(tcUser.getFullname());
			user.setUsername(tcUser.getUsername());
			user.setStatusId(tcUser.getStatusId());
			user.setRequiredChangePassword(tcUser.getRequiredChangePassword());
			user.setTcRole(tcUser.getTcRole());
			TcUser userUpdate = tcUserRepository.save(user);
			List<TcUser> list = new ArrayList<>();
			list.add(userUpdate);
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

	@PutMapping("/user/{userId}")
	public ApiResponse updName(@PathVariable(value = "userId") Long userId, @Valid @RequestBody TcUser tcUser) {
		try {
			Optional<TcUser> found = tcUserRepository.findById(userId);
			TcUser user = found.get();
			user.setFullname(tcUser.getFullname());
			user.setPhoto(tcUser.getPhoto());
			TcUser userUpdate = tcUserRepository.save(user);
			List<TcUser> list = new ArrayList<>();
			list.add(userUpdate);
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

	@PutMapping("/userpass/{userId}")
	public ApiResponse updPasswrd(@PathVariable(value = "userId") Long userId, @Valid @RequestBody TcUser tcUser) {
		try {
			byte[] tmpBPass = Base64.decodeBase64(tcUser.getOldPassword());
			String tmpPass = new String(tmpBPass);
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(tcUser.getUsername(), tmpPass));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			Optional<TcUser> found = tcUserRepository.findById(userId);
			TcUser user = found.get();
			String pass = tcUser.getPassword();
			tmpBPass = Base64.decodeBase64(pass);
			pass = new String(tmpBPass);
			pass = encoder.encode(pass);
			user.setPassword(pass);
			TcUser userUpdate = tcUserRepository.save(user);
			List<TcUser> list = new ArrayList<>();
			list.add(userUpdate);
			apiResponse.setData(list);
			apiResponse.setStatus(ResponseResult.success.getValue());
			apiResponse.setMessage(ResponseResult.success.getMessage());
		} catch (Exception e) {
			apiResponse.setStatus(ResponseResult.fail.getValue());
			if (e.getMessage().equals("Bad credentials")) {
				apiResponse.setMessage("La contraseña actual no es correcta, favor de verificar");
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

	@DeleteMapping("/{userId}")
	public ApiResponse delUser(@PathVariable(value = "userId") Long userId) {
		try {
			Optional<TcUser> found = tcUserRepository.findById(userId);
			if (found == null) {
				apiResponse.setStatus(ResponseResult.fail.getValue());
				apiResponse.setMessage("El usuario indicado no existe");
			} else {
				TcUser user = found.get();
				user.setStatusId((byte) 0);
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