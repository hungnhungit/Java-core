package com.banking.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.lib.Pager;
import com.banking.demo.models.Role;
import com.banking.demo.repositories.RoleRepository;
import com.banking.demo.service.MessageService;

@Controller
@RequestMapping(value = "admin/roles")
public class RoleController {

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = { 5, 10, 20 };
	private static final String URL_ROLE = "/admin/roles";
	private RoleRepository rolesRepository;
	private MessageService messageService;

	@Autowired
	public RoleController(RoleRepository rolesRepository, MessageService messageService) {
		this.rolesRepository = rolesRepository;
		this.messageService = messageService;
	}

	@GetMapping({ "", "/" })
	public String index(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam(name = "search", defaultValue = "") String search) {

		String likeNameOrTitile = "";

		if (search.trim() != "") {
			likeNameOrTitile = "%" + search + "%";
		}

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Role> roles = rolesRepository.findAll(new PageRequest(evalPage, evalPageSize));

		Pager pager = new Pager(roles.getTotalPages(), roles.getNumber(), BUTTONS_TO_SHOW);

		Integer pageNumber = null;

		if (page.isPresent()) {
			pageNumber = page.get();
		}

		model.addAttribute("roles", roles);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("active", pageNumber);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return "admin/roles/index";
	}

	@GetMapping({ "/new", "/create" })
	public String create(Model model) {
		model.addAttribute("role", new Role());
		return "admin/roles/new";
	}

	@PostMapping("/create")
	public String store(@Valid Role roleSubmitted, Errors validation, Model model,
			RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("role", roleSubmitted);
			return "admin/roles/new";
		}
		rolesRepository.save(roleSubmitted);
		messageService.addMessage("Success", "success", redirectAttributes);
		return "redirect:/admin/roles";
	}

	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		Role Role = rolesRepository.findOne(id);
		model.addAttribute("Role", Role);

		return "admin/roles/edit";
	}

	@PostMapping("/update")
	public String update(@Valid Role Rolesubmitted, Errors validation, Model model,
			RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("Role", Rolesubmitted);
			return "admin/roles/edit";
		}
		Role role = rolesRepository.findOne(Rolesubmitted.getId());
		rolesRepository.save(role);
		
		messageService.addMessage("Success", "success", redirectAttributes);
		redirectAttributes.addFlashAttribute("messages", messageService.getMessage());
		return "redirect:" + URL_ROLE;
	}

	@GetMapping("delete/{id}")
	public String destroy(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		rolesRepository.delete(id);
		messageService.addMessage("Success", "danger", redirectAttributes);
		redirectAttributes.addFlashAttribute("messages", messageService.getMessage());
		return "redirect:/admin/roles";
	}

}
