package com.banking.demo.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.lib.Pager;
import com.banking.demo.models.Category;
import com.banking.demo.models.Post;
import com.banking.demo.models.Tag;
import com.banking.demo.repositories.CategoryRepository;
import com.banking.demo.repositories.PostRepository;
import com.banking.demo.repositories.TagRepository;
import com.banking.demo.service.MessageService;

@Controller
@RequestMapping(value = "admin/categories")
public class CategoryController {

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = { 5, 10, 20 };

	private CategoryRepository categoryRepository;
	private MessageService messageService;

	@Autowired
	public CategoryController(CategoryRepository categoryRepository, MessageService messageService) {
		this.categoryRepository = categoryRepository;
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

		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Category> categories = categoryRepository.findAll(new PageRequest(evalPage, INITIAL_PAGE_SIZE));

		Pager pager = new Pager(categories.getTotalPages(), categories.getNumber(), BUTTONS_TO_SHOW);

		Integer pageNumber = null;

		if (page.isPresent()) {
			pageNumber = page.get();
		}

		model.addAttribute("categories", categories);
		model.addAttribute("active", pageNumber);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return "admin/category/index";
	}

	@GetMapping({ "/new", "/create" })
	public String create(Model model) {

		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/category/new";
	}

	@PostMapping("/create")
	public String store(@Valid Category categorySubmitted, Errors validation, Model model,
			RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("category", categorySubmitted);
			model.addAttribute("categories", categoryRepository.findAll());
			return "admin/posts/new";
		}
		categoryRepository.save(categorySubmitted);
		return "redirect:/admin/categories";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		Category category = categoryRepository.findOne(id);
		model.addAttribute("category", category);
		model.addAttribute("categories", categoryRepository.findAll());

		return "admin/category/edit";
	}


}
